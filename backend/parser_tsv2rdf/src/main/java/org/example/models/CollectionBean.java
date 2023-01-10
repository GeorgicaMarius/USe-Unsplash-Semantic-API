package org.example.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.example.converters.CustomDateConverter;

import java.util.Date;

public class CollectionBean {
    @CsvBindByName(column = "photo_id")
    public String photoId;

    @CsvBindByName(column = "collection_id")
    public String collectionId;

    @CsvBindByName(column = "collection_title")
    public String collectionTitle;

    @CsvCustomBindByName(column = "photo_collected_at", converter = CustomDateConverter.class)
    public Date photoCollectedAt;

    @Override
    public String toString() {
        return "CollectionBean{" + "\n" +
                "\tphotoId='" + photoId + '\'' + "\n" +
                "\tcollectionId='" + collectionId + '\'' + "\n" +
                "\tcollectionTitle='" + collectionTitle + '\'' + "\n" +
                "\tphotoCollectedAt=" + photoCollectedAt + "\n" +
                '}';
    }
}
