package com.vire.controller;

import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.response.CommunityProfileResponse;
import com.vire.service.CommunityProfileService;
import com.vire.constant.VireConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.COMMUNITYPROFILE_REQUEST_PATH_API)
public class CommunityProfileController {

  @Autowired
  CommunityProfileService communityProfileService;

  @PostMapping("/create")
  public ResponseEntity<CommunityProfileResponse> create(@RequestBody CommunityProfileRequest request) {
    return new ResponseEntity<>(communityProfileService.create(request), HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<CommunityProfileResponse> update(@RequestBody CommunityProfileRequest request) {
    return new ResponseEntity<>(communityProfileService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{communityProfileId}")
  public ResponseEntity<CommunityProfileResponse> delete(
          @PathVariable(value = "id") Long communityProfileId) {
    return new ResponseEntity<>(communityProfileService.delete(communityProfileId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<CommunityProfileResponse>> retrieveAll() {
    return new ResponseEntity<>(communityProfileService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{communityProfileId}")
  public ResponseEntity<CommunityProfileResponse> retrieveById(@PathVariable Long communityProfileId) {
    return new ResponseEntity<CommunityProfileResponse>(communityProfileService.retrieveById(communityProfileId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<CommunityProfileResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(communityProfileService.search(searchString), HttpStatus.OK);
  }
}
