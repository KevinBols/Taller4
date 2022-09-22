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
package org.semanticweb.owlapi.apitest.syntax;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
class OWLXMLNullPointerTestCase extends TestBase {

    @Test
    void testRoundTrip() {
        OWLOntology ontology = create(iri("urn:test", ""));
        OWLAnonymousIndividual anonInd = AnonymousIndividual();
        OWLLiteral lit = Literal("Anonymous individual for testing");
        OWLAxiom annAss = AnnotationAssertion(RDFSLabel(), anonInd, lit);
        OWLAxiom classAss = ClassAssertion(CLASSES.cheesy, anonInd);
        OWLIndividual j = AnonymousIndividual();
        OWLAxiom classAssj = ClassAssertion(CLASSES.cheese, j);
        OWLAxiom objAss = ObjectPropertyAssertion(OBJPROPS.hasTopping, anonInd, j);
        ontology.add(annAss, classAss, classAssj, objAss);
        roundTrip(ontology, new OWLXMLDocumentFormat());
    }

    @Test
    void shouldParse() {
        OWLOntology o = create(IRIS.iriTest);
        o.addAxiom(SubClassOf(CLASSES.C, ObjectHasValue(OBJPROPS.P, AnonymousIndividual())));
        OWLOntology roundtrip = roundTrip(o, new OWLXMLDocumentFormat());
        equal(o, roundtrip);
    }
}
