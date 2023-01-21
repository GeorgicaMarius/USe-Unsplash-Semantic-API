package ro.wade.useapi.helpers;

import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.wade.useapi.models.DbpediaCityQueryDto;

@Service
public class DbpediaDsQueryHelper {
    private final String dbpediaDatasetEndpointUrl;

    @Autowired
    public DbpediaDsQueryHelper(@Value("${dbpedia.dataset.endpoint}") String dbpediaDatasetEndpointUrl) {
        this.dbpediaDatasetEndpointUrl = dbpediaDatasetEndpointUrl;
    }

    private DbpediaCityQueryDto querySolutionToCityDto(QuerySolution sol) {
        DbpediaCityQueryDto city = new DbpediaCityQueryDto();
        city.cityName = sol.getLiteral("cityName").getString();
        city.cityAbstract = sol.contains("cityAbstract") ? sol.getLiteral("cityAbstract").getString() : "";
        city.cityCountry = sol.contains("cityCountry") ? sol.getResource("cityCountry").toString() : "";
        city.cityAreaCode = sol.contains("cityAreaCode") ? sol.getLiteral("cityAreaCode").getString() : "";
        city.cityPopulationTotal = sol.contains("cityPopulationTotal") ? sol.getLiteral("cityPopulationTotal").getInt() : null;
        city.cityPopulationAsOf = sol.contains("cityPopulationAsOf") ? sol.getLiteral("cityPopulationAsOf").getInt() : null;
        city.cityPostalCode = sol.contains("cityPostalCode") ? sol.getLiteral("cityPostalCode").getString() : "";
        city.cityTimeZone = sol.contains("cityTimeZone") ? sol.getResource("cityTimeZone").toString() : "";
        city.cityType = sol.contains("cityType") ? sol.getResource("cityType").toString() : "";
        city.cityWebsite = sol.contains("cityWebsite") ? sol.getResource("cityWebsite").toString() : "";
        city.cityLat = sol.contains("cityLat") ? sol.getLiteral("cityLat").getDouble() : null;
        city.cityLong = sol.contains("cityLong") ? sol.getLiteral("cityLong").getDouble() : null;
        return city;
    }

    public DbpediaCityQueryDto getCityByName(String cityName) {
        ParameterizedSparqlString pss = new ParameterizedSparqlString();

        pss.setCommandText("" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "\n" +
                "SELECT * WHERE {\n" +
                "  ?city rdf:type dbo:City ;\n" +
                "        rdfs:label ?cityName .\n" +
                "  OPTIONAL { ?city dbo:abstract ?cityAbstract . }\n" +
                "  OPTIONAL { ?city dbo:country ?cityCountry . }\n" +
                "  OPTIONAL { ?city dbo:areaCode ?cityAreaCode . }\n" +
                "  OPTIONAL { ?city dbo:populationTotal ?cityPopulationTotal . }\n" +
                "  OPTIONAL { ?city dbp:populationAsOf ?cityPopulationAsOf . }\n" +
                "  OPTIONAL { ?city dbo:postalCode ?cityPostalCode . }\n" +
                "  OPTIONAL { ?city dbo:timeZone ?cityTimeZone . }\n" +
                "  OPTIONAL { ?city dbo:type ?cityType . }\n" +
                "  OPTIONAL { ?city dbp:website ?cityWebsite . }\n" +
                "  OPTIONAL { ?city geo:lat ?cityLat . }\n" +
                "  OPTIONAL { ?city geo:long ?cityLong . }\n" +
                "  FILTER ((!bound(?cityAbstract) || LANG(?cityAbstract) = 'en') && ?cityName = ?injectedCityName@en)\n" +
                "}\n" +
                "LIMIT 10\n");
        pss.setLiteral("injectedCityName", cityName);
        // see the query string with injected values
        System.out.println(pss);
        Query query = pss.asQuery();

        System.out.print("Please wait while querying DBpedia... ");
        try (QueryExecution queryExec = QueryExecutionHTTP.service(dbpediaDatasetEndpointUrl).query(query).build()) {
            ResultSet results = queryExec.execSelect();
            System.out.println("Done!");
            if (results.hasNext()) {
                QuerySolution querySolution = results.nextSolution();
                return querySolutionToCityDto(querySolution);
            }
        }
        return null;
    }
}
