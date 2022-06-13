package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ChannelProfileRequest;
import com.vire.model.request.ChannelProfileRequest;
import com.vire.model.response.ChannelProfileResponse;
import com.vire.model.response.ChannelProfileResponse;
import com.vire.service.ChannelProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
  @Operation(summary = "Update channel profile status")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "updated channel profile status successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channel profile status updating failed",
                  content = @Content) })
  @PutMapping("channelProfileStatus/update")
  public ResponseEntity<ChannelProfileResponse> updateStatus(@Valid @RequestBody ChannelProfileRequest request) {
    return new ResponseEntity<>(channelProfileService.updateChannelProfileStatus(request), HttpStatus.CREATED);
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
