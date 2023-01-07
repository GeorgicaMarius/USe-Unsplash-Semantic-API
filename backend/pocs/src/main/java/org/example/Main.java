package org.example;

import org.example.helpers.DbpediaDsQueryHelper;
import org.example.helpers.UseDsQueryHelper;
import org.example.models.DbpediaCityQueryDto;
import org.example.models.UsePhotoQueryDto;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UsePhotoQueryDto photo1 = UseDsQueryHelper.getPhotoById("IpIenETtzXM");
        System.out.println(photo1);

//        List<UsePhotoQueryDto> photos = UseDsQueryHelper.getPhotosByKeyword("tulips", 0, 10);
//        List<UsePhotoQueryDto> photos = UseDsQueryHelper.getPhotosByCountry("Romania", 0, 10);
//        List<UsePhotoQueryDto> photos = UseDsQueryHelper.getPhotosByCity("New York", 0, 10);
//        for(UsePhotoQueryDto photo: photos) {
//            System.out.println(photo);
//        }

//        DbpediaCityQueryDto city = DbpediaDsQueryHelper.getCityByName("Iași");
//        DbpediaCityQueryDto city = DbpediaDsQueryHelper.getCityByName("New York City");
//        DbpediaCityQueryDto city = DbpediaDsQueryHelper.getCityByName("Saint Petersburg");
//        System.out.println(city);
    }
}