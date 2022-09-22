package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.apitest.TestFiles.TO_DL;
import static org.semanticweb.owlapi.apitest.TestFiles.TO_FUNCTIONAL;
import static org.semanticweb.owlapi.apitest.TestFiles.TO_LATEX;
import static org.semanticweb.owlapi.apitest.TestFiles.TO_MANCHESTER;
import static org.semanticweb.owlapi.apitest.TestFiles.TO_MANCHESTER_PREFIX;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.LatexDocumentFormat;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.utilities.PrefixManagerImpl;

class ToSyntaxTestCase extends TestBase {

    OWLClassExpression expression = ObjectIntersectionOf(ObjectAllValuesFrom(OBJPROPS.P, CLASSES.A),
        ObjectSomeValuesFrom(OBJPROPS.Q, CLASSES.B));

    @Test
    void shouldFormatToFunctional() {
        assertEquals(TO_FUNCTIONAL, expression.toFunctionalSyntax());
    }

    @Test
    void shouldFormatToDL() {
        assertEquals(TO_DL, expression.toSyntax(new DLSyntaxDocumentFormat()));
    }

    @Test
    void shouldFormatToManchester() {
        assertEquals(TO_MANCHESTER, expression.toManchesterSyntax());
        PrefixManager pm = new PrefixManagerImpl().withDefaultPrefix(OWLAPI_TEST);
        assertEquals(TO_MANCHESTER_PREFIX, expression.toManchesterSyntax(pm));
    }

    @Test
    void shouldFormatToLatex() {
        assertEquals(TO_LATEX, expression.toSyntax(new LatexDocumentFormat()));
    }

    @Test
    void shouldFormatToSimple() {
        assertEquals(TO_FUNCTIONAL, expression.toString());
    }
}
