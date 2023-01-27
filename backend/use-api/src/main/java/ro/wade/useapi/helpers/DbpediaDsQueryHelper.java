package ro.wade.useapi.helpers;

import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.wade.useapi.models.DbpediaCityQueryDto;
import ro.wade.useapi.models.DbpediaCountryQueryDto;

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

    private DbpediaCountryQueryDto querySolutionToCountryDto(QuerySolution sol) {
        DbpediaCountryQueryDto country = new DbpediaCountryQueryDto();
        country.countryName = sol.getLiteral("countryName").getString();
        country.countryCurrency = sol.contains("countryCurrency") ? sol.getResource("countryCurrency").toString() : "";
        country.countryPopulationTotal = sol.contains("countryPopulationTotal") ? sol.getLiteral("countryPopulationTotal").getInt() : null;
        country.countryTimeZone = sol.contains("countryTimeZone") ? sol.getResource("countryTimeZone").toString() : "";
        country.countryWebsite = sol.contains("countryWebsite") ? sol.getResource("countryWebsite").toString() : "";
        country.countryLat = sol.contains("countryLat") ? sol.getLiteral("countryLat"). getDouble(): null;
        country.countryLong = sol.contains("countryLong") ? sol.getLiteral("countryLong").getDouble() : null;
        country.countryOfficialLanguage = sol.contains("countryOfficialLanguage") ? sol.getResource("countryOfficialLanguage").toString() : "";
        country.countryCapital = sol.contains("countryCapital") ? sol.getResource("countryCapital").toString() : "";
        country.countryGovernmentType = sol.contains("countryGovernmentType") ? sol.getResource("countryGovernmentType").toString() : "";
        return country;
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

    public DbpediaCountryQueryDto getCountryByName(String countryName) {
        ParameterizedSparqlString pss = new ParameterizedSparqlString();

        pss.setCommandText("" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "\n" +
                "SELECT * WHERE {\n" +
                "?country rdf:type dbo:Country ;\n" +
                "rdfs:label ?countryName .\n" +
                "OPTIONAL { ?country dbo:abstract ?countryAbstract . }\n" +
                "OPTIONAL { ?country dbo:capital ?countryCapital . }\n" +
                "OPTIONAL { ?country dbo:currency ?countryCurrency . }\n" +
                "OPTIONAL { ?country dbo:populationTotal ?countryPopulationTotal . }\n" +
                "OPTIONAL { ?country dbo:timeZone ?countryTimeZone . }\n" +
                "OPTIONAL { ?country dbp:website ?countryWebsite . }\n" +
                "OPTIONAL { ?country geo:lat ?countryLat . }\n" +
                "OPTIONAL { ?country geo:long ?countryLong . }\n" +
                "OPTIONAL { ?country dbo:governmentType ?countryGovernmentType . }\n" +
                "OPTIONAL { ?country dbo:officialLanguage ?countryOfficialLanguage . }\n" +
                "FILTER ((!bound(?countryAbstract) || LANG(?countryAbstract) = 'en') && ?countryName = ?injectedCountryName@en)\n" +
                "}\n" +
                "LIMIT 10\n");
        pss.setLiteral("injectedCountryName", countryName);
        // see the query string with injected values
        System.out.println(pss);
        Query query = pss.asQuery();

        System.out.print("Please wait while querying DBpedia... ");
        try (QueryExecution queryExec = QueryExecutionHTTP.service(dbpediaDatasetEndpointUrl).query(query).build()) {
            ResultSet results = queryExec.execSelect();
            System.out.println("Done!");
            if (results.hasNext()) {
                QuerySolution querySolution = results.nextSolution();
                return querySolutionToCountryDto(querySolution);
            }
        }
        return null;
    }
}
