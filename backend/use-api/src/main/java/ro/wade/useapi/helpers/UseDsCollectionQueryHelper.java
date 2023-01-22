package ro.wade.useapi.helpers;

import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.wade.useapi.models.UseCollectionQueryDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UseDsCollectionQueryHelper {
    private final String useDatasetEndpointUrl;

    @Autowired
    public UseDsCollectionQueryHelper(@Value("${use.dataset.endpoint}") String useDatasetEndpointUrl) {
        this.useDatasetEndpointUrl = useDatasetEndpointUrl;
    }

    private final String prefixesBlock = "" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX schema: <http://schema.org/>\n" +
            "PREFIX use: <http://use.ro/>\n";

    private UseCollectionQueryDto resultSetToSingleCollectionDto(ResultSet results) {
        UseCollectionQueryDto collection = new UseCollectionQueryDto();
        if (results.hasNext()) {
            QuerySolution sol = results.nextSolution();
            collection.collectionId = sol.getLiteral("collectionId").getString();
            collection.collectionTitle = sol.getLiteral("collectionTitle").getString();
            (collection.photoIds = new ArrayList<>()).add(sol.getLiteral("photoId").getString());
        }
        while (results.hasNext()) {
            QuerySolution sol = results.nextSolution();
            collection.photoIds.add(sol.getLiteral("photoId").getString());
        }
        return collection;
    }

    private List<UseCollectionQueryDto> resultSetToMultipleCollectionDto(ResultSet results) {
        HashMap<String, UseCollectionQueryDto> map = new HashMap<>();

        while (results.hasNext()) {
            QuerySolution sol = results.nextSolution();
            String collectionId = sol.contains("collectionId") ? sol.getLiteral("collectionId").getString() : null;
            String collectionTitle = sol.contains("collectionTitle") ? sol.getLiteral("collectionTitle").getString() : null;
            String photoId = sol.contains("photoId") ? sol.getLiteral("photoId").getString() : null;

            if (map.containsKey(collectionId)) {
                map.get(collectionId).photoIds.add(photoId);
            } else {
                UseCollectionQueryDto collection = new UseCollectionQueryDto();
                collection.collectionId = collectionId;
                collection.collectionTitle = collectionTitle;
                (collection.photoIds = new ArrayList<>()).add(photoId);
                map.put(collectionId, collection);
            }
        }
        return new ArrayList<>(map.values());
    }

    private ResultSet executeBasicQuery(String filterBlock, String sortBlock, Integer offset, Integer limit) {
        if (filterBlock == null) filterBlock = "";
        if (sortBlock == null) sortBlock = "";

        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText("" +
                prefixesBlock +
                "\n" +
                "SELECT * WHERE {\n" +
                "  ?collectionRes rdf:type rdf:Bag ;\n" +
                "                 schema:identifier ?collectionId ;\n" +
                "                 rdfs:label ?collectionTitle ;\n" +
                "                 rdfs:member ?entryItem .\n" +
                "  ?entryItem schema:identifier ?photoId .\n" +
                filterBlock +
                "}\n" +
                sortBlock + "\n");
        if (offset != null)
            pss.append("OFFSET " + offset + "\n");
        if (limit != null)
            pss.append("LIMIT " + limit + "\n");

        // see the query string with injected values
        // System.out.println(pss);
        Query query = pss.asQuery();

        ResultSet resultSetCopy;
        try (QueryExecution queryExec = QueryExecutionHTTP.service(useDatasetEndpointUrl).query(query).build()) {
            ResultSet results = queryExec.execSelect();
            resultSetCopy = ResultSetFactory.copyResults(results);
        }
        return resultSetCopy;
    }

    private List<String> executeGetCollectionIdsQuery(Integer offset, Integer limit) {
        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText("" +
                prefixesBlock +
                "\n" +
                "SELECT * WHERE {\n" +
                "  ?collectionRes rdf:type rdf:Bag ;\n" +
                "                 schema:identifier ?collectionId .\n" +
                "}\n");
        if (offset != null)
            pss.append("OFFSET " + offset + "\n");
        if (limit != null)
            pss.append("LIMIT " + limit + "\n");

        // see the query string with injected values
        System.out.println(pss);
        Query query = pss.asQuery();

        List<String> collectionIds = new ArrayList<>();
        try (QueryExecution queryExec = QueryExecutionHTTP.service(useDatasetEndpointUrl).query(query).build()) {
            ResultSet results = queryExec.execSelect();
            while (results.hasNext()) {
                QuerySolution querySolution = results.nextSolution();
                collectionIds.add(querySolution.getLiteral("collectionId").getString());
            }
        }
        return collectionIds;
    }

    private <T> List<T> clampedSublist(List<T> list, int offset, int limit) {
        return list.subList(
                Math.min(list.size(), offset),
                Math.min(list.size(), offset + limit));
    }

    public UseCollectionQueryDto getCollectionById(String collectionId) {
        String filterBlock = "  FILTER (?collectionId = \"" + collectionId + "\")\n";
        ResultSet resultSet = executeBasicQuery(filterBlock, "", 0, null);
        return resultSetToSingleCollectionDto(resultSet);
    }

    public List<UseCollectionQueryDto> getCollectionsFilter(Integer offset, Integer limit, String collectionTitle) {
        String filterBlock = null;
        if (collectionTitle != null)
            filterBlock = String.format("  FILTER (regex (?collectionTitle, \"^%s$\", \"i\"))\n", collectionTitle);

        if (filterBlock != null) {
            ResultSet resultSet = executeBasicQuery(filterBlock, null, 0, null);
            return clampedSublist(resultSetToMultipleCollectionDto(resultSet), offset, limit);
        } else {
            List<String> collectionIds = executeGetCollectionIdsQuery(offset, limit);
            List<UseCollectionQueryDto> collections = new ArrayList<>();
            for (String collectionId : collectionIds) {
                collections.add(getCollectionById(collectionId));
            }
            return collections;
        }
    }

    public List<UseCollectionQueryDto> getCollectionsSearch(Integer offset, Integer limit, String collectionTitle) {
        String filterBlock = null;
        if (collectionTitle != null)
            filterBlock = String.format("  FILTER (regex (?collectionTitle, \"%s\", \"i\"))\n", collectionTitle);
        ResultSet resultSet = executeBasicQuery(filterBlock, null, 0, null);
        return clampedSublist(resultSetToMultipleCollectionDto(resultSet), offset, limit);
    }
}
