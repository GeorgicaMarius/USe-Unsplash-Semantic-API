package ro.wade.useapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.wade.useapi.helpers.DbpediaDsQueryHelper;
import ro.wade.useapi.models.DbpediaCityQueryDto;

@RestController
public class AdditionalDataController {

    @GetMapping("/additional-data/city/{cityName}")
    public DbpediaCityQueryDto getCityByName(@PathVariable("cityName") String cityName) {
        return DbpediaDsQueryHelper.getCityByName(cityName);
    }
}
