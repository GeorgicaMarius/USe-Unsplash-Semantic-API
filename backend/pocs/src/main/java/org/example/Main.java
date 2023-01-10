package org.example;

import org.example.helpers.UseDsCollectionQueryHelper;
import org.example.helpers.UseDsPhotoQueryHelper;
import org.example.models.UseCollectionQueryDto;
import org.example.models.UsePhotoQueryDto;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UsePhotoQueryDto photo1 = UseDsPhotoQueryHelper.getPhotoById("IpIenETtzXM");
        System.out.println(photo1);

//        List<UsePhotoQueryDto> photos = UseDsQueryHelper.searchPhotosByKeyword("tulips", 0, 10);
//        List<UsePhotoQueryDto> photos = UseDsQueryHelper.filterPhotosByCountry("Romania", 0, 10);
//        List<UsePhotoQueryDto> photos = UseDsQueryHelper.filterPhotosByCity("New York", 0, 10);
//        for(UsePhotoQueryDto photo: photos) {
//            System.out.println(photo);
//        }

//        DbpediaCityQueryDto city = DbpediaDsQueryHelper.getCityByName("Iași");
//        DbpediaCityQueryDto city = DbpediaDsQueryHelper.getCityByName("New York City");
//        DbpediaCityQueryDto city = DbpediaDsQueryHelper.getCityByName("Saint Petersburg");
//        System.out.println(city);

//        UseCollectionQueryDto collection1 = UseDsCollectionQueryHelper.getCollectionById("1900273");
//        System.out.println(collection1);

//        List<UseCollectionQueryDto> collections = UseDsCollectionQueryHelper.searchCollectionsByTitle("business", 0, 10);
//        List<UseCollectionQueryDto> collections = UseDsCollectionQueryHelper.filterCollectionsByTitle("business", 0, 10);
//        System.out.println(collections);
    }
}