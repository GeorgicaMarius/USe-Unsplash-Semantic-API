package org.example.models;

import java.util.List;

public class UseCollectionQueryDto {

    public String collectionId;
    public String collectionTitle;
    public List<String> photoIds;

    @Override
    public String toString() {
        return "UseCollectionQueryDto{" + "\n" +
                "\tcollectionId='" + collectionId + '\'' + "\n" +
                "\tcollectionTitle='" + collectionTitle + '\'' + "\n" +
                "\tphotoIds=" + photoIds + "\n" +
                '}';
    }
}
