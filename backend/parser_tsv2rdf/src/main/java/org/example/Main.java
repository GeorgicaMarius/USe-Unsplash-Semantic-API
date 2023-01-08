package org.example;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.example.models.KeywordBean;
import org.example.models.PhotoBean;
import org.example.vocabulary.SCHEMA;
import org.example.vocabulary.USE;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    private static final String basePath = "C:\\Users\\User03\\Desktop\\wade\\use-files\\";
    private static final String photosTsvFilePath = basePath + "photos.tsv";
    private static final String keywordsTsvFilePath = basePath + "keywords.tsv";
    private static final String outputRdfNoKeywordsFilePath = basePath + "temp_model.rdf";
    private static final String outputRdfWithKeywordsFilePath = basePath + "use_model.rdf";


    private static void createRdfModelFromPhotosTsv() throws IOException {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // explicit prefix definitions
        model.setNsPrefix("schema", SCHEMA.getURI());
        model.setNsPrefix("use", USE.getURI());

        try (FileReader fr = new FileReader(photosTsvFilePath, StandardCharsets.UTF_8);
             FileWriter fw = new FileWriter(outputRdfNoKeywordsFilePath, StandardCharsets.UTF_8)) {

            CsvToBean<PhotoBean> csvToBean = new CsvToBeanBuilder<PhotoBean>(fr)
                    .withType(PhotoBean.class)
                    .withSeparator('\t')
                    .build();

            for (PhotoBean photo : csvToBean) {
                // System.out.println(photo);

                // create the resource and add the properties cascading style
                Resource photoRes
                        = model.createResource(USE.getCURIE(photo.photoId))
                        .addProperty(RDF.type, SCHEMA.Photograph)
                        .addLiteral(SCHEMA.identifier, photo.photoId)
                        .addLiteral(SCHEMA.sameAs, photo.photoUrl)
                        .addLiteral(SCHEMA.description, photo.photoDescription)
                        .addLiteral(USE.downloadsCount, photo.statsDownloads)
                        .addLiteral(USE.viewsCount, photo.statsViews)
                        .addLiteral(USE.exifCameraMake, photo.exifCameraMake)
                        .addLiteral(USE.exifCameraModel, photo.exifCameraModel)
                        .addLiteral(USE.exifIso, photo.exifIso)
                        .addLiteral(USE.exifApertureValue, photo.exifApertureValue)
                        .addLiteral(USE.exifFocalLength, photo.exifFocalLength)
                        .addLiteral(USE.exifExposureTime, photo.exifExposureTime)
                        .addProperty(SCHEMA.associatedMedia,
                                model.createResource()
                                        .addProperty(RDF.type, SCHEMA.MediaObject)
                                        .addLiteral(SCHEMA.contentUrl, photo.photoImageUrl)
                                        .addLiteral(SCHEMA.uploadDate, photo.photoSubmittedAt.getTime())
                                        .addLiteral(SCHEMA.width, photo.photoWidth)
                                        .addLiteral(SCHEMA.height, photo.photoHeight))
                        .addProperty(SCHEMA.contentLocation,
                                model.createResource()
                                        .addProperty(RDF.type, SCHEMA.Place)
                                        .addLiteral(SCHEMA.addressLocality, photo.photoLocationCity)
                                        .addLiteral(SCHEMA.addressCountry, photo.photoLocationCountry)
                                        .addLiteral(SCHEMA.longitude, photo.photoLocationLongitude)
                                        .addLiteral(SCHEMA.latitude, photo.photoLocationLatitude)
                                        .addLiteral(SCHEMA.name, photo.photoLocationName))
                        .addProperty(SCHEMA.author,
                                model.createResource()
                                        .addProperty(RDF.type, SCHEMA.Person)
                                        .addLiteral(SCHEMA.familyName, photo.photographerLastName)
                                        .addLiteral(SCHEMA.givenName, photo.photographerFirstName)
                                        .addLiteral(SCHEMA.callSign, photo.photographerUsername));
            }

            // write the model in XML form to a file
            model.write(fw);

            // write the model in N-TRIPLES form
            // RDFDataMgr.write(out, model, Lang.NTRIPLES);
        }
    }

    private static boolean keywordIsRelevant(KeywordBean keywordBean) {
        // relevant if added by user or AI confidence above 50%
        return keywordBean.suggestedByUser ||
                (keywordBean.aiService1Confidence != null && keywordBean.aiService1Confidence >= 50) ||
                (keywordBean.aiService2Confidence != null && keywordBean.aiService2Confidence >= 50);
    }

    private static void addKeywordsToRdfModel() throws IOException {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        try (FileReader model_fr = new FileReader(outputRdfNoKeywordsFilePath, StandardCharsets.UTF_8);
             FileReader keywords_fr = new FileReader(keywordsTsvFilePath, StandardCharsets.UTF_8);
             FileWriter model_with_keywords_fw = new FileWriter(outputRdfWithKeywordsFilePath, StandardCharsets.UTF_8)) {

            // read the RDF/XML file
            model.read(model_fr, "");

            CsvToBean<KeywordBean> csvToBean = new CsvToBeanBuilder<KeywordBean>(keywords_fr)
                    .withType(KeywordBean.class)
                    .withSeparator('\t')
                    .build();

            String previousPhotoId = null;
            StringBuilder keywordsComp = null;

            for (KeywordBean keywordBean : csvToBean) {
                // System.out.println(keywordBean);

                // add keyword only if relevant
                if (keywordIsRelevant(keywordBean)) {
                    if (keywordBean.photoId.equals(previousPhotoId)) {
                        // if intermediary keyword entry within an photoId group
                        keywordsComp.append(", ").append(keywordBean.keyword);
                    } else {
                        // if first keyword entry from a new photoId group - save keywords for previous photoId
                        //   except when is also first keyword from file
                        if (previousPhotoId != null) {
                            Resource photoRes = model.getResource(USE.getCURIE(previousPhotoId));
                            photoRes.addLiteral(SCHEMA.keywords, keywordsComp.toString());
                        }

                        previousPhotoId = keywordBean.photoId;
                        keywordsComp = new StringBuilder(keywordBean.keyword);
                    }
                }
            }
            // save keywords for the last photoId
            System.out.println("Last keyword entry: " + previousPhotoId);
            Resource photoRes = model.getResource(USE.getCURIE(previousPhotoId));
            assert keywordsComp != null; // only if file has no keyword entry...
            photoRes.addLiteral(SCHEMA.keywords, keywordsComp.toString());

            // write the model in XML form to a file
            model.write(model_with_keywords_fw);
        }
    }

    public static void main(String[] args) throws IOException {

        createRdfModelFromPhotosTsv();

        addKeywordsToRdfModel();
    }
}