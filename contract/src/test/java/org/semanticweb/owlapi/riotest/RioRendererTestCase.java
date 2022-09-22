/**
 *
 */
package org.semanticweb.owlapi.riotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.rio.RioManchesterSyntaxParserFactory;
import org.semanticweb.owlapi.rio.RioNTriplesStorerFactory;
import org.semanticweb.owlapi.rio.RioRDFXMLStorerFactory;
import org.semanticweb.owlapi.rio.RioRenderer;
import org.semanticweb.owlapi.rio.RioTurtleStorerFactory;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class RioRendererTestCase extends TestBase {

    private static final String DUPLICATE_STATEMENTS = "Duplicate statements were emitted";
    private final IRI testOntologyUri1 = iri("urn:test:ontology:uri:1", "");
    private SimpleValueFactory vf;
    private OWLOntology testOntologyEmpty;
    private OWLOntology testOntologyKoala;
    private Statement testOntologyEmptyStatement;
    private StatementCollector testHandlerStatementCollector;
    private StringWriter testRdfXmlStringWriter;
    private RDFWriter testRdfXmlRioWriter;
    private StringWriter testTurtleStringWriter;
    private RDFWriter testTurtleRioWriter;
    private StringWriter testNTriplesStringWriter;
    private RDFWriter testNTriplesRioWriter;
    private final RioRDFXMLDocumentFormat format = new RioRDFXMLDocumentFormat();

    @BeforeEach
    void setUp() throws Exception {
        vf = SimpleValueFactory.getInstance();
        m.getOntologyStorers().set(new RioNTriplesStorerFactory(), new RioRDFXMLStorerFactory(),
            new RioTurtleStorerFactory());
        testOntologyEmpty = m.createOntology(testOntologyUri1);
        File in = new File(getClass().getResource("/koala.owl").toURI());
        testOntologyKoala = m.loadOntologyFromOntologyDocument(in);
        assertEquals(70, testOntologyKoala.getAxiomCount());
        testHandlerStatementCollector = new StatementCollector();
        testOntologyEmptyStatement =
            vf.createStatement(vf.createIRI("urn:test:ontology:uri:1"), RDF.TYPE, OWL.ONTOLOGY);
        testRdfXmlStringWriter = new StringWriter();
        testRdfXmlRioWriter = Rio.createWriter(RDFFormat.RDFXML, testRdfXmlStringWriter);
        testTurtleStringWriter = new StringWriter();
        testTurtleRioWriter = Rio.createWriter(RDFFormat.TURTLE, testTurtleStringWriter);
        testNTriplesStringWriter = new StringWriter();
        testNTriplesRioWriter = Rio.createWriter(RDFFormat.NTRIPLES, testNTriplesStringWriter);
    }

    @Test
    void testRenderEmptyStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyEmpty, format, testHandlerStatementCollector);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(1, testHandlerStatementCollector.getStatements().size());
        // Verify that the RDF:TYPE OWL:ONTOLOGY statement was generated for the
        // otherwise empty
        // ontology
        assertEquals(testOntologyEmptyStatement,
            testHandlerStatementCollector.getStatements().iterator().next());
    }

    @Test
    void testRenderEmptyRdfXmlWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, format, testRdfXmlRioWriter);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        assertTrue(result.length() > 560, "Result was smaller than expected:" + result);
        assertTrue(result.length() < 590, "Result was larger than expected:" + result);
    }

    @Test
    void testRenderEmptyTurtleWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, format, testTurtleRioWriter);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        assertTrue(result.length() > 420, "Result was smaller than expected:" + result);
        assertTrue(result.length() < 450, "Result was larger than expected:" + result);
    }

    @Test
    void testRenderEmptyNTriplesWriter() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyEmpty, format, testNTriplesRioWriter);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        assertTrue(result.length() > 190, "Result was smaller than expected:" + result);
        assertTrue(result.length() < 220, "Result was larger than expected:" + result);
    }

    @Test
    void testRenderKoalaStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyKoala, format, testHandlerStatementCollector);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRenderKoalaRdfXmlWriter() throws RDFParseException, RDFHandlerException, IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, format, testRdfXmlRioWriter);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 23500,
            "result.length()=" + result.length() + " was not inside the expected bounds");
        assertTrue(result.length() < 26000,
            "result.length()=" + result.length() + " was not inside the expected bounds");
        RDFParser parser = Rio.createParser(RDFFormat.RDFXML, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        // NOTE: The base xmlns: does not get counted as a namespace by the Rio
        // RDFXML parser, which
        // is why it counts to 5, compared to direct StatementCollector result
        // and the turtle result
        assertEquals(5, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRenderKoalaTurtleWriter() throws RDFParseException, RDFHandlerException, IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, format, testTurtleRioWriter);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 7500,
            "result.length()=" + result.length() + " was not inside the expected bounds");
        assertTrue(result.length() < 9500,
            "result.length()=" + result.length() + " was not inside the expected bounds");
        RDFParser parser = Rio.createParser(RDFFormat.TURTLE, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRenderKoalaNTriplesWriter()
        throws RDFParseException, RDFHandlerException, IOException {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyKoala, format, testNTriplesRioWriter);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 25500,
            "result.length()=" + result.length() + " was not inside the expected bounds");
        assertTrue(result.length() < 27500,
            "result.length()=" + result.length() + " was not inside the expected bounds");
        RDFParser parser = Rio.createParser(RDFFormat.NTRIPLES, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        // NTriples does not contain namespaces, so we will not find any when
        // parsing the document
        assertEquals(0, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRioOWLRDFParser() throws RDFParseException, RDFHandlerException, IOException {
        RDFParser parser = new RioManchesterSyntaxParserFactory().getParser();
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(TestFiles.inputManSyntax),
            "http://www.owl-ontologies.com/Ontology1307394066.owl");
        assertEquals(36, testHandlerStatementCollector.getStatements().size());
    }
}
