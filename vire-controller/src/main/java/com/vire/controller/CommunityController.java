package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.CommunityRequest;
import com.vire.model.response.CommunityResponse;
import com.vire.service.CommunityService;
import com.vire.constant.VireConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.COMMUNITY_REQUEST_PATH_API)
public class CommunityController {

  @Autowired
  CommunityService communityService;

  @PostMapping("/create")
  public ResponseEntity<CommunityResponse> create(@Valid @RequestBody CommunityRequest request) {
    return new ResponseEntity<>(communityService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<CommunityResponse> update(@Valid @RequestBody CommunityRequest request) {
    return new ResponseEntity<>(communityService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{communityId}")
  public ResponseEntity<CommunityResponse> delete(
          @PathVariable(value = "id") Long communityId) {
    return new ResponseEntity<>(communityService.delete(communityId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<CommunityResponse>> retrieveAll() {
    return new ResponseEntity<>(communityService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{communityId}")
  public ResponseEntity<CommunityResponse> retrieveById(@PathVariable Long communityId) {
    return new ResponseEntity<CommunityResponse>(communityService.retrieveById(communityId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<CommunityResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(communityService.search(searchString), HttpStatus.OK);
  }
}
