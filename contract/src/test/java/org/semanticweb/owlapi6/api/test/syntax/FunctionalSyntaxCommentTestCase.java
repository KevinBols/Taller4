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
package org.semanticweb.owlapi6.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.io.StringDocumentSource;
import org.semanticweb.owlapi6.io.StringDocumentTarget;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

public class FunctionalSyntaxCommentTestCase extends TestBase {

    @Test
    public void shouldParseCommentAndSkipIt() {
        String input =
            "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\nPrefix(owl:=<http://www.w3.org/2002/07/owl#>)\nPrefix(xml:=<http://www.w3.org/XML/1998/namespace>)\nPrefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\nPrefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\nPrefix(skos:=<http://www.w3.org/2004/02/skos/core#>)\n\n"
                + "Ontology(<file:test.owl>\n"
                + "Declaration(Class(<urn:test.owl#ContactInformation>))\n" + "#Test comment\n"
                + "Declaration(DataProperty(<urn:test.owl#city>))\n"
                + "SubClassOf(<urn:test.owl#ContactInformation> DataMaxCardinality(1 <urn:test.owl#city> xsd:string))\n"
                + ')';
        OWLOntology o = loadOntologyFromString(input, new FunctionalSyntaxDocumentFormat());
        OWLAxiom ax1 = Declaration(DataProperty(IRI("urn:test.owl#", "city")));
        OWLAxiom ax2 = SubClassOf(Class(IRI("urn:test.owl#", "ContactInformation")),
            DataMaxCardinality(1, DataProperty(IRI("urn:test.owl#", "city")),
                Datatype(OWL2Datatype.XSD_STRING.getIRI())));
        OWLAxiom ax3 = Declaration(Class(IRI("urn:test.owl#", "ContactInformation")));
        assertTrue(o.containsAxiom(ax1));
        assertTrue(o.containsAxiom(ax2));
        assertTrue(o.containsAxiom(ax3));
    }

    @Test
    public void shouldSaveMultilineComment()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String output = "Prefix(:=<file:test.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n"
            + "\n\nOntology(<file:test.owl>\n\n" + "Declaration(Annotation(rdfs:label \"blah \n"
            + "blah\")  Class(<urn:test.owl#ContactInformation>))\n"
            + "Declaration(DataProperty(<urn:test.owl#city>))\n"
            + "\n\n\n############################\n#   Classes\n############################\n\n"
            + "# Class: <urn:test.owl#ContactInformation> (blah \n# blah)\n\n"
            + "AnnotationAssertion(rdfs:label <urn:test.owl#ContactInformation> \"blah \n"
            + "blah\")\n"
            + "SubClassOf(<urn:test.owl#ContactInformation> DataMaxCardinality(1 <urn:test.owl#city> xsd:string))\n\n\n)";
        OWLOntology o = m.createOntology(df.getIRI("file:test.owl"));
        o.addAxiom(df.getOWLAnnotationAssertionAxiom(IRI("urn:test.owl#ContactInformation"),
            Annotation(RDFSLabel(), Literal("blah \nblah"))));
        o.addAxiom(Declaration(DataProperty(IRI("urn:test.owl#city"))));
        o.addAxiom(SubClassOf(Class(IRI("urn:test.owl#ContactInformation")), DataMaxCardinality(1,
            DataProperty(IRI("urn:test.owl#city")), Datatype(OWL2Datatype.XSD_STRING.getIRI()))));
        o.addAxiom(Declaration(Class(IRI("urn:test.owl#ContactInformation")), new HashSet<>(Arrays
            .asList(df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("blah \nblah"))))));
        StringDocumentTarget saveOntology = saveOntology(o, new FunctionalSyntaxDocumentFormat());
        assertEquals(output, saveOntology.toString());
        OWLOntology loadOntologyFromString =
            loadOntologyFromString(saveOntology.toString(), new FunctionalSyntaxDocumentFormat());
        equal(o, loadOntologyFromString);
    }

    @Test
    public void shouldParseCardinalityRestrictionWithMoreThanOneDigitRange()
        throws OWLOntologyCreationException {
        String in = "Prefix(:=<urn:test#>)" + "Prefix(a:=<urn:test#>)"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)"
            + "Prefix(owl2xml:=<http://www.w3.org/2006/12/owl2-xml#>)" + "Prefix(test:=<urn:test#>)"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Ontology(<urn:test>\n" + "Declaration(NamedIndividual(test:a)) \n"
            + "    Declaration(Class(test:A)) \n" + "    Declaration(DataProperty(test:dp)) \n"
            + "    SubClassOf( test:A DataMinCardinality( 257 test:dp rdfs:Literal ) ) \n"
            + "    SubClassOf( test:A DataAllValuesFrom( test:dp xsd:byte ) ) \n"
            + "    ClassAssertion( test:A test:a ))";
        OWLOntology o = loadOntologyFromString(
            new StringDocumentSource(in, new FunctionalSyntaxDocumentFormat()));
        OWLClass a = df.getOWLClass(df.getIRI("urn:test#", "A"));
        OWLDataProperty p = df.getOWLDataProperty(df.getIRI("urn:test#", "dp"));
        assertTrue(o.containsAxiom(df.getOWLSubClassOfAxiom(a,
            df.getOWLDataMinCardinality(257, p, OWL2Datatype.RDFS_LITERAL.getDatatype(df)))));
    }

    @Test
    public void testConvertGetLoadedOntology() {
        String input =
            "Prefix(:=<http://www.example.org/#>)\nOntology(<http://example.org/>\nSubClassOf(:a :b) )";
        OWLOntology origOnt = loadOntologyFromString(input, new FunctionalSyntaxDocumentFormat());
        assertNotNull(origOnt);
        OWLOntologyManager manager = origOnt.getOWLOntologyManager();
        assertEquals(1, manager.ontologies().count());
        assertFalse(origOnt.getOntologyID().getVersionIRI().isPresent());
        assertTrue(origOnt.getAxiomCount() > 0);
        Optional<IRI> ontologyIRI = origOnt.getOntologyID().getOntologyIRI();
        assertTrue(ontologyIRI.isPresent());
        OWLOntology newOnt = manager.getOntology(get(ontologyIRI));
        assertNotNull(newOnt);
    }
}
