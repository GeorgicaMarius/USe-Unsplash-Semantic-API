package ro.wade.useapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.wade.useapi.helpers.UseDsCollectionQueryHelper;
import ro.wade.useapi.models.UseCollectionQueryDto;

import java.util.List;

@RestController
public class CollectionController {
    @Autowired
    private UseDsCollectionQueryHelper useDsCollectionQueryHelper;

    @GetMapping("/collections")
    public List<UseCollectionQueryDto> getCollectionsFilter(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "15") Integer limit,
            @RequestParam(value = "title", required = false) String filterTitle) {

        return useDsCollectionQueryHelper.getCollectionsFilter(offset, limit, filterTitle);
    }

    @GetMapping("/collections/{collectionId}")
    public UseCollectionQueryDto getCollectionById(@PathVariable("collectionId") String collectionId) {
        return useDsCollectionQueryHelper.getCollectionById(collectionId);
    }

    @GetMapping("/collections/search")
    public List<UseCollectionQueryDto> getCollectionsSearch(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "15") Integer limit,
            @RequestParam(value = "title", required = false) String searchTitle) {

        return useDsCollectionQueryHelper.getCollectionsSearch(offset, limit, searchTitle);
    }
}
