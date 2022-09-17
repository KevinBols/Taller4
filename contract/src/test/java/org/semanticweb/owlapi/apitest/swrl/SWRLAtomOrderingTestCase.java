package org.semanticweb.owlapi.apitest.swrl;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apitest.TestEntities.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class SWRLAtomOrderingTestCase extends TestBase {

    protected SWRLAtom atomA;
    protected SWRLAtom atomB;
    protected SWRLAtom atomC;
    protected SWRLAtom atomD;
    private SWRLRule rule;
    private final Set<SWRLAtom> body = new LinkedHashSet<>();

    @Before
    public void setUp() {
        atomA = df.getSWRLClassAtom(A,
            df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("i"))));
        atomB = df.getSWRLClassAtom(A,
            df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("j"))));
        atomC = df.getSWRLClassAtom(A,
            df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("k"))));
        atomD = df.getSWRLClassAtom(A,
            df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("l"))));
        body.add(atomC);
        body.add(atomB);
        body.add(atomA);
        Set<SWRLAtom> head = new LinkedHashSet<>();
        head.add(atomD);
        rule = df.getSWRLRule(body, head, Collections.<OWLAnnotation>emptySet());
    }

    @Test
    public void shouldPreserveBodyOrdering() {
        List<SWRLAtom> ruleImplBody = rule.bodyList();
        List<SWRLAtom> specifiedBody = new ArrayList<>(body);
        assertEquals(specifiedBody, ruleImplBody);
    }
}