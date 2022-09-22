package org.semanticweb.owlapi.obolibrarytest.oboformat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_STRING;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OntologyConfigurator;

class LoadAnonymousTestCase extends TestBase {

    private static final OWLLiteral LIT_UNIT = literal("unit.ontology");

    private static OWLLiteral literal(String litvalue) {
        return Literal(litvalue, XSD_STRING);
    }

    private static OWLAnnotationAssertionAxiom comment(OWLAnnotationSubject subj,
        OWLAnnotationValue value) {
        return AnnotationAssertion(RDFSComment(), subj, value);
    }

    private static OWLAnnotationAssertionAxiom label(OWLAnnotationSubject subj,
        OWLAnnotationValue value) {
        return AnnotationAssertion(RDFSLabel(), subj, value);
    }

    @Test
    void shouldLoad() {
        OntologyConfigurator loaderConfig = new OntologyConfigurator()
            .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology =
            loadFrom(TestFiles.loadOboAnonymous, new OBODocumentFormat(), loaderConfig);
        Set<OWLAxiom> expected = set(OWLAxiom.class, Declaration(ANNPROPS.date),
            Declaration(ANNPROPS.autogeneratedby), Declaration(ANNPROPS.hasDbXref),
            Declaration(ANNPROPS.defaultnamespace), Declaration(ANNPROPS.subsetProperty),
            Declaration(ANNPROPS.hasOBOFormatVersion), Declaration(ANNPROPS.iao0000115),
            Declaration(ANNPROPS.namespaceIdRule), Declaration(ANNPROPS.createdBy),
            Declaration(ANNPROPS.inSubset), Declaration(ANNPROPS.savedby),
            Declaration(CLASSES.pato0001708), Declaration(CLASSES.uo0), Declaration(RDFSComment()),
            Declaration(RDFSLabel()), Declaration(ANNPROPS.hasOBONamespace),
            Declaration(CLASSES.uo1), Declaration(ANNPROPS.id),
            SubAnnotationPropertyOf(ANNPROPS.mpathSlim, ANNPROPS.subsetProperty),
            AnnotationAssertion(ANNPROPS.hasOBONamespace, CLASSES.uo1.getIRI(), LIT_UNIT),
            comment(ANNPROPS.attributeSlim.getIRI(), literal("Attribute slim")),
            label(ANNPROPS.iao0000115.getIRI(), literal("definition")),
            AnnotationAssertion(ANNPROPS.hasOBONamespace, CLASSES.uo0.getIRI(), LIT_UNIT),
            SubAnnotationPropertyOf(ANNPROPS.unitSlim, ANNPROPS.subsetProperty),
            comment(ANNPROPS.valueSlim.getIRI(), literal("Value slim")),
            SubAnnotationPropertyOf(ANNPROPS.absentSlim, ANNPROPS.subsetProperty),
            SubAnnotationPropertyOf(ANNPROPS.abnormalSlim, ANNPROPS.subsetProperty),
            label(CLASSES.uo1.getIRI(), literal("length unit")),
            label(ANNPROPS.hasOBOFormatVersion.getIRI(), literal("has_obo_format_version")),
            label(ANNPROPS.namespaceIdRule.getIRI(), literal("namespace-id-rule")),
            SubClassOf(CLASSES.uo1, ObjectSomeValuesFrom(OBJPROPS.isUnitOf, CLASSES.pato0001708)),
            SubAnnotationPropertyOf(ANNPROPS.cellQuality, ANNPROPS.subsetProperty),
            comment(ANNPROPS.relationalSlim.getIRI(), literal(
                "Relational slim: types of quality that require an additional entity in order to exist")),
            SubAnnotationPropertyOf(ANNPROPS.prefixSlim, ANNPROPS.subsetProperty),
            SubAnnotationPropertyOf(ANNPROPS.scalarSlim, ANNPROPS.subsetProperty),
            comment(ANNPROPS.scalarSlim.getIRI(), literal("Scalar slim")),
            comment(ANNPROPS.abnormalSlim.getIRI(), literal("Abnormal/normal slim")),
            SubAnnotationPropertyOf(ANNPROPS.attributeSlim, ANNPROPS.subsetProperty),
            label(CLASSES.uo0.getIRI(), literal("unit")),
            SubAnnotationPropertyOf(ANNPROPS.dispositionSlim, ANNPROPS.subsetProperty),
            comment(ANNPROPS.unitSlim.getIRI(), literal("unit slim")),
            SubAnnotationPropertyOf(ANNPROPS.relationalSlim, ANNPROPS.subsetProperty),
            AnnotationAssertion(ANNPROPS.id, CLASSES.uo1.getIRI(), literal("UO:0000001")),
            comment(ANNPROPS.mpathSlim.getIRI(), literal("Pathology slim")),
            AnnotationAssertion(ANNPROPS.createdBy, CLASSES.uo1.getIRI(),
                literal("george gkoutos")),
            label(ANNPROPS.hasDbXref.getIRI(), literal("database_cross_reference")),
            SubClassOf(CLASSES.uo1, CLASSES.uo0),
            label(ANNPROPS.hasOBONamespace.getIRI(), literal("has_obo_namespace")),
            AnnotationAssertion(ANNPROPS.id, CLASSES.uo0.getIRI(), literal("UO:0000000")),
            AnnotationAssertion(ANNPROPS.createdBy, CLASSES.uo0.getIRI(),
                literal("george gkoutos")),
            comment(ANNPROPS.prefixSlim.getIRI(), literal("prefix slim")),
            comment(ANNPROPS.cellQuality.getIRI(), literal("cell_quality")),
            comment(ANNPROPS.absentSlim.getIRI(), literal("Absent/present slim")),
            label(ANNPROPS.subsetProperty.getIRI(), literal("subset_property")),
            SubAnnotationPropertyOf(ANNPROPS.unitGroupSlim, ANNPROPS.subsetProperty),
            comment(ANNPROPS.unitGroupSlim.getIRI(), literal("unit group slim")),
            comment(ANNPROPS.dispositionSlim.getIRI(), literal("Disposition slim")),
            label(ANNPROPS.inSubset.getIRI(), literal("in_subset")),
            SubAnnotationPropertyOf(ANNPROPS.valueSlim, ANNPROPS.subsetProperty),
            AnnotationAssertion(ANNPROPS.inSubset, CLASSES.uo1.getIRI(),
                ANNPROPS.unitGroupSlim.getIRI()),
            AnnotationAssertion(Annotation(ANNPROPS.hasDbXref, LITERALS.literal),
                ANNPROPS.iao0000115, CLASSES.uo0.getIRI(),
                literal("A unit of measurement is a standardized quantity of a physical quality.")),
            AnnotationAssertion(Annotation(ANNPROPS.hasDbXref, LITERALS.literal),
                ANNPROPS.iao0000115, CLASSES.uo1.getIRI(),
                literal("A unit which is a standard measure of the distance between two points.")));
        assertEquals(expected, asUnorderedSet(ontology.axioms()));
    }
}
