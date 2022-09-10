package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ChannelRequest;
import com.vire.model.response.ChannelResponse;
import com.vire.model.response.MinimalProfileResponse;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.CHANNEL_REQUEST_PATH_API)
@CrossOrigin
public class ChannelController {

  @Autowired
  ChannelService channelService;

  @Operation(summary = "Create channel")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully created channel",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Creating channel failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<ChannelResponse> create(@RequestBody ChannelRequest request) {
    return new ResponseEntity<>(channelService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update channel")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully updated channel",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "updating channel failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<ChannelResponse> update(@RequestBody ChannelRequest request) {
    return new ResponseEntity<>(channelService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete channel")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully deleted channel",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "delete channel failed",
                  content = @Content) })
  @DeleteMapping("/{channelId}")
  public ResponseEntity<ChannelResponse> delete(@PathVariable(value = "channelId") Long channelId) {
    return new ResponseEntity<>(channelService.delete(channelId), HttpStatus.OK);
  }

  @Operation(summary = "Get all channels")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all channels",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channels retrieval failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<ChannelResponse>> retrieveAll() {
    return new ResponseEntity<>(channelService.getAll(), HttpStatus.OK);
  }
  
  @Operation(summary = "Get all channels PageWise")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all channels",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channels retrieval failed",
                  content = @Content) })
  @GetMapping("/allPageWise")
  public ResponseEntity<PageWiseSearchResponse<ChannelResponse>> retrieveAllPageWise(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
                                                                                       @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize) {

    return new ResponseEntity<>(channelService.getAllPaged(pageNumber,pageSize), HttpStatus.OK);
  }

  @Operation(summary = "Get all channels with logged in profile id status")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all channels",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channels retrieval failed",
                  content = @Content) })
  @GetMapping("loginProfileId/all/{profileId}")
  public ResponseEntity<List<ChannelResponse>> retrieveAll(@PathVariable String profileId) {
    return new ResponseEntity<>(channelService.getAll(profileId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve participants not part of channel by channel ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieved participants not part of channel successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MinimalProfileResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve participants not part of channel failed",
                  content = @Content) })
  @GetMapping("/newParticipants/{channelId}")
  public ResponseEntity<List<MinimalProfileResponse>> retrieveParticipantsNotOfChannel(@PathVariable Long channelId) {
    return new ResponseEntity<>(channelService.retrieveProfilesNotPartOfChannel(channelId), HttpStatus.OK);
  }
  @Operation(summary = "Get all channels with logged in profile id status")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got all channels",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channels retrieval failed",
                  content = @Content) })
  @GetMapping("/{channelId}")
  public ResponseEntity<ChannelResponse> retrieveById(@PathVariable Long channelId) {
    return new ResponseEntity<ChannelResponse>(channelService.retrieveById(channelId), HttpStatus.OK);
  }

  @Operation(summary = "Get all channels search")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully got searched channels",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "channels retrieval failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<ChannelResponse>> search(@RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(channelService.search(searchString), HttpStatus.OK);
  }
  @Operation(summary = "Retrieve  communities joined by profile ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve  communities joined by profile ID successfully",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChannelResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve  communities joined by profile ID failed",
                  content = @Content) })
  @GetMapping("/linkedProfiles/{profileId}")
  public ResponseEntity<List<ChannelResponse>> retrieveChannelsLinkedByProfileId(@PathVariable Long profileId) {
    return new ResponseEntity<>(channelService.retrieveChannelsLinked(profileId), HttpStatus.OK);
  }
}
