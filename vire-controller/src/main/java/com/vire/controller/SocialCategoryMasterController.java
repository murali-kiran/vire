package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialCategoryMasterRequest;
import com.vire.model.response.SocialCategoryMasterResponse;
import com.vire.service.SocialCategoryMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.SOCIALCATEGORYMASTER_REQUEST_PATH_API)
public class SocialCategoryMasterController {

  @Autowired
  SocialCategoryMasterService socialCategoryMasterService;

  @PostMapping("/create")
  public ResponseEntity<SocialCategoryMasterResponse> create(@RequestBody SocialCategoryMasterRequest request) {
    return new ResponseEntity<>(socialCategoryMasterService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<SocialCategoryMasterResponse> update(@RequestBody SocialCategoryMasterRequest request) {
    return new ResponseEntity<>(socialCategoryMasterService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{socialCategoryMasterId}")
  public ResponseEntity<SocialCategoryMasterResponse> delete(
          @PathVariable(value = "socialCategoryMasterId") Long socialCategoryMasterId) {
    return new ResponseEntity<>(socialCategoryMasterService.delete(socialCategoryMasterId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<SocialCategoryMasterResponse>> retrieveAll() {
    return new ResponseEntity<>(socialCategoryMasterService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{socialCategoryMasterId}")
  public ResponseEntity<SocialCategoryMasterResponse> retrieveById(@PathVariable Long socialCategoryMasterId) {
    return new ResponseEntity<SocialCategoryMasterResponse>(socialCategoryMasterService.retrieveById(socialCategoryMasterId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<SocialCategoryMasterResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(socialCategoryMasterService.search(searchString), HttpStatus.OK);
  }
}
