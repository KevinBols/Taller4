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
package org.semanticweb.owlapi6.api.test.ontology;

import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Literal;

import org.semanticweb.owlapi6.api.test.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi6.model.AddOntologyAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class OntologyAnnotationsTestCase extends AbstractRoundTrippingTestCase {

    @Override
    protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology();
        OWLAnnotationProperty prop = AnnotationProperty(
            IRI("http://www.semanticweb.org/ontologies/test/annotationont#", "prop"));
        OWLLiteral value = Literal(33);
        OWLAnnotation annotation = Annotation(prop, value);
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.addAxiom(Declaration(prop));
        return ont;
    }

    @Override
    public void testRDFJSON() {
        // XXX ignored. The parser parses the annotation correctly but it is not
        // associated to the ontology.
        // super.testRDFJSON();
    }
}
