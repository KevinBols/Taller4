/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.apitest.baseclasses;

import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.AnnotationPropertyDomain;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.AnnotationPropertyRange;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataExactCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DatatypeDefinition;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DatatypeRestriction;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.FacetRestriction;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.HasKey;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Integer;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.RDFSComment;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SubPropertyChainOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.createClass;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.createIndividual;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingTestCase extends AxiomsRoundTrippingBase {

    private static final String VAR = "urn:swrl:var#";
    private static final IRI iriA = iri("A");
    private static final OWLClass clsA = Class(iriA);
    private static final OWLClass clsB = Class(iri("B"));
    private static final OWLDataProperty dp = DataProperty(iri("p"));
    private static final OWLDataProperty dq = DataProperty(iri("q"));
    private static final OWLObjectProperty op = ObjectProperty(iri("op"));
    private static final OWLObjectProperty oq = ObjectProperty(iri("oq"));
    private static final OWLDataProperty dpA = DataProperty(iri("dpropA"));
    private static final OWLDataProperty dpB = DataProperty(iri("dpropB"));
    private static final OWLDataProperty dpC = DataProperty(iri("dpropC"));
    private static final OWLObjectProperty pA = ObjectProperty(iri("propA"));
    private static final OWLObjectProperty pB = ObjectProperty(iri("propB"));
    private static final OWLObjectProperty pC = ObjectProperty(iri("propC"));
    private static final OWLObjectProperty pD = ObjectProperty(iri("propD"));
    private static final OWLAnnotationProperty apropA = AnnotationProperty(iri("apropA"));
    private static final OWLAnnotationProperty apropB = AnnotationProperty(iri("apropB"));
    private static final OWLNamedIndividual ind = NamedIndividual(iri("i"));
    private static final OWLNamedIndividual indj = NamedIndividual(iri("j"));
    private static final OWLEntity peter =
        NamedIndividual(IRI("http://www.another.com/ont#", "peter"));
    private static final OWLAnnotation ann1 = Annotation(RDFSLabel(), Literal("Annotation 1"));
    private static final OWLAnnotation ann2 = Annotation(RDFSLabel(), Literal("Annotation 2"));
    private static final OWLAnnotation eAnn1 =
        Annotation(RDFSLabel(), Literal("EntityAnnotation 1"));
    private static final OWLAnnotation eAnn2 =
        Annotation(RDFSLabel(), Literal("EntityAnnotation 2"));
    private static final OWLDatatype datatype =
        Datatype(IRI("http://www.ont.com/myont/", "mydatatype"));
    private static final OWLAnnotation annoOuterOuter1 =
        Annotation(AnnotationProperty(iri("myOuterOuterLabel1")), Literal("Outer Outer label 1"));
    private static final OWLAnnotation annoOuterOuter2 =
        Annotation(AnnotationProperty(iri("myOuterOuterLabel2")), Literal("Outer Outer label 2"));
    private static final OWLDatatype dt = Datatype(IRI("file:/c/test.owlapi#", "SSN"));
    private static final OWLFacetRestriction fr =
        FacetRestriction(OWLFacet.PATTERN, Literal("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
    private static final OWLDataRange dr =
        DatatypeRestriction(Datatype(IRI("http://www.w3.org/2001/XMLSchema#", "string")), fr);
    private static final OWLDataIntersectionOf disj1 = DataIntersectionOf(DataComplementOf(dr), dt);
    private static final OWLDataIntersectionOf disj2 = DataIntersectionOf(DataComplementOf(dt), dr);
    private static final OWLAnnotation annoOuter =
        Annotation(AnnotationProperty(iri("myOuterLabel")), Literal("Outer label"), annoOuterOuter1,
            annoOuterOuter2);
    private static final OWLAnnotation annoInner =
        Annotation(AnnotationProperty(iri("myLabel")), Literal("Label"), annoOuter);

    public AxiomsRoundTrippingTestCase(AxiomBuilder f) {
        super(f);
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
        //@formatter:off
            (AxiomBuilder) () -> swrl("http://www.owlapi#", df),
            () -> swrl(VAR, df),
            () -> Arrays.asList(SubPropertyChainOf(Arrays.asList(pA, pB, pC), pD)),
            () -> Arrays.asList(AsymmetricObjectProperty(op)),
            () -> Arrays.asList(DifferentIndividuals(createIndividual(), createIndividual(), createIndividual(), createIndividual(), createIndividual(), createIndividual(), createIndividual(), createIndividual(), createIndividual(), createIndividual())),
            () -> Arrays.asList( SubClassOf(clsA, ObjectSomeValuesFrom(op, ObjectSomeValuesFrom(op, clsB))), Declaration(clsA), Declaration(clsB)),
            () -> Arrays.asList(Declaration(RDFSLabel()), Declaration(peter), AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)),
            () -> Arrays.asList(Declaration(RDFSLabel()), Declaration(peter, eAnn1, eAnn2), AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)),
            () -> Arrays.asList(InverseObjectProperties(oq, op)),
            () -> Arrays.asList(InverseObjectProperties(op, oq)),
            () -> Arrays.asList(Declaration(clsA), AnnotationAssertion(apropA, clsA.getIRI(),     IRI("http://www.semanticweb.org/owlapi#", "object"))),
            () -> Arrays.asList(SubClassOf(clsA, clsB, Arrays.asList(annoInner))),
            () -> Arrays.asList(AnnotationPropertyDomain(RDFSComment(), iriA)),
            () -> Arrays.asList(AnnotationPropertyRange(RDFSComment(), iriA)),
            () -> Arrays.asList(SubAnnotationPropertyOf(apropA, RDFSLabel())),
            () -> Arrays.asList(SubClassOf(clsA, DataMaxCardinality(3, dp, Integer()))),
            () -> Arrays.asList(SubClassOf(clsA, DataMinCardinality(3, dp, Integer()))),
            () -> Arrays.asList(SubClassOf(clsA, DataExactCardinality(3, dp, Integer()))),
            () -> Arrays.asList(DataPropertyRange(dp, DataUnionOf(disj1, disj2))),
            () -> Arrays.asList( HasKey(singleton(Annotation(apropA, Literal("Test", ""))), clsA, pA, pB, pC), Declaration(apropA), Declaration(pA), Declaration(pB), Declaration(pC)),
            () -> Arrays.asList( DisjointClasses(asUnorderedSet(Stream.generate(() -> createClass()).limit(6000)))),
            () -> Arrays.asList(SubClassOf(clsB, ObjectSomeValuesFrom(op.getInverseProperty(), clsA))),
            () -> Arrays.asList(SubDataPropertyOf(dp, dq)),
            () -> Arrays.asList(DataPropertyAssertion(dp, ind, Literal(33.3))),
            () -> Arrays.asList(NegativeDataPropertyAssertion(dp, ind, Literal(33.3)), NegativeDataPropertyAssertion(dp, ind, Literal("weasel", "")), NegativeDataPropertyAssertion(dp, ind, Literal("weasel"))),
            () -> Arrays.asList(FunctionalDataProperty(dp)),
            () -> Arrays.asList(DataPropertyDomain(dp, Class(iri("A")))),
            () -> Arrays.asList(DataPropertyRange(dp, TopDatatype())),
            () -> Arrays.asList(DisjointDataProperties(dpA, dpB, dpC), Declaration(dpA), Declaration(dpB), Declaration(dpC)),
            () -> Arrays.asList(DisjointDataProperties(dpA, dpB)),
            () -> Arrays.asList(EquivalentDataProperties(dp, dq)),
            () -> Arrays.asList(AsymmetricObjectProperty(op)),
            () -> Arrays.asList(DatatypeDefinition(datatype, DataComplementOf(Integer())), Declaration(datatype)),
            () -> Arrays.asList(DifferentIndividuals(ind, indj), DifferentIndividuals(ind, NamedIndividual(iri("k")))),
            () -> Arrays.asList(DifferentIndividuals(ind, indj, NamedIndividual(iri("k")), NamedIndividual(iri("l")))),
            () -> Arrays.asList(DisjointObjectProperties(pA, pB, pC), Declaration(pA), Declaration(pB), Declaration(pC)),
            () -> Arrays.asList(DisjointObjectProperties(pA, pB)),
            () -> Arrays.asList(EquivalentObjectProperties(pA, pB), Declaration(pA), Declaration(pB)),
            () -> Arrays.asList(FunctionalObjectProperty(op)),
            () -> Arrays.asList(InverseFunctionalObjectProperty(op)),
            () -> Arrays.asList(IrreflexiveObjectProperty(op)),
            () -> Arrays.asList(DifferentIndividuals( asUnorderedSet(Stream.generate(() -> createIndividual()).limit(1000)))),
            () -> Arrays.asList(AnnotationAssertion(apropA, clsA.getIRI(), Literal("abc", "en")), Declaration(clsA)),
            () -> Arrays.asList(AnnotationAssertion(apropA, iriA, Literal("abc", "en")), AnnotationAssertion(apropA, iriA, Literal("abcd", "")), AnnotationAssertion(apropA, iriA, Literal("abcde")), AnnotationAssertion(apropA, iriA, Literal("abcdef", OWL2Datatype.XSD_STRING)), Declaration(clsA)),
            () -> Arrays.asList(NegativeObjectPropertyAssertion(op, ind, indj)),
            () -> Arrays.asList(ObjectPropertyAssertion(op, ind, indj)),
            () -> Arrays.asList(SubPropertyChainOf(Arrays.asList(pA, pB, pC), pD, Arrays.asList(Annotation(apropA, Literal("Test", "en")),     Annotation(apropB, Literal("Test", ""))))),
            () -> Arrays.asList(ObjectPropertyDomain(op, clsA)),
            () -> Arrays.asList(ObjectPropertyRange(op, clsA)),
            () -> Arrays.asList( Declaration(Class(IRI("http://www.test.com/ontology#", "Class%37A"))), Declaration(ObjectProperty(IRI("http://www.test.com/ontology#", "prop%37A")))),
            () -> Arrays.asList(ReflexiveObjectProperty(op)),
            () -> Arrays.asList(SameIndividual(ind, indj)),
            () -> Arrays.asList(DataPropertyAssertion(dp, ind, Literal("Test \"literal\"\nStuff"))),
            () -> Arrays.asList(DataPropertyAssertion(dp, ind, Literal("Test \"literal\"")), DataPropertyAssertion(dp, ind, Literal("Test 'literal'")), DataPropertyAssertion(dp, ind, Literal("Test \"\"\"literal\"\"\""))),
            () -> Arrays.asList(SubObjectPropertyOf(op, oq)),
            () -> Arrays.asList(SymmetricObjectProperty(op)),
            () -> Arrays.asList(TransitiveObjectProperty(op)),
            () -> Arrays.asList(DataPropertyAssertion(dp, ind, Literal(3)), DataPropertyAssertion(dp, ind, Literal(33.3)), DataPropertyAssertion(dp, ind, Literal(true)), DataPropertyAssertion(dp, ind, Literal(33.3f)), DataPropertyAssertion(dp, ind, Literal("33.3")))
            );
        //@formatter:on
    }

    protected static List<OWLAxiom> swrl(String namespace, OWLDataFactory df2) {
        List<OWLAxiom> axioms = new ArrayList<>();
        SWRLVariable varX = df2.getSWRLVariable(VAR, "x");
        SWRLVariable varY = df2.getSWRLVariable(VAR, "y");
        SWRLVariable varZ = df2.getSWRLVariable(VAR, "z");
        Set<SWRLAtom> body = new HashSet<>();
        body.add(df2.getSWRLClassAtom(Class(iri("A")), varX));
        SWRLIndividualArgument indIArg = df2.getSWRLIndividualArgument(ind);
        SWRLIndividualArgument indJArg = df2.getSWRLIndividualArgument(indj);
        body.add(df2.getSWRLClassAtom(Class(iri("D")), indIArg));
        body.add(df2.getSWRLClassAtom(Class(iri("B")), varX));
        SWRLVariable varQ = df2.getSWRLVariable(VAR, "q");
        SWRLVariable varR = df2.getSWRLVariable(VAR, "r");
        body.add(df2.getSWRLDataPropertyAtom(dp, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = df2.getSWRLLiteralArgument(lit);
        body.add(df2.getSWRLDataPropertyAtom(dp, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(df2.getSWRLClassAtom(Class(iri("C")), varX));
        head.add(df2.getSWRLObjectPropertyAtom(op, varY, varZ));
        head.add(df2.getSWRLSameIndividualAtom(varX, varY));
        head.add(df2.getSWRLSameIndividualAtom(indIArg, indJArg));
        head.add(df2.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df2.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df2.getSWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, Class(iri("A")));
        head.add(df2.getSWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(df2.getSWRLBuiltInAtom(IRI(namespace, "myBuiltIn"), args));
        axioms.add(df2.getSWRLRule(body, head));
        return axioms;
    }
}
