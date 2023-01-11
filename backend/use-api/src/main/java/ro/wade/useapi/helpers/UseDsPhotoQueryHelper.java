package ro.wade.useapi.helpers;

import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import ro.wade.useapi.models.UsePhotoQueryDto;

import java.util.ArrayList;
import java.util.List;

public class UseDsPhotoQueryHelper {
    private static final String useDatasetEndpointUrl = "http://localhost:3030/use_dataset/sparql";

    private static final String prefixesBlock = "" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX schema: <http://schema.org/>\n" +
            "PREFIX use: <http://use.ro/>\n";

    private static final String createVarsForAllFieldsBlock = "" +
            "  ?photoRes rdf:type schema:Photograph ;\n" +
            "            schema:identifier ?photoId ;\n" +
            "            schema:sameAs ?photoUrl ;\n" +
            "            schema:description ?photoDescription ;\n" +
            "            schema:keywords ?photoKeywords ;\n" +
            "            schema:associatedMedia ?bnAssociatedMedia ;\n" +
            "            schema:contentLocation ?bnContentLocation ;\n" +
            "            use:downloadsCount ?statsDownloads ;\n" +
            "            use:viewsCount ?statsViews ;\n" +
            "            use:exifCameraMake ?exifCameraMake ;\n" +
            "            use:exifCameraModel ?exifCameraModel ;\n" +
            "            use:exifIso ?exifIso ;\n" +
            "            use:exifApertureValue ?exifApertureValue ;\n" +
            "            use:exifFocalLength ?exifFocalLength ;\n" +
            "            use:exifExposureTime ?exifExposureTime ;\n" +
            "            schema:author ?bnAuthor.\n" +
            "  ?bnAssociatedMedia rdf:type schema:MediaObject ;\n" +
            "                     schema:contentUrl ?photoImageUrl ;\n" +
            "                     schema:uploadDate ?photoSubmittedAt ;\n" +
            "                     schema:width ?photoWidth ;\n" +
            "                     schema:height ?photoHeight .\n" +
            "  ?bnContentLocation rdf:type schema:Place ;\n" +
            "                     schema:addressLocality ?photoLocationCity ;\n" +
            "                     schema:addressCountry ?photoLocationCountry ;\n" +
            "                     schema:longitude ?photoLocationLongitude ;\n" +
            "                     schema:latitude ?photoLocationLatitude ;\n" +
            "                     schema:name ?photoLocationName .\n" +
            "  ?bnAuthor rdf:type schema:Person ;\n" +
            "            schema:familyName ?photographerLastName ;\n" +
            "            schema:givenName ?photographerFirstName ;\n" +
            "            schema:callSign ?photographerUsername .\n";


    private static UsePhotoQueryDto querySolutionToPhotoDto(QuerySolution sol) {
        UsePhotoQueryDto photo = new UsePhotoQueryDto();
        photo.photoId = sol.getLiteral("photoId").getString();
        photo.photoUrl = sol.getLiteral("photoUrl").getString();
        photo.photoDescription = sol.getLiteral("photoDescription").getString();
        photo.photoKeywords = sol.getLiteral("photoKeywords").getString();
        photo.statsDownloads = sol.getLiteral("statsDownloads").getInt();
        photo.statsViews = sol.getLiteral("statsViews").getInt();
        photo.exifCameraMake = sol.getLiteral("exifCameraMake").getString();
        photo.exifCameraModel = sol.getLiteral("exifCameraModel").getString();
        photo.exifIso = sol.getLiteral("exifIso").getString();
        photo.exifApertureValue = sol.getLiteral("exifApertureValue").getString();
        photo.exifFocalLength = sol.getLiteral("exifFocalLength").getString();
        photo.exifExposureTime = sol.getLiteral("exifExposureTime").getString();
        photo.photoImageUrl = sol.getLiteral("photoImageUrl").getString();
        photo.photoSubmittedAt = sol.getLiteral("photoSubmittedAt").getLong();
        photo.photoWidth = sol.getLiteral("photoWidth").getInt();
        photo.photoHeight = sol.getLiteral("photoHeight").getInt();
        photo.photoLocationCity = sol.getLiteral("photoLocationCity").getString();
        photo.photoLocationCountry = sol.getLiteral("photoLocationCountry").getString();
        photo.photoLocationLongitude = sol.getLiteral("photoLocationLongitude").getString();
        photo.photoLocationLatitude = sol.getLiteral("photoLocationLatitude").getString();
        photo.photoLocationName = sol.getLiteral("photoLocationName").getString();
        photo.photographerLastName = sol.getLiteral("photographerLastName").getString();
        photo.photographerFirstName = sol.getLiteral("photographerFirstName").getString();
        photo.photographerUsername = sol.getLiteral("photographerUsername").getString();
        return photo;
    }

    private static List<UsePhotoQueryDto> executeBasicQuery(String filterBlock, String sortBlock, Integer offset, Integer limit) {
        if (filterBlock == null) filterBlock = "";
        if (sortBlock == null) sortBlock = "";

        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText("" +
                prefixesBlock +
                "\n" +
                "SELECT * WHERE {\n" +
                createVarsForAllFieldsBlock +
                filterBlock +
                "}\n" +
                sortBlock +
                "OFFSET ?injectedOffsetLimit\n" +
                "LIMIT ?injectedEntriesLimit\n");
        pss.setLiteral("injectedOffsetLimit", offset);
        pss.setLiteral("injectedEntriesLimit", limit);
        // see the query string with injected values
        System.out.println(pss);
        Query query = pss.asQuery();

        List<UsePhotoQueryDto> photos = new ArrayList<>();
        try (QueryExecution queryExec = QueryExecutionHTTP.service(useDatasetEndpointUrl).query(query).build()) {
            ResultSet results = queryExec.execSelect();
            while (results.hasNext()) {
                QuerySolution querySolution = results.nextSolution();
                photos.add(querySolutionToPhotoDto(querySolution));
            }
        }
        return photos;
    }

    public static UsePhotoQueryDto getPhotoById(String photoId) {
        String filterBlock = "  FILTER (?photoId = \"" + photoId + "\")\n";
        List<UsePhotoQueryDto> photos = executeBasicQuery(filterBlock, "", 0, 1);
        return (photos.size() != 0) ? photos.get(0) : null;
    }

    public static List<UsePhotoQueryDto> getPhotosFilter(
            Integer offset,
            Integer limit,
            String photographerFirstName,
            String photographerLastName,
            String cameraMake,
            String country,
            String city
    ) {
        String filterBlock = null;
        if (photographerFirstName != null)
            filterBlock = String.format("  FILTER (regex (?photographerFirstName, \"^%s$\", \"i\"))\n", photographerFirstName);
        else if (photographerLastName != null)
            filterBlock = String.format("  FILTER (regex (?photographerLastName, \"^%s$\", \"i\"))\n", photographerLastName);
        else if (cameraMake != null)
            filterBlock = String.format("  FILTER (regex (?exifCameraMake, \"^%s$\", \"i\"))\n", cameraMake);
        else if (country != null)
            filterBlock = String.format("  FILTER (regex (?photoLocationCountry, \"^%s$\", \"i\"))\n", country);
        else if (city != null)
            filterBlock = String.format("  FILTER (regex (?photoLocationCity, \"^%s$\", \"i\"))\n", city);
        String sortBlock = "ORDER BY ASC(?photoSubmittedAt)\n";
        return executeBasicQuery(filterBlock, sortBlock, offset, limit);
    }

    public static List<UsePhotoQueryDto> getPhotosSearch(
            Integer offset,
            Integer limit,
            String photographerFirstName,
            String photographerLastName,
            String cameraMake,
            String country,
            String city,
            String keyword
    ) {
        String filterBlock = null;
        if (photographerFirstName != null)
            filterBlock = String.format("  FILTER (regex (?photographerFirstName, \"%s\", \"i\"))\n", photographerFirstName);
        else if (photographerLastName != null)
            filterBlock = String.format("  FILTER (regex (?photographerLastName, \"%s\", \"i\"))\n", photographerLastName);
        else if (cameraMake != null)
            filterBlock = String.format("  FILTER (regex (?exifCameraMake, \"%s\", \"i\"))\n", cameraMake);
        else if (country != null)
            filterBlock = String.format("  FILTER (regex (?photoLocationCountry, \"%s\", \"i\"))\n", country);
        else if (city != null)
            filterBlock = String.format("  FILTER (regex (?photoLocationCity, \"%s\", \"i\"))\n", city);
        else if (keyword != null)
            filterBlock = String.format("  FILTER (regex (?photoKeywords, \"%s\", \"i\"))\n", keyword);
        String sortBlock = "ORDER BY ASC(?photoSubmittedAt)\n";
        return executeBasicQuery(filterBlock, sortBlock, offset, limit);
    }
}
