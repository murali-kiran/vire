package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ProfileBlockRequest;
import com.vire.model.response.ProfileBlockResponse;
import com.vire.service.ProfileBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.PROFILEBLOCK_REQUEST_PATH_API)
public class ProfileBlockController {

  @Autowired
  ProfileBlockService profileBlockService;

  @PostMapping("/create")
  public ResponseEntity<ProfileBlockResponse> create(@RequestBody ProfileBlockRequest request) {
    return new ResponseEntity<>(profileBlockService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileBlockResponse> update(@RequestBody ProfileBlockRequest request) {
    return new ResponseEntity<>(profileBlockService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{profileBlockId}")
  public ResponseEntity<ProfileBlockResponse> delete(
          @PathVariable(value = "profileBlockId") Long profileBlockId) {
    return new ResponseEntity<>(profileBlockService.delete(profileBlockId), HttpStatus.OK);
  }
  @DeleteMapping("/{profileId}/{blockedProfileId}")
  public ResponseEntity<ProfileBlockResponse> unblock(
          @PathVariable(value = "profileId") Long profileId, @PathVariable(value = "blockedProfileId") Long blockedProfileId) {
    return new ResponseEntity<>(profileBlockService.unblock(profileId, blockedProfileId), HttpStatus.OK);
  }
  @GetMapping("/all")
  public ResponseEntity<List<ProfileBlockResponse>> retrieveAll() {
    return new ResponseEntity<>(profileBlockService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{profileBlockId}")
  public ResponseEntity<ProfileBlockResponse> retrieveById(@PathVariable Long profileBlockId) {
    return new ResponseEntity<ProfileBlockResponse>(profileBlockService.retrieveById(profileBlockId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ProfileBlockResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileBlockService.search(searchString), HttpStatus.OK);
  }
  @GetMapping("getblockedlist")
  public ResponseEntity<List<ProfileBlockResponse>> getBlockedListByProfile(
          @RequestParam(value = "profileid") String profileId) {
    return new ResponseEntity<>(profileBlockService.getBlockedListByProfile(profileId), HttpStatus.OK);
  }
}
