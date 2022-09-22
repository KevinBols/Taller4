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
package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.io.OWLParserFactory;

/**
 * Test the generic factory and all the formats it can build.
 *
 * @author ignazio
 */
class FormatsMimeTestCase {

    private static final String X_TRIG = "application/x-trig";
    private static final String HTML = "text/html";
    private static final String RDF_N3 = "text/rdf+n3";
    private static final String APP_JSON = "application/ld+json";
    private static final String APP_X_BINARY_RDF = "application/x-binary-rdf";
    private static final String FS = "text/owl-functional";
    private static final String MANCH = "text/owl-manchester";
    private static final String APP_TRIG = "application/trig";
    private static final String PLAIN = "text/plain";
    private static final String NQUADS = "text/nquads";
    private static final String X_NQUADS = "text/x-nquads";
    private static final String APP_HTML = "application/html";
    private static final String APP_XHTML = "application/xhtml+xml";
    private static final String APP_NT = "application/n-triples";
    private static final String APP_OWL_XML = "application/owl+xml";
    private static final String N3 = "text/n3";
    private static final String APP_RDF_JSON = "application/rdf+json";
    private static final String APP_TRIX = "application/trix";
    private static final String APP_X_TURTLE = "application/x-turtle";
    private static final String XML = "text/xml";
    private static final String APP_XML = "application/xml";
    private static final String TURTLE = "text/turtle";
    private static final String APP_N_QUADS = "application/n-quads";
    private static final String APP_RDF_XML = "application/rdf+xml";

    public static Stream<Arguments> params() {
        return Stream.of(
        //@formatter:off
            of(new org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParserFactory(),                           "OBO Format",            null,             Collections.emptyList()),
            of(new org.semanticweb.owlapi.krss2.parser.KRSS2OWLParserFactory(),                               "KRSS2 Syntax",          null,             Collections.emptyList()),
            of(new org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxOWLParserFactory(),                         "DL Syntax Format",      null,             Collections.emptyList()),
            of(new org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory(),                            "RDF/XML Syntax",        APP_RDF_XML,      Arrays.asList(APP_RDF_XML, APP_XML,  XML)),
            of(new org.semanticweb.owlapi.rio.RioRDFXMLParserFactory(),                                       "RDF/XML",               APP_RDF_XML,      Arrays.asList(APP_RDF_XML, APP_XML,  XML)),
            of(new org.semanticweb.owlapi.rio.RioNQuadsParserFactory(),                                       "N-Quads",               APP_N_QUADS,      Arrays.asList(APP_N_QUADS, X_NQUADS, NQUADS)),
            of(new org.semanticweb.owlapi.rio.RioRDFaParserFactory(),                                         "RDFa",                  APP_XHTML,        Arrays.asList(APP_XHTML,   APP_HTML, HTML)),
            of(new org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory(),                    "Turtle Syntax",         TURTLE,           Arrays.asList(TURTLE,      APP_X_TURTLE)),
            of(new org.semanticweb.owlapi.rio.RioTurtleParserFactory(),                                       "Turtle",                TURTLE,           Arrays.asList(TURTLE,      APP_X_TURTLE)),
            of(new org.semanticweb.owlapi.rio.RioN3ParserFactory(),                                           "N3",                    N3,               Arrays.asList(N3,          RDF_N3)),
            of(new org.semanticweb.owlapi.owlxml.parser.OWLXMLParserFactory(),                                "OWL/XML Syntax",        APP_OWL_XML,      Arrays.asList(APP_OWL_XML, XML)),
            of(new org.semanticweb.owlapi.rio.RioNTriplesParserFactory(),                                     "N-Triples",             APP_NT,           Arrays.asList(APP_NT,      PLAIN)),
            of(new org.semanticweb.owlapi.rio.RioTrigParserFactory(),                                         "TriG",                  APP_TRIG,         Arrays.asList(APP_TRIG,    X_TRIG)),
            of(new org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory(), "Manchester OWL Syntax", MANCH,            Arrays.asList(MANCH)),
            of(new org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParserFactory(),            "OWL Functional Syntax", FS,               Arrays.asList(FS)),
            of(new org.semanticweb.owlapi.rio.RioBinaryRdfParserFactory(),                                    "BinaryRDF",             APP_X_BINARY_RDF, Arrays.asList(APP_X_BINARY_RDF)),
            of(new org.semanticweb.owlapi.rio.RioJsonLDParserFactory(),                                       "JSON-LD",               APP_JSON,         Arrays.asList(APP_JSON)),
            of(new org.semanticweb.owlapi.rio.RioJsonParserFactory(),                                         "RDF/JSON",              APP_RDF_JSON,     Arrays.asList(APP_RDF_JSON)),
            of(new org.semanticweb.owlapi.rio.RioTrixParserFactory(),                                         "TriX",                  APP_TRIX,         Arrays.asList(APP_TRIX))
            //@formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldMatchExpectedValues(OWLParserFactory f, String key, String defaultmime,
        List<String> mimes) {
        assertEquals(key, f.getSupportedFormat().getKey());
        assertEquals(mimes, f.getMIMETypes());
        assertEquals(defaultmime, f.getDefaultMIMEType());
    }
}
