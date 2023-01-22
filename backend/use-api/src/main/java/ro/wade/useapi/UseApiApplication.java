package ro.wade.useapi;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class UseApiApplication {

    public static void main(String[] args) throws IOException {
        // Spring app startup
        SpringApplication.run(UseApiApplication.class, args);

        // Load properties
        String startFusekiServer;
        try (InputStream input = UseApiApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            startFusekiServer = prop.getProperty("fuseki.server.start");
        }

        // Fuseki server startup
        if (startFusekiServer.equals("True")) {
            Model model = ModelFactory.createDefaultModel();
            try (InputStream inputStream = new ClassPathResource("use_model.rdf").getInputStream()) {
                model.read(inputStream, "");
            }
            Dataset ds = DatasetFactory.create(model);

            FusekiServer server = FusekiServer.create()
                    .add("/use_dataset", ds)
                    .build();
            server.start();

            System.out.println("\n=== Fuseki Server fully started ===\n");
        }
    }
}
