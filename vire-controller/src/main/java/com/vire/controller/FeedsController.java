package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedsRequest;
import com.vire.model.response.FeedsResponse;
import com.vire.service.FeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.FEEDS_REQUEST_PATH_API)
public class FeedsController {
    @Autowired
    FeedsService feedsService;

    @PostMapping("/create")
    public ResponseEntity<FeedsResponse> create(@RequestBody FeedsRequest request) {
        return new ResponseEntity<>(feedsService.createFeeds(request), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<FeedsResponse> update(@RequestBody FeedsRequest request) {
        return new ResponseEntity<>(feedsService.updateFeeds(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{feedid}")
    public ResponseEntity<FeedsResponse> delete(
            @PathVariable(value = "feedid") Long feedsId) {
        return new ResponseEntity<>(feedsService.deleteFeedsPost(feedsId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedsResponse>> retrieveAll() {
        return new ResponseEntity<>(feedsService.getFeeds(), HttpStatus.OK);
    }

    @GetMapping("/{feedid}")
    public ResponseEntity<FeedsResponse> retrieveById(@PathVariable(name = "feedid") Long feedsId) {
        return new ResponseEntity<FeedsResponse>(feedsService.retrieveById(feedsId), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<FeedsResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(feedsService.search(searchString), HttpStatus.OK);
    }

    @GetMapping("feedsdetails/{feedid}")
    public ResponseEntity<FeedsResponse> findById(@PathVariable(name = "feedsid") Long feedsPostId) {
        return new ResponseEntity<FeedsResponse>(feedsService.retrieveFeedsDetailsById(feedsPostId), HttpStatus.OK);
    }
   /* @GetMapping("feedsposts/{profileid}")
    public ResponseEntity<List<FeedsResponse>> findByIdProfileId(@PathVariable(name = "profileid") Long profileId) {
        return new ResponseEntity<List<FeedsResponse>>(feedsService.retrievePostsByProfileId(profileId), HttpStatus.OK);
    }*/
}
