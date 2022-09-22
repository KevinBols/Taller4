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
package org.semanticweb.owlapi.apitest.annotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class AnnotationPropertyReferencesTestCase extends TestBase {

    @Test
    void testContainsReferenceForAnnotationAssertion() {
        OWLAnnotationAssertionAxiom ax =
            AnnotationAssertion(ANNPROPS.AP, CLASSES.A.getIRI(), LITERALS.val);
        OWLOntology ont = create("Ont");
        ont.addAxiom(ax);
        assertTrue(ont.containsAnnotationPropertyInSignature(ANNPROPS.AP.getIRI()));
        assertTrue(ont.annotationPropertiesInSignature().anyMatch(ANNPROPS.AP::equals));
    }

    @Test
    void testContainsReferenceForAxiomAnnotation() {
        OWLAnnotation anno = Annotation(ANNPROPS.AP, LITERALS.val);
        OWLOntology ont = create("Ont");
        ont.addAxiom(SubClassOf(anno, CLASSES.A, CLASSES.B));
        assertTrue(ont.containsAnnotationPropertyInSignature(anno.getProperty().getIRI()));
        assertTrue(ont.annotationPropertiesInSignature().anyMatch(ANNPROPS.AP::equals));
    }

    @Test
    void testContainsReferenceForOntologyAnnotation() {
        OWLAnnotation anno = Annotation(ANNPROPS.AP, LITERALS.val);
        OWLOntology ont = create("Ont");
        ont.applyChange(new AddOntologyAnnotation(ont, anno));
        assertTrue(ont.containsAnnotationPropertyInSignature(ANNPROPS.AP.getIRI(), EXCLUDED));
        assertTrue(ont.annotationPropertiesInSignature(EXCLUDED).anyMatch(ANNPROPS.AP::equals));
    }
}
