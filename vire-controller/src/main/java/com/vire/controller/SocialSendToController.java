package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialSendToRequest;
import com.vire.model.response.SocialSendToResponse;
import com.vire.service.SocialSendToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.SENT_REQUEST_PATH_API)
public class SocialSendToController {

    @Autowired
    SocialSendToService socialSendToService;

    @PostMapping("/create")
    public ResponseEntity<SocialSendToResponse> createSent(@RequestBody SocialSendToRequest request){
        return new ResponseEntity<>(socialSendToService.create(request), HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<SocialSendToResponse> updateSent(@RequestBody SocialSendToRequest request){
        return new ResponseEntity<>(socialSendToService.update(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{sendtoid}")
    public ResponseEntity<SocialSendToResponse> deleteSent(
            @PathVariable(value = "sendtoid") Long sentId) {
        return new ResponseEntity<>(socialSendToService.deletePostSent(sentId), HttpStatus.OK);
    }

    @GetMapping("/{sendtoid}")
    public ResponseEntity<SocialSendToResponse> retrieveById(@PathVariable(name="sendtoid") Long postSentId){
        return new ResponseEntity<SocialSendToResponse>(socialSendToService.retrieveById(postSentId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<SocialSendToResponse>> searchSent(
            @RequestParam(value = "searchpostsent") String searchString) {
        return new ResponseEntity<>(socialSendToService.searchSent(searchString), HttpStatus.OK);
    }
}
