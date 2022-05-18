package org.semanticweb.owlapi.atomicdecomposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.modularity.ModuleType;

/**
 * atomc decomposition implementation
 */
public class AtomicDecompositionImpl implements AtomicDecomposition {

    final Map<OWLEntity, Set<Atom>> termBasedIndex = new LinkedHashMap<>();
    private final ModuleType type;
    Set<OWLAxiom> globalAxioms;
    Set<OWLAxiom> tautologies;
    List<Atom> atoms;
    Map<Atom, Integer> atomIndex = new HashMap<>();
    IdentityMultiMap<Atom, Atom> dependents = new IdentityMultiMap<>();
    IdentityMultiMap<Atom, Atom> dependencies = new IdentityMultiMap<>();
    Decomposer decomposer;

    /**
     * @param o o
     */
    public AtomicDecompositionImpl(OWLOntology o) {
        this(AxiomSelector.selectAxioms(o), ModuleType.BOT);
    }

    /**
     * @param o o
     * @param type type
     */
    public AtomicDecompositionImpl(OWLOntology o, ModuleType type) {
        this(AxiomSelector.selectAxioms(o), type);
    }

    /**
     * @param o o
     * @param type type
     * @param excludeAssertions true if assertions should be excluded
     */
    public AtomicDecompositionImpl(OWLOntology o, ModuleType type, boolean excludeAssertions) {
        this(AxiomSelector.selectAxioms(o, excludeAssertions), type);
    }

    /**
     * @param axioms axioms
     * @param type type
     */
    public AtomicDecompositionImpl(List<OWLAxiom> axioms, ModuleType type) {
        this.type = type;
        decomposer = new Decomposer(AxiomSelector.wrap(axioms), new SyntacticLocalityChecker());
        int size = decomposer.getAOS(this.type).size();
        atoms = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Atom atom = new Atom(asSet(decomposer.getAOS().get(i).getAtomAxioms()));
            atoms.add(atom);
            atomIndex.put(atom, Integer.valueOf(i));
            for (OWLEntity e : atom.getSignature()) {
                termBasedIndex.computeIfAbsent(e, x -> new HashSet<>()).add(atom);
            }
        }
        for (int i = 0; i < size; i++) {
            Set<OntologyAtom> dependentIndexes = decomposer.getAOS().get(i).getDependencies();
            for (OntologyAtom j : dependentIndexes) {
                dependencies.put(atoms.get(i), atoms.get(j.getId()));
                dependents.put(atoms.get(j.getId()), atoms.get(i));
            }
        }
    }

    Set<OWLAxiom> asSet(Collection<AxiomWrapper> c) {
        Set<OWLAxiom> toReturn = new HashSet<>();
        for (AxiomWrapper p : c) {
            toReturn.add(p.getAxiom());
        }
        return toReturn;
    }

    int getModuleType() {
        return type.ordinal();
    }

    @Override
    public Set<Atom> getAtoms() {
        return new HashSet<>(atoms);
    }

    @Nullable
    @Override
    public Atom getAtomForAxiom(OWLAxiom axiom) {
        for (int i = 0; i < atoms.size(); i++) {
            if (atoms.get(i).contains(axiom)) {
                return atoms.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean isTopAtom(Atom atom) {
        return !dependents.containsKey(atom);
    }

    @Override
    public boolean isBottomAtom(Atom atom) {
        return !dependencies.containsKey(atom);
    }

    @Override
    public Set<OWLAxiom> getPrincipalIdeal(Atom atom) {
        return asSet(getAtomModule(atomIndex.get(atom).intValue()));
    }

    @Override
    public Set<OWLEntity> getPrincipalIdealSignature(Atom atom) {
        return Collections.emptySet();
    }

    @Override
    public Set<Atom> getDependencies(Atom atom) {
        return getDependencies(atom, false);
    }

    @Override
    public Set<Atom> getDependencies(Atom atom, boolean direct) {
        return explore(atom, direct, dependencies);
    }

    @Override
    public Set<Atom> getDependents(Atom atom) {
        return getDependents(atom, false);
    }

    @Override
    public Set<Atom> getDependents(Atom atom, boolean direct) {
        return explore(atom, direct, dependents);
    }

    Set<Atom> explore(Atom atom, boolean direct, IdentityMultiMap<Atom, Atom> multimap) {
        if (direct) {
            Set<Atom> hashSet = new HashSet<>(multimap.get(atom));
            for (Atom a : multimap.get(atom)) {
                hashSet.removeAll(multimap.get(a));
            }
            return hashSet;
        }
        Map<Atom, Atom> toReturn = new HashMap<>();
        toReturn.put(atom, atom);
        List<Atom> toDo = new ArrayList<>();
        toDo.add(atom);
        for (int i = 0; i < toDo.size(); i++) {
            final Atom key = toDo.get(i);
            if (key != null) {
                Collection<Atom> c = multimap.get(key);
                for (Atom a : c) {
                    if (toReturn.put(a, a) == null) {
                        toDo.add(a);
                    }
                }
            }
        }
        return toReturn.keySet();
    }

    @Override
    public Set<Atom> getRelatedAtoms(Atom atom) {
        Set<Atom> s = getDependencies(atom);
        s.addAll(getDependents(atom));
        return s;
    }

    @Override
    public Set<Atom> getTopAtoms() {
        Set<Atom> keys = getAtoms();
        keys.removeAll(dependencies.getAllValues());
        return keys;
    }

    @Override
    public Set<Atom> getBottomAtoms() {
        Set<Atom> keys = getAtoms();
        keys.removeAll(dependents.getAllValues());
        return keys;
    }

    Set<OWLAxiom> getGlobalAxioms() {
        return globalAxioms;
    }

    void setGlobalAxioms(Set<OWLAxiom> globalAxioms) {
        this.globalAxioms = globalAxioms;
    }

    @Override
    public Set<OWLAxiom> getTautologies() {
        return asSet(decomposer.getTautologies());
    }

    @Override
    public Map<OWLEntity, Collection<Atom>> getTermBasedIndex() {
        Map<OWLEntity, Collection<Atom>> toReturn = new HashMap<>();
        termBasedIndex.forEach((a, b) -> toReturn.put(a, new ArrayList<>(b)));
        return toReturn;
    }

    /**
     * get a set of axioms that corresponds to the module of the atom with the id INDEX
     *
     * @param index index
     * @return module at index
     */
    Collection<AxiomWrapper> getAtomModule(int index) {
        return decomposer.getAOS().get(index).getModule();
    }

    @Override
    public AtomList getAtomList() {
        return decomposer.getAOS();
    }

    @Override
    public Stream<OWLAxiom> getModule(Stream<OWLEntity> signature, boolean useSemantics,
        ModuleType moduletype) {
        return decomposer.getModule(signature, useSemantics, moduletype).stream()
            .map(AxiomWrapper::getAxiom).filter(Objects::nonNull);
    }
}