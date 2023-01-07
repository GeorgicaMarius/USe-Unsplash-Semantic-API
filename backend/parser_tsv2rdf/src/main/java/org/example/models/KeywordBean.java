package org.example.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.example.converters.CustomSuggestedByUserConverter;

public class KeywordBean {
    @CsvBindByName(column = "photo_id")
    public String photoId;

    @CsvBindByName(column = "keyword")
    public String keyword;

    @CsvBindByName(column = "ai_service_1_confidence")
    public Double aiService1Confidence;

    @CsvBindByName(column = "ai_service_2_confidence")
    public Double aiService2Confidence;

    @CsvCustomBindByName(column = "suggested_by_user", converter = CustomSuggestedByUserConverter.class)
    public Boolean suggestedByUser;

    @Override
    public String toString() {
        return "KeywordBean{" + "\n" +
                "\tphotoId='" + photoId + '\'' + "\n" +
                "\tkeyword='" + keyword + '\'' + "\n" +
                "\taiService1Confidence=" + aiService1Confidence + "\n" +
                "\taiService2Confidence=" + aiService2Confidence + "\n" +
                "\tsuggestedByUser=" + suggestedByUser + "\n" +
                '}';
    }
}
