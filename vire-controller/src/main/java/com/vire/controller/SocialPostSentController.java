package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialPostSentRequest;
import com.vire.model.response.SocialPostSentResponse;
import com.vire.service.SocialPostSentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.SENT_REQUEST_PATH_API)
public class SocialPostSentController {

    @Autowired
    SocialPostSentService socialPostSentService;

    @PostMapping("/createPostSent")
    public ResponseEntity<SocialPostSentResponse> createSent(@RequestBody SocialPostSentRequest request){
        return new ResponseEntity<>(socialPostSentService.create(request), HttpStatus.CREATED);
    }
    @PostMapping("/updatePostPostSent")
    public ResponseEntity<SocialPostSentResponse> updateSent(@RequestBody SocialPostSentRequest request){
        return new ResponseEntity<>(socialPostSentService.update(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postsentid}")
    public ResponseEntity<SocialPostSentResponse> deleteSent(
            @PathVariable(value = "postsentid") Long sentId) {
        return new ResponseEntity<>(socialPostSentService.deletePostSent(sentId), HttpStatus.OK);
    }

    @GetMapping("/{postsentid}")
    public ResponseEntity<SocialPostSentResponse> retrieveById(@PathVariable(name="postsentid") Long postSentId){
        return new ResponseEntity<SocialPostSentResponse>(socialPostSentService.retrieveById(postSentId), HttpStatus.OK);
    }
    @GetMapping("searchpostsent")
    public ResponseEntity<List<SocialPostSentResponse>> searchSent(
            @RequestParam(value = "searchpostsent") String searchString) {
        return new ResponseEntity<>(socialPostSentService.searchSent(searchString), HttpStatus.OK);
    }
}
