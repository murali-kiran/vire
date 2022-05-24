package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ProfileThumbsDownRequest;
import com.vire.model.response.ProfileThumbsDownResponse;
import com.vire.service.ProfileThumbsDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.PROFILETHUMBSDOWN_REQUEST_PATH_API)
public class ProfileThumbsDownController {

  @Autowired
  ProfileThumbsDownService profileThumbsDownService;

  @PostMapping("/create")
  public ResponseEntity<ProfileThumbsDownResponse> create(@RequestBody ProfileThumbsDownRequest request) {
    return new ResponseEntity<>(profileThumbsDownService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileThumbsDownResponse> update(@RequestBody ProfileThumbsDownRequest request) {
    return new ResponseEntity<>(profileThumbsDownService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{profileThumbsDownId}")
  public ResponseEntity<ProfileThumbsDownResponse> delete(
          @PathVariable(value = "profileThumbsDownId") Long profileThumbsDownId) {
    return new ResponseEntity<>(profileThumbsDownService.delete(profileThumbsDownId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProfileThumbsDownResponse>> retrieveAll() {
    return new ResponseEntity<>(profileThumbsDownService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{profileThumbsDownId}")
  public ResponseEntity<ProfileThumbsDownResponse> retrieveById(@PathVariable Long profileThumbsDownId) {
    return new ResponseEntity<ProfileThumbsDownResponse>(profileThumbsDownService.retrieveById(profileThumbsDownId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ProfileThumbsDownResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileThumbsDownService.search(searchString), HttpStatus.OK);
  }
}
