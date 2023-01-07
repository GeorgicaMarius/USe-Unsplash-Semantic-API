package org.example.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class SCHEMA {
    public static final String uri = "http://schema.org/";
    private static final Model m = ModelFactory.createDefaultModel();

    public static final Resource Photograph;
    public static final Resource MediaObject;
    public static final Resource Place;
    public static final Resource InteractionCounter;
    public static final Resource Person;

    public static final Property identifier;
    public static final Property sameAs;
    public static final Property description;
    public static final Property keywords;
    public static final Property associatedMedia;
    public static final Property contentUrl;
    public static final Property uploadDate;
    public static final Property width;
    public static final Property height;
    public static final Property contentLocation;
    public static final Property addressLocality;
    public static final Property addressCountry;
    public static final Property longitude;
    public static final Property latitude;
    public static final Property name;
    public static final Property interactionStatistic;
    public static final Property userInteractionCount;
    public static final Property interactionType;
    public static final Property author;
    public static final Property familyName;
    public static final Property givenName;
    public static final Property callSign;

    public SCHEMA() {
    }

    public static String getURI() {
        return uri;
    }

    static {
        Photograph = m.createResource(uri + "Photograph");
        MediaObject = m.createResource(uri + "MediaObject");
        Place = m.createResource(uri + "Place");
        InteractionCounter = m.createResource(uri + "InteractionCounter");
        Person = m.createResource(uri + "Person");

        identifier = m.createProperty(uri, "identifier");
        sameAs = m.createProperty(uri, "sameAs");
        description = m.createProperty(uri, "description");
        keywords = m.createProperty(uri, "keywords");
        associatedMedia = m.createProperty(uri, "associatedMedia");
        contentUrl = m.createProperty(uri, "contentUrl");
        uploadDate = m.createProperty(uri, "uploadDate");
        width = m.createProperty(uri, "width");
        height = m.createProperty(uri, "height");
        contentLocation = m.createProperty(uri, "contentLocation");
        addressLocality = m.createProperty(uri, "addressLocality");
        addressCountry = m.createProperty(uri, "addressCountry");
        longitude = m.createProperty(uri, "longitude");
        latitude = m.createProperty(uri, "latitude");
        name = m.createProperty(uri, "name");
        interactionStatistic = m.createProperty(uri, "interactionStatistic");
        userInteractionCount = m.createProperty(uri, "userInteractionCount");
        interactionType = m.createProperty(uri, "interactionType");
        author = m.createProperty(uri, "author");
        familyName = m.createProperty(uri, "familyName");
        givenName = m.createProperty(uri, "givenName");
        callSign = m.createProperty(uri, "callSign");
    }
}
