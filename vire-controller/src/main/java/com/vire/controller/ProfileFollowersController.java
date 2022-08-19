package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ProfileFollowersRequest;
import com.vire.model.response.ProfileFollowersResponse;
import com.vire.service.ProfileFollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.PROFILEFOLLOWERS_REQUEST_PATH_API)
public class ProfileFollowersController {

  @Autowired
  ProfileFollowersService profileFollowersService;

  @PostMapping("/create")
  public ResponseEntity<ProfileFollowersResponse> create(@RequestBody ProfileFollowersRequest request) {
    return new ResponseEntity<>(profileFollowersService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileFollowersResponse> update(@RequestBody ProfileFollowersRequest request) {
    return new ResponseEntity<>(profileFollowersService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{profileFollowersId}")
  public ResponseEntity<ProfileFollowersResponse> delete(
          @PathVariable(value = "profileFollowersId") Long profileFollowersId) {
    return new ResponseEntity<>(profileFollowersService.delete(profileFollowersId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProfileFollowersResponse>> retrieveAll() {
    return new ResponseEntity<>(profileFollowersService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{profileFollowersId}")
  public ResponseEntity<ProfileFollowersResponse> retrieveById(@PathVariable Long profileFollowersId) {
    return new ResponseEntity<ProfileFollowersResponse>(profileFollowersService.retrieveById(profileFollowersId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ProfileFollowersResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileFollowersService.search(searchString), HttpStatus.OK);
  }
  @GetMapping("/followers/{profileid}/{loginprofileid}")
  public ResponseEntity<List<ProfileFollowersResponse>> getFollowers(@PathVariable(value = "profileid") String profileId,@PathVariable(value="loginprofileid") String loginProfileId) {
    return new ResponseEntity<>(profileFollowersService.getFollowers(profileId, loginProfileId), HttpStatus.OK);
  }
  @GetMapping("/following/{profileid}/{loginprofileid}")
  public ResponseEntity<List<ProfileFollowersResponse>> getFollowing(@PathVariable(value = "profileid") String profileId,@PathVariable(value="loginprofileid") String loginProfileId) {
    return new ResponseEntity<>(profileFollowersService.getFollowing(profileId, loginProfileId), HttpStatus.OK);
  }
}
