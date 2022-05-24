package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ChannelRequest;
import com.vire.model.response.ChannelResponse;
import com.vire.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.CHANNEL_REQUEST_PATH_API)
public class ChannelController {

  @Autowired
  ChannelService channelService;

  @PostMapping("/create")
  public ResponseEntity<ChannelResponse> create(@RequestBody ChannelRequest request) {
    return new ResponseEntity<>(channelService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ChannelResponse> update(@RequestBody ChannelRequest request) {
    return new ResponseEntity<>(channelService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{channelId}")
  public ResponseEntity<ChannelResponse> delete(
          @PathVariable(value = "channelId") Long channelId) {
    return new ResponseEntity<>(channelService.delete(channelId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ChannelResponse>> retrieveAll() {
    return new ResponseEntity<>(channelService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{channelId}")
  public ResponseEntity<ChannelResponse> retrieveById(@PathVariable Long channelId) {
    return new ResponseEntity<ChannelResponse>(channelService.retrieveById(channelId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ChannelResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(channelService.search(searchString), HttpStatus.OK);
  }
}
