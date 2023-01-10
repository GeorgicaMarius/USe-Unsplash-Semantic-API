package org.example.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

public class USE {
    public static final String uri = "http://use.ro/";
    private static final Model m = ModelFactory.createDefaultModel();

    public static final Property downloadsCount;
    public static final Property viewsCount;
    public static final Property exifCameraMake;
    public static final Property exifCameraModel;
    public static final Property exifIso;
    public static final Property exifApertureValue;
    public static final Property exifFocalLength;
    public static final Property exifExposureTime;

    public USE() {
    }

    public static String getURI() {
        return uri;
    }

    public static String getResUri(String localName) {
        return uri + localName;
    }

    static {
        downloadsCount = m.createProperty(uri, "downloadsCount");
        viewsCount = m.createProperty(uri, "viewsCount");
        exifCameraMake = m.createProperty(uri, "exifCameraMake");
        exifCameraModel = m.createProperty(uri, "exifCameraModel");
        exifIso = m.createProperty(uri, "exifIso");
        exifApertureValue = m.createProperty(uri, "exifApertureValue");
        exifFocalLength = m.createProperty(uri, "exifFocalLength");
        exifExposureTime = m.createProperty(uri, "exifExposureTime");
    }
}
