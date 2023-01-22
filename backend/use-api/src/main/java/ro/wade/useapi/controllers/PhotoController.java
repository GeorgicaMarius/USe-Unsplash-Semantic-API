package ro.wade.useapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.wade.useapi.helpers.UseDsPhotoQueryHelper;
import ro.wade.useapi.models.UsePhotoQueryDto;

import java.util.List;

@RestController
public class PhotoController {
    @Autowired
    private UseDsPhotoQueryHelper useDsPhotoQueryHelper;

    @GetMapping("/photos")
    public List<UsePhotoQueryDto> getPhotosFilter(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "15") Integer limit,
            @RequestParam(value = "photographerFirstName", required = false) String filterPhotographerFirstName,
            @RequestParam(value = "photographerLastName", required = false) String filterPhotographerLastName,
            @RequestParam(value = "cameraMake", required = false) String filterCameraMake,
            @RequestParam(value = "country", required = false) String filterCountry,
            @RequestParam(value = "city", required = false) String filterCity) {

        return useDsPhotoQueryHelper.getPhotosFilter(offset, limit,
                filterPhotographerFirstName, filterPhotographerLastName, filterCameraMake, filterCountry, filterCity);
    }

    @GetMapping("/photos/{photoId}")
    public UsePhotoQueryDto getPhotoById(@PathVariable("photoId") String photoId) {
        return useDsPhotoQueryHelper.getPhotoById(photoId);
    }

    @GetMapping("/photos/search")
    public List<UsePhotoQueryDto> getPhotosSearch(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "15") Integer limit,
            @RequestParam(value = "photographerFirstName", required = false) String searchPhotographerFirstName,
            @RequestParam(value = "photographerLastName", required = false) String searchPhotographerLastName,
            @RequestParam(value = "cameraMake", required = false) String searchCameraMake,
            @RequestParam(value = "country", required = false) String searchCountry,
            @RequestParam(value = "city", required = false) String searchCity,
            @RequestParam(value = "keyword", required = false) String searchKeyword) {

        return useDsPhotoQueryHelper.getPhotosSearch(offset, limit, searchPhotographerFirstName,
                searchPhotographerLastName, searchCameraMake, searchCountry, searchCity, searchKeyword);
    }
}
