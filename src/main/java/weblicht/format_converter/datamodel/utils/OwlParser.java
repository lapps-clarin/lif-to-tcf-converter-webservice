/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.semanticweb.owlapi.search.Searcher.annotations;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;



/**
 *
 * @author felahi
 */
public class OwlParser {

    private OWLOntology ontology = null;
    private OWLDataFactory df = null;
    private Set<String> urls = new HashSet<String>();

    public OwlParser(String documentSource) {
        try {
            parseOwlDocuments(documentSource) ;
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OwlParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public OwlParser(OWLOntology ontology, OWLDataFactory df) {
        this.ontology = ontology;
        this.df = df;
    }

    public void parseOntology()
            throws OWLOntologyCreationException {

        for (OWLClass cls : ontology.getClassesInSignature()) {
            String id = cls.getIRI().toString();
            //String label = get(cls, RDFS_LABEL.toString()).get(0);
            //System.out.println(label + " [" + id + "]");
            //System.out.println(" [" + id + "]");
            urls.add(id);
        }
    }

    private List<String> get(OWLClass clazz, String property) {
        List<String> ret = new ArrayList<String>();

        final OWLAnnotationProperty owlProperty = df
                .getOWLAnnotationProperty(IRI.create(property));
        for (OWLOntology o : ontology.getImportsClosure()) {
            for (OWLAnnotation annotation : annotations(
                    o.getAnnotationAssertionAxioms(clazz.getIRI()), owlProperty)) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    ret.add(val.getLiteral());
                }
            }
        }
        return ret;
    }

     private void parseOwlDocuments(String url) throws OWLOntologyCreationException {        
        IRI documentIRI = IRI.create(url);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(documentIRI);
        OwlParser parser = new OwlParser(ontology, manager.getOWLDataFactory());
        parser.parseOntology();
    }

    public Set<String> getUrls() {
        return urls;
    }

}
