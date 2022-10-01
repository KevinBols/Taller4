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
package org.semanticweb.owlapi.rdf.rdfxml.renderer;

import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LITERAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_DESCRIPTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.builtInVocabularyIris;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.documents.RDFLiteral;
import org.semanticweb.owlapi.documents.RDFNode;
import org.semanticweb.owlapi.documents.RDFResource;
import org.semanticweb.owlapi.documents.RDFResourceBlankNode;
import org.semanticweb.owlapi.documents.RDFTriple;
import org.semanticweb.owlapi.io.OWLStorerParameters;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.utilities.ShortFormProvider;
import org.semanticweb.owlapi.utilities.XMLUtils;
import org.semanticweb.owlapi.utility.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.utility.VersionInfo;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class RDFXMLRenderer extends RDFRendererBase {

    private static final String PROP_CANNOT_BE_NULL = "prop cannot be null";
    private final RDFXMLWriter writer;
    private final RDFXMLNamespaceManager qnameManager;
    private final ShortFormProvider labelMaker;
    private boolean explicitXsdString;

    /**
     * @param ontology ontology
     * @param w writer
     * @param parameters storer parameters
     */
    public RDFXMLRenderer(OWLOntology ontology, PrintWriter w, OWLStorerParameters parameters) {
        this(ontology, w, verifyNotNull(ontology.getFormat()), parameters);
    }

    /**
     * @param ontology ontology
     * @param w writer
     * @param format format
     * @param parameters storer parameters
     */
    public RDFXMLRenderer(OWLOntology ontology, PrintWriter w, OWLDocumentFormat format,
        OWLStorerParameters parameters) {
        super(checkNotNull(ontology, "ontology cannot be null"), format,
            ontology.getOWLOntologyManager().getOntologyConfigurator());
        checkNotNull(w, "w cannot be null");
        checkNotNull(format, "format cannot be null");
        explicitXsdString = Boolean.parseBoolean(
            parameters.getParameter("force xsd:string on literals", Boolean.FALSE).toString());
        qnameManager = new RDFXMLNamespaceManager(ontology, format);
        String defaultNamespace = qnameManager.getDefaultNamespace();
        String base = base(defaultNamespace);
        XMLWriter xmlWriter = new XMLWriterImpl(w, qnameManager, base, config);
        xmlWriter.setEncoding(parameters.getEncoding());
        writer = new RDFXMLWriter(xmlWriter);
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLAnnotationProperty labelProp = manager.getOWLDataFactory().getRDFSLabel();
        labelMaker = new AnnotationValueShortFormProvider(Collections.singletonList(labelProp),
            prefLangMap, manager);
    }

    private static String base(String defaultNamespace) {
        String base;
        if (defaultNamespace.endsWith("#")) {
            base = defaultNamespace.substring(0, defaultNamespace.length() - 1);
        } else {
            base = defaultNamespace;
        }
        return base;
    }

    @Override
    protected void beginDocument() {
        writer.startDocument();
    }

    @Override
    protected void endDocument() {
        writer.endDocument();
        writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        if (!config.shouldAddMissingTypes()) {
            // missing type declarations could have been omitted, adding a
            // comment to document it
            writer.writeComment("Warning: type declarations were not added automatically.");
        }
    }

    @Override
    protected void writeIndividualComments(OWLNamedIndividual ind) {
        writeCommentForEntity("ind cannot be null", ind);
    }

    @Override
    protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) {
        writeCommentForEntity(PROP_CANNOT_BE_NULL, prop);
    }

    @Override
    protected void writeClassComment(OWLClass cls) {
        writeCommentForEntity("cls cannot be null", cls);
    }

    @Override
    protected void writeDataPropertyComment(OWLDataProperty prop) {
        writeCommentForEntity(PROP_CANNOT_BE_NULL, prop);
    }

    @Override
    protected void writeDatatypeComment(OWLDatatype datatype) {
        writeCommentForEntity("datatype cannot be null", datatype);
    }

    @Override
    protected void writeObjectPropertyComment(OWLObjectProperty prop) {
        writeCommentForEntity(PROP_CANNOT_BE_NULL, prop);
    }

    @Override
    protected void writeBanner(String name) {
        writer.writeComment(
            "\n///////////////////////////////////////////////////////////////////////////////////////\n//\n// "
                + checkNotNull(name, "name cannot be null")
                + "\n//\n///////////////////////////////////////////////////////////////////////////////////////\n");
    }

    private void writeCommentForEntity(String msg, OWLEntity entity) {
        if (config.shouldUseBanners()) {
            checkNotNull(entity, msg);
            String iriString = entity.getIRI().toString();
            if (config.shouldUseLabelsAsBanner()) {
                String labelString = labelMaker.getShortForm(entity);
                String commentString;
                if (!iriString.equals(labelString)) {
                    commentString = labelString;
                } else {
                    commentString = iriString;
                }
                writer.writeComment(XMLUtils.escapeXML(commentString));
            } else {
                writer.writeComment(XMLUtils.escapeXML(iriString));
            }
        }
    }

    @Override
    protected void render(RDFResource node, boolean root) {
        checkNotNull(node, "node cannot be null");
        if (pending.contains(node)) {
            return;
        }
        Collection<RDFTriple> triples = getRDFGraph().getTriplesForSubject(node);
        pending.add(node);
        RDFTriple candidatePrettyPrintTypeTriple = findPrettyPrintCandidate(triples);
        writeElementStart(candidatePrettyPrintTypeTriple);
        writeAboutOrID(node);
        for (RDFTriple triple : triples) {
            if (candidatePrettyPrintTypeTriple != null
                && candidatePrettyPrintTypeTriple.equals(triple)) {
                continue;
            }
            writer.writeStartElement(triple.getPredicate().getIRI());
            RDFNode objectNode = triple.getObject();
            if (!objectNode.isLiteral()) {
                renderResource(node, objectNode);
            } else {
                writew((RDFLiteral) objectNode);
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
        if (root) {
            deferredRendering();
        }
        pending.remove(node);
    }

    protected void renderResource(RDFResource node, RDFNode objectNode) {
        RDFResource objectRes = (RDFResource) objectNode;
        if (objectRes.isAnonymous()) {
            // Special rendering for lists
            if (isObjectList(objectRes)) {
                writer.writeParseTypeAttribute();
                List<RDFNode> list = new ArrayList<>();
                toJavaList(objectRes, list);
                list.forEach(this::renderList);
            } else if (objectRes.equals(node)) {
                // special case for triples with same object and subject
                writer.writeNodeIDAttribute(objectRes);
            } else {
                renderObject(objectRes);
            }
        } else {
            writer.writeResourceAttribute(objectRes.getIRI());
        }
    }

    protected void writeAboutOrID(RDFResource node) {
        if (!node.isAnonymous()) {
            writer.writeAboutAttribute(node.getIRI());
        } else if (node.idRequired()) {
            writer.writeNodeIDAttribute(node);
        }
    }

    protected void writeElementStart(@Nullable RDFTriple candidatePrettyPrintTypeTriple) {
        if (candidatePrettyPrintTypeTriple == null) {
            writer.writeStartElement(RDF_DESCRIPTION.getIRI());
        } else {
            writer.writeStartElement(candidatePrettyPrintTypeTriple.getObject().getIRI());
        }
    }

    protected RDFTriple findPrettyPrintCandidate(Collection<RDFTriple> triples) {
        RDFTriple candidatePrettyPrintTypeTriple = null;
        for (RDFTriple triple : triples) {
            IRI propertyIRI = triple.getPredicate().getIRI();
            if (propertyIRI.equals(RDF_TYPE.getIRI()) && !triple.getObject().isAnonymous()
                && builtInVocabularyIris().contains(triple.getObject().getIRI())
                && prettyPrintedTypes.contains(triple.getObject().getIRI())) {
                candidatePrettyPrintTypeTriple = triple;
            }
        }
        return candidatePrettyPrintTypeTriple;
    }

    protected void renderList(RDFNode n) {
        if (n.isAnonymous()) {
            render((RDFResourceBlankNode) n, false);
        } else {
            if (n.isLiteral()) {
                write((RDFLiteral) n);
            } else {
                writer.writeStartElement(RDF_DESCRIPTION.getIRI());
                writer.writeAboutAttribute(n.getIRI());
                writer.writeEndElement();
            }
        }
    }

    protected void renderObject(RDFResource object) {
        if (object.idRequired()) {
            if (!pending.contains(object)) {
                defer(object);
            }
            writer.writeNodeIDAttribute(object);
        } else {
            render(object, false);
        }
    }

    protected void write(RDFLiteral litNode) {
        writer.writeStartElement(RDFS_LITERAL.getIRI());
        writew(litNode);
        writer.writeEndElement();
    }

    protected void writew(RDFLiteral rdfLiteralNode) {
        if (rdfLiteralNode.hasLang()) {
            writer.writeLangAttribute(rdfLiteralNode.getLang());
        } else if (!rdfLiteralNode.isPlainLiteral() && (explicitXsdString
            || !OWL2Datatype.XSD_STRING.getIRI().equals(rdfLiteralNode.getDatatype()))) {
            writer.writeDatatypeAttribute(rdfLiteralNode.getDatatype());
        }
        writer.writeTextContent(rdfLiteralNode.getLexicalValue());
    }

    /**
     * @return unserializable entities
     */
    public Set<OWLEntity> getUnserialisableEntities() {
        return qnameManager.getEntitiesWithInvalidQNames();
    }
}
