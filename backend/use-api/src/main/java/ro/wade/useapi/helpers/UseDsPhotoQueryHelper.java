package ro.wade.useapi.helpers;

import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.wade.useapi.models.UsePhotoQueryDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UseDsPhotoQueryHelper {
    private final String useDatasetEndpointUrl;
    private final String prefixesBlock = "" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX schema: <http://schema.org/>\n" +
            "PREFIX use: <http://use.ro/>\n";
    private final String createVarsForAllFieldsBlock = "" +
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

    @Autowired
    public UseDsPhotoQueryHelper(@Value("${use.dataset.endpoint}") String useDatasetEndpointUrl) {
        this.useDatasetEndpointUrl = useDatasetEndpointUrl;
    }

    private UsePhotoQueryDto querySolutionToPhotoDto(QuerySolution sol) {
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

    private List<UsePhotoQueryDto> executeBasicQuery(String filterBlock, String orderBlock, Integer offset, Integer limit) {
        if (filterBlock == null) filterBlock = "";
        if (orderBlock == null) orderBlock = "";

        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText("" +
                prefixesBlock +
                "\n" +
                "SELECT * WHERE {\n" +
                createVarsForAllFieldsBlock +
                filterBlock +
                "}\n" +
                orderBlock +
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

    private static String getPhotoIdsFilter(List<String> photoIds) {
        StringBuilder filterBlockBuilder = new StringBuilder();
        filterBlockBuilder.append("FILTER (");
        for (int i = 0; i < photoIds.size(); i++) {
            filterBlockBuilder.append(String.format("?photoId = \"%s\"", photoIds.get(i)));
            if (i < photoIds.size() - 1) {
                filterBlockBuilder.append(" || ");
            }
        }
        filterBlockBuilder.append(")");
        return filterBlockBuilder.toString();
    }

    private static String getPhotoOrderBlock(String orderBy) {
        String orderBlock = "ORDER BY ASC(?photoSubmittedAt)\n";
        if (orderBy == null)
            return orderBlock;

        switch (orderBy) {
            case "downloads_asc":
                orderBlock = "ORDER BY ASC(?statsDownloads)\n";
                break;
            case "downloads_desc":
                orderBlock = "ORDER BY DESC(?statsDownloads)\n";
                break;
            case "views_asc":
                orderBlock = "ORDER BY ASC(?statsViews)\n";
                break;
            case "views_desc":
                orderBlock = "ORDER BY DESC(?statsViews)\n";
                break;
            case "submitted_asc":
                orderBlock = "ORDER BY ASC(?photoSubmittedAt)\n";
                break;
            case "submitted_desc":
                orderBlock = "ORDER BY DESC(?photoSubmittedAt)\n";
                break;
            case "width_asc":
                orderBlock = "ORDER BY ASC(?photoWidth)\n";
                break;
            case "width_desc":
                orderBlock = "ORDER BY DESC(?photoWidth)\n";
                break;
            case "height_asc":
                orderBlock = "ORDER BY ASC(?photoHeight)\n";
                break;
            case "height_desc":
                orderBlock = "ORDER BY DESC(?photoHeight)\n";
                break;
            case "city_asc":
                orderBlock = "ORDER BY ASC(?photoLocationCity)\n";
                break;
            case "city_desc":
                orderBlock = "ORDER BY DESC(?photoLocationCity)\n";
                break;
            case "country_asc":
                orderBlock = "ORDER BY ASC(?photoLocationCountry)\n";
                break;
            case "country_desc":
                orderBlock = "ORDER BY DESC(?photoLocationCountry)\n";
                break;
        }
        return orderBlock;
    }

    public UsePhotoQueryDto getPhotoById(String photoId) {
        String filterBlock = "  FILTER (?photoId = \"" + photoId + "\")\n";
        List<UsePhotoQueryDto> photos = executeBasicQuery(filterBlock, "", 0, 1);
        return (photos.size() != 0) ? photos.get(0) : null;
    }

    public List<UsePhotoQueryDto> getPhotosByIds(List<String> photoIds) {
        String filterBlock = getPhotoIdsFilter(photoIds);
        return executeBasicQuery(filterBlock, "", 0, photoIds.size());
    }

    public List<UsePhotoQueryDto> getPhotosFilter(
            Integer offset,
            Integer limit,
            String orderBy,
            String photographerFirstName,
            String photographerLastName,
            String cameraMake,
            String country,
            String city
    ) {
        StringBuilder filterBlockBuilder = new StringBuilder();
        if (photographerFirstName != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photographerFirstName, \"^%s$\", \"i\"))\n", photographerFirstName));
        if (photographerLastName != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photographerLastName, \"^%s$\", \"i\"))\n", photographerLastName));
        if (cameraMake != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?exifCameraMake, \"^%s$\", \"i\"))\n", cameraMake));
        if (country != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photoLocationCountry, \"^%s$\", \"i\"))\n", country));
        if (city != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photoLocationCity, \"^%s$\", \"i\"))\n", city));
        String orderBlock = getPhotoOrderBlock(orderBy);
        return executeBasicQuery(filterBlockBuilder.toString(), orderBlock, offset, limit);
    }

    public List<UsePhotoQueryDto> getPhotosSearch(
            Integer offset,
            Integer limit,
            String orderBy,
            String photographerFirstName,
            String photographerLastName,
            String cameraMake,
            String country,
            String city,
            String keyword,
            String masterKeyword
    ) {
        StringBuilder filterBlockBuilder = new StringBuilder();
        if (photographerFirstName != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photographerFirstName, \"%s\", \"i\"))\n", photographerFirstName));
        if (photographerLastName != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photographerLastName, \"%s\", \"i\"))\n", photographerLastName));
        if (cameraMake != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?exifCameraMake, \"%s\", \"i\"))\n", cameraMake));
        if (country != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photoLocationCountry, \"%s\", \"i\"))\n", country));
        if (city != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photoLocationCity, \"%s\", \"i\"))\n", city));
        if (keyword != null)
            filterBlockBuilder.append(String.format("  FILTER (regex (?photoKeywords, \"%s\", \"i\"))\n", keyword));
        if (masterKeyword != null) {
            List<String> variables = Arrays.asList("photoLocationCity", "photoLocationCountry", "photographerLastName",
                    "photographerFirstName", "exifCameraMake", "photoDescription", "photoKeywords");
            filterBlockBuilder.append("FILTER (");
            for (int i = 0; i < variables.size(); i++) {
                filterBlockBuilder.append(String.format("regex(?%s, \"%s\", \"i\")", variables.get(i), masterKeyword));
                if (i < variables.size() - 1) {
                    filterBlockBuilder.append(" || ");
                }
            }
            filterBlockBuilder.append(")");
        }
        String orderBlock = getPhotoOrderBlock(orderBy);
        return executeBasicQuery(filterBlockBuilder.toString(), orderBlock, offset, limit);
    }
}
