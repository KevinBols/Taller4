package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class PunningAndDomainsRangesTestCase extends TestBase {

    @Test
    void shouldKeepDomainsInFSS() {
        OWLOntology o = create(iri("ontology"));
        OWLAnnotationProperty ann = AnnotationProperty(DATAPROPS.DP.getIRI());
        o.addAxiom(AnnotationPropertyRange(ann, OWL2Datatype.RDFS_LITERAL.getIRI()));
        o.addAxiom(DataPropertyRange(DATAPROPS.DP, RDFSLiteral()));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }

    @Test
    void shouldSupportPunningClassesAndPropertiesInManchesterSyntax() {
        OWLOntology o = createAnon();
        o.addAxiom(Declaration(ObjectProperty(CLASSES.B.getIRI())));
        o.addAxiom(Declaration(CLASSES.B));
        o.addAxiom(Declaration(CLASSES.A));
        ManchesterOWLSyntaxParserImpl parser =
            (ManchesterOWLSyntaxParserImpl) OWLManager.createManchesterParser();
        parser.getPrefixManager().withDefaultPrefix(OWLAPI_TEST);
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, ObjectUnionOf(CLASSES.A, CLASSES.B));
    }
}
