package org.example.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

public class USE {
    public static final String uri = "http://use.ro/";
    private static final Model m = ModelFactory.createDefaultModel();

    public static final Property downloadsCount;
    public static final Property viewsCount;

    public USE() {
    }

    public static String getURI() {
        return uri;
    }

    public static String getCURIE(String localName) {
        return uri + localName;
    }

    static {
        downloadsCount = m.createProperty(uri, "downloadsCount");
        viewsCount = m.createProperty(uri, "viewsCount");
    }
}
