package ro.wade.useapi.helpers;

import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.wade.useapi.models.UseCollectionQueryDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UseDsCollectionQueryHelper {

    private final String useDatasetEndpointUrl;
    private final String prefixesBlock = "" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX schema: <http://schema.org/>\n" +
            "PREFIX use: <http://use.ro/>\n";

    @Autowired
    public UseDsCollectionQueryHelper(@Value("${use.dataset.endpoint}") String useDatasetEndpointUrl) {
        this.useDatasetEndpointUrl = useDatasetEndpointUrl;
    }

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

    private UseCollectionQueryDto executeGetCollectionByIdQuery(String collectionId, boolean verbose) {
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
                "  FILTER (?collectionId = ?injectedCollectionId)\n" +
                "}\n");
        pss.setLiteral("injectedCollectionId", collectionId);
        // see the query string with injected values
        if (verbose)
            System.out.println(pss);
        Query query = pss.asQuery();

        try (QueryExecution queryExec = QueryExecutionHTTP.service(useDatasetEndpointUrl).query(query).build()) {
            ResultSet results = queryExec.execSelect();
            if (results.hasNext())
                return resultSetToSingleCollectionDto(results);
        }
        return null;
    }

    private List<String> executeGetCollectionIdsQuery(String filterBlock, String sortBlock, Integer offset, Integer limit) {
        if (filterBlock == null) filterBlock = "";
        if (sortBlock == null) sortBlock = "";

        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText("" +
                prefixesBlock +
                "\n" +
                "SELECT * WHERE {\n" +
                "  ?collectionRes rdf:type rdf:Bag ;\n" +
                "                 schema:identifier ?collectionId ;\n" +
                "                 rdfs:label ?collectionTitle .\n" +
                filterBlock +
                "}\n" +
                sortBlock + "\n");
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

    public UseCollectionQueryDto getCollectionById(String collectionId, boolean verbose) {
        return executeGetCollectionByIdQuery(collectionId, verbose);
    }

    public List<UseCollectionQueryDto> getCollectionsFilter(Integer offset, Integer limit, String collectionTitle) {
        String filterBlock = null;
        if (collectionTitle != null)
            filterBlock = String.format("  FILTER (regex (?collectionTitle, \"^%s$\", \"i\"))\n", collectionTitle);

        List<String> collectionIds = executeGetCollectionIdsQuery(filterBlock, null, offset, limit);
        List<UseCollectionQueryDto> collections = new ArrayList<>();
        for (String collectionId : collectionIds) {
            collections.add(getCollectionById(collectionId, false));
        }
        return collections;
    }

    public List<UseCollectionQueryDto> getCollectionsSearch(Integer offset, Integer limit, String collectionTitle) {
        String filterBlock = null;
        if (collectionTitle != null)
            filterBlock = String.format("  FILTER (regex (?collectionTitle, \"%s\", \"i\"))\n", collectionTitle);

        List<String> collectionIds = executeGetCollectionIdsQuery(filterBlock, null, offset, limit);
        List<UseCollectionQueryDto> collections = new ArrayList<>();
        for (String collectionId : collectionIds) {
            collections.add(getCollectionById(collectionId, false));
        }
        return collections;
    }
}
