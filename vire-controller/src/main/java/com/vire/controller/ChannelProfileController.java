package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ChannelProfileRequest;
import com.vire.model.response.ChannelProfileResponse;
import com.vire.service.ChannelProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.CHANNELPROFILE_REQUEST_PATH_API)
public class ChannelProfileController {

  @Autowired
  ChannelProfileService channelProfileService;

  @PostMapping("/create")
  public ResponseEntity<ChannelProfileResponse> create(@RequestBody ChannelProfileRequest request) {
    return new ResponseEntity<>(channelProfileService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ChannelProfileResponse> update(@RequestBody ChannelProfileRequest request) {
    return new ResponseEntity<>(channelProfileService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{channelProfileId}")
  public ResponseEntity<ChannelProfileResponse> delete(
          @PathVariable(value = "channelProfileId") Long channelProfileId) {
    return new ResponseEntity<>(channelProfileService.delete(channelProfileId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ChannelProfileResponse>> retrieveAll() {
    return new ResponseEntity<>(channelProfileService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{channelProfileId}")
  public ResponseEntity<ChannelProfileResponse> retrieveById(@PathVariable Long channelProfileId) {
    return new ResponseEntity<ChannelProfileResponse>(channelProfileService.retrieveById(channelProfileId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ChannelProfileResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(channelProfileService.search(searchString), HttpStatus.OK);
  }
}
