package ro.wade.useapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
            @RequestParam(value = "city", required = false) String filterCity,
            @RequestParam("photoIds") List<String> photoIds
            ) {

        return useDsPhotoQueryHelper.getPhotosFilter(offset, limit,
                filterPhotographerFirstName, filterPhotographerLastName, filterCameraMake, filterCountry, filterCity, photoIds);
    }

    @GetMapping("/photos/{photoId}")
    public UsePhotoQueryDto getPhotoById(@PathVariable("photoId") String photoId) {
        return useDsPhotoQueryHelper.getPhotoById(photoId);
    }

    @PostMapping("/photos")
    public List<UsePhotoQueryDto> getPhotosByIds(@RequestBody List<String> photoIds) {
        return useDsPhotoQueryHelper.getPhotosByIds(photoIds);
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
            @RequestParam(value = "keyword", required = false) String searchKeyword,
            @RequestParam(value = "masterKeyword", required = false) String masterKeyword) {

        return useDsPhotoQueryHelper.getPhotosSearch(offset, limit, searchPhotographerFirstName,
                searchPhotographerLastName, searchCameraMake, searchCountry, searchCity, searchKeyword, masterKeyword);
    }
}
