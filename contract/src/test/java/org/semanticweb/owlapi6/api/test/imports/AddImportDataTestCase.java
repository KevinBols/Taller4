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
package org.semanticweb.owlapi6.api.test.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.semanticweb.owlapi6.change.AddImportData;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLOntology;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.2.0
 */
public class AddImportDataTestCase {

    private final OWLImportsDeclaration mockDeclaration = mock(OWLImportsDeclaration.class);
    private final OWLOntology mockOntology = mock(OWLOntology.class);

    private AddImportData createData() {
        return new AddImportData(mockDeclaration);
    }

    @Test
    public void testEquals() {
        AddImportData data1 = createData();
        AddImportData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        AddImportData data = createData();
        assertNotNull(data.getDeclaration());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        AddImportData data = createData();
        assertEquals(mockDeclaration, data.getDeclaration());
    }

    @Test
    public void testCreateOntologyChange() {
        AddImportData data = createData();
        AddImport change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockDeclaration, change.getImportDeclaration());
    }

    @Test
    public void testOntologyChangeSymmetry() {
        AddImportData data = createData();
        AddImport change = new AddImport(mockOntology, mockDeclaration);
        assertEquals(change.getChangeData(), data);
    }
}
