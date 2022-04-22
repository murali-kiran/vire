package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedsSendToRequest;
import com.vire.model.response.FeedsSendToResponse;
import com.vire.service.FeedsSendToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.SEND_TO_REQUEST_PATH_API)
public class FeedsSendToController {

    @Autowired
    FeedsSendToService feedsSendToService;

    @PostMapping("/create")
    public ResponseEntity<FeedsSendToResponse> createSent(@RequestBody FeedsSendToRequest request){
        return new ResponseEntity<>(feedsSendToService.create(request), HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<FeedsSendToResponse> updateSent(@RequestBody FeedsSendToRequest request){
        return new ResponseEntity<>(feedsSendToService.update(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{feedsendtoid}")
    public ResponseEntity<FeedsSendToResponse> delete(
            @PathVariable(value = "feedsendtoid") Long sentId) {
        return new ResponseEntity<>(feedsSendToService.deletePostSent(sentId), HttpStatus.OK);
    }

    @GetMapping("/{feedsendtoid}")
    public ResponseEntity<FeedsSendToResponse> retrieveById(@PathVariable(name="feedsendtoid") Long postSentId){
        return new ResponseEntity<FeedsSendToResponse>(feedsSendToService.retrieveById(postSentId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<FeedsSendToResponse>> searchSent(
            @RequestParam(value = "searchpostsent") String searchString) {
        return new ResponseEntity<>(feedsSendToService.searchSent(searchString), HttpStatus.OK);
    }
}
