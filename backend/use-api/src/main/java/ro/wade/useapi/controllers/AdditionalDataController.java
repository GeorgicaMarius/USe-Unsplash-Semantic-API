package ro.wade.useapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.wade.useapi.helpers.DbpediaDsQueryHelper;
import ro.wade.useapi.models.DbpediaCityQueryDto;
import ro.wade.useapi.models.DbpediaCountryQueryDto;

@RestController
public class AdditionalDataController {
    @Autowired
    private DbpediaDsQueryHelper dbpediaDsQueryHelper;

    @GetMapping("/additional-data/city/{cityName}")
    public DbpediaCityQueryDto getCityByName(@PathVariable("cityName") String cityName) {
        return dbpediaDsQueryHelper.getCityByName(cityName);
    }

    @GetMapping("/additional-data/country/{countryName}")
    public DbpediaCountryQueryDto getCountryByName(@PathVariable("countryName") String countryName) {
        return dbpediaDsQueryHelper.getCountryByName(countryName);
    }
}
