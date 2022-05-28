package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialCallRequestRequest;
import com.vire.model.response.SocialCallRequestResponse;
import com.vire.service.SocialCallRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.SOCIALCALLREQUEST_REQUEST_PATH_API)
public class SocialCallRequestController {

  @Autowired
  SocialCallRequestService socialCallRequestService;

  @PostMapping("/create")
  public ResponseEntity<SocialCallRequestResponse> create(@RequestBody SocialCallRequestRequest request) {
    return new ResponseEntity<>(socialCallRequestService.create(request), HttpStatus.CREATED);
  }

  /*@PutMapping("/update")
  public ResponseEntity<SocialCallRequestResponse> update(@RequestBody SocialCallRequestRequest request) {
    return new ResponseEntity<>(socialCallRequestService.update(request), HttpStatus.CREATED);
  }*/

  @DeleteMapping("/{socialCallRequestId}")
  public ResponseEntity<SocialCallRequestResponse> delete(
          @PathVariable(value = "socialCallRequestId") Long socialCallRequestId) {
    return new ResponseEntity<>(socialCallRequestService.delete(socialCallRequestId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<SocialCallRequestResponse>> retrieveAll() {
    return new ResponseEntity<>(socialCallRequestService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{socialCallRequestId}")
  public ResponseEntity<SocialCallRequestResponse> retrieveById(@PathVariable Long socialCallRequestId) {
    return new ResponseEntity<SocialCallRequestResponse>(socialCallRequestService.retrieveById(socialCallRequestId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<SocialCallRequestResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(socialCallRequestService.search(searchString), HttpStatus.OK);
  }
}
