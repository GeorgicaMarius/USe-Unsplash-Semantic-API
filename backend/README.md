# Backend projects

## Local run requirements:

- Java IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- Apache Jena Fuseki e.g. [apache-jena-fuseki-4.7.0.zip](https://jena.apache.org/download/index.cgi)

---

## parser_tsv2rdf

Program that converts data from .tsv files from [Unsplash Lite Dataset](https://github.com/unsplash/datasets#lite-dataset) into RDF XML/TTL format using the ontology model decribed in the project documentation.

Notes:

- the result rdf model file is available [here](https://drive.google.com/file/d/1i_KNnYsORsSu4mzoT4ocs2JC79v4fudi/view)
- use provided `photos.tsv`, `keywords.tsv`, `collections.tsv` from [Google Drive](https://drive.google.com/drive/folders/14MGJOAdQSFFSElGMqmUlvdJnnvcudCe3) that have some additional fixes like trouble causing characters removed/replaced
- in `Main.java` set the path to directory where the .tsv files are, run and `use_model.rdf` will be generated
- run Fuseki with UI using `apache-jena-fuseki-4.7.0\fuseki-server.bat` or `fuseki-server` ([docs](https://jena.apache.org/documentation/fuseki2/fuseki-webapp.html))
- access Fuseki UI at http://localhost:3030/#/
- go to "manage" -> "new dataset" and name it `use_dataset`, set Persistent (TDB2) and create dataset -> "add data" select `use_model.rdf` file and upload the triples
- dataset can be queried using UI [here](http://localhost:3030/#/dataset/use_dataset/query) and programmatic access is available using endpoints from [here](http://localhost:3030/#/dataset/use_dataset/info)

---

## use-api

Spring Boot Web REST API project, serving as the backend for the Angular frontend project. API documentation can be found in the project docs. Offers operations for:

- photos: query, search, filter, offset, limit, order, etc.
- collections: query, search, filter, offset, limit, order, etc.
- additional data: from DBpedia about cities and countries

Use `src\main\resources\application.properties` to configure:

- `fuseki.server.start`: `True` or `False` - if to start embedded Fuseki server (available at port 3330)
  - ⚠ Don't forget to add model file into resources folder: `src\main\resources\use_model.rdf` when True
- `use.dataset.endpoint`: URL to Fuseki SPARQL endpoint
  - `http://localhost:3030/use_dataset/sparql` for standalone local Fuseki server
  - `http://localhost:3330/use_dataset/sparql` for embedded Fuseki server
- `dbpedia.dataset.endpoint` already set to `https://dbpedia.org/sparql`
