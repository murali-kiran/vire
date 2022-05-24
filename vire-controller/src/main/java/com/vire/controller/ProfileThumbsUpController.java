package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ProfileThumbsUpRequest;
import com.vire.model.response.ProfileThumbsUpResponse;
import com.vire.service.ProfileThumbsUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.PROFILETHUMBSUP_REQUEST_PATH_API)
public class ProfileThumbsUpController {

  @Autowired
  ProfileThumbsUpService profileThumbsUpService;

  @PostMapping("/create")
  public ResponseEntity<ProfileThumbsUpResponse> create(@RequestBody ProfileThumbsUpRequest request) {
    return new ResponseEntity<>(profileThumbsUpService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileThumbsUpResponse> update(@RequestBody ProfileThumbsUpRequest request) {
    return new ResponseEntity<>(profileThumbsUpService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{profileThumbsUpId}")
  public ResponseEntity<ProfileThumbsUpResponse> delete(
          @PathVariable(value = "profileThumbsUpId") Long profileThumbsUpId) {
    return new ResponseEntity<>(profileThumbsUpService.delete(profileThumbsUpId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProfileThumbsUpResponse>> retrieveAll() {
    return new ResponseEntity<>(profileThumbsUpService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{profileThumbsUpId}")
  public ResponseEntity<ProfileThumbsUpResponse> retrieveById(@PathVariable Long profileThumbsUpId) {
    return new ResponseEntity<ProfileThumbsUpResponse>(profileThumbsUpService.retrieveById(profileThumbsUpId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ProfileThumbsUpResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileThumbsUpService.search(searchString), HttpStatus.OK);
  }
}
