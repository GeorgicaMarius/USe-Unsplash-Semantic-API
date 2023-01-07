# Backend projects

## Local run requirements:

- Java IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- Apache Jena Fuseki e.g. [apache-jena-fuseki-4.7.0.zip](https://jena.apache.org/download/index.cgi)

---

## parser_tsv2rdf

Program that converts data from .tsv files from [Unsplash Lite Dataset](https://github.com/unsplash/datasets#lite-dataset) into RDF XML/TTL format using the ontology model decribed in the project documentation.

Notes:

- use provided `photos.tsv` and `keywords.tsv` from [Google Drive](https://drive.google.com/drive/folders/14MGJOAdQSFFSElGMqmUlvdJnnvcudCe3) that have some additional fixes like trouble causing characters removed/replaced
- in `Main.java` set the path to directory where the .tsv files are, run and `use_model.rdf` will be generated
- run Fuseki with UI using `apache-jena-fuseki-4.7.0\fuseki-server.bat` or `fuseki-server` ([docs](https://jena.apache.org/documentation/fuseki2/fuseki-webapp.html))
- access Fuseki UI at http://localhost:3030/#/
- go to "manage" -> "new dataset" and name it `use_dataset`, set Persistent (TDB2) and create dataset -> "add data" select `use_model.rdf` file and upload the triples
- dataset can be queried using UI [here](http://localhost:3030/#/dataset/use_dataset/query) and programmatic access is available using endpoints from [here](http://localhost:3030/#/dataset/use_dataset/info)

---

## pocs

Project that contains some Proof of Concept (pocs) code for using Apache Jena to call and retrieve data from SPARQL endpoints:

- `UseDsQueryHelper` contains queries for retrieving data from the local Fuseki SPARQL endpoint
- `DbpediaDsQueryHelper` contains queries for the DBpedia SPARQL enpoint
