package de.dfki.scilake.dostres.data;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class SCIONT {

    protected static final String uri = "http://scilake.eu/ontologies/sciont#";
    protected static final String defaultPrefix = "http://scilake.eu/res/";

    /**
     * returns the URI for this schema
     * 
     * @return the URI for this schema
     */
    public static String getURI() {
        return uri;
    }
    public static String getDefaultPrefix() {
        return defaultPrefix;
    }

    public static final Resource resource(String local) {
        return ResourceFactory.createResource(uri + local);
    }

    public static final Property property(String local) {
        return ResourceFactory.createProperty(uri, local);
    }

    public static final Resource anchorOf = resource("anchorOf");
    public static final Property beginIndex = property("beginIndex");
    public static final Property confidence = property("confidence");
    public static final Property isString = property("isString");
    public static final Property endIndex = property("endIndex");
    public static final Property entity = property("entity");
    public static final Property keyword = property("keyword");
        
    /**
     * Resources and properties related to SECTIONS
     */
    public static final Resource Section = resource("Section");
    public static final Property hasSection = property("hasSection");
    public static final Property isSectionOf = property("isSectionOf");
    public static final Property sectionNumber = property("sectionNumber");

    public static final Resource Subsection = resource("Subsection");
    public static final Property hasSubsection = property("hasSubsection");
    public static final Property isSubsectionOf = property("isSubsectionOf");

    /**
     * Resources and properties related to AUTHORSHIP
    public static final Resource  = resource("");
    public static final Property has = property("has");
    public static final Property isOf = property("isOf");
    public static final Resource  = resource("");
    public static final Property has = property("has");
    public static final Property isOf = property("isOf");
    */

    /**
     * Resources and properties related to 
    public static final Resource  = resource("");
    public static final Property has = property("has");
    public static final Property isOf = property("isOf");
    public static final Resource  = resource("");
    public static final Property has = property("has");
    public static final Property isOf = property("isOf");
     */
    
    // public static final Resource  = resource("");
    // public static final Property has = property("has");
    // public static final Property isOf = property("isOf");
    
    public static String createDocumentURI(){
    	return defaultPrefix+"doc"+((int)(Math.random()*9000)+1000);
    }

}
