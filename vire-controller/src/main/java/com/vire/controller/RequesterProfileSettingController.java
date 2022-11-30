package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.RequesterProfileSettingRequest;
import com.vire.model.response.RequesterProfileSettingResponse;
import com.vire.service.RequesterProfileSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.REQUESTERPROFILESETTING_REQUEST_PATH_API)
public class RequesterProfileSettingController {

  @Autowired
  RequesterProfileSettingService requesterProfileSettingService;

  @PostMapping("/create")
  public ResponseEntity<RequesterProfileSettingResponse> create(@RequestBody RequesterProfileSettingRequest request) {
    return new ResponseEntity<>(requesterProfileSettingService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<RequesterProfileSettingResponse> update(@RequestBody RequesterProfileSettingRequest request) {
    return new ResponseEntity<>(requesterProfileSettingService.update(request), HttpStatus.CREATED);
  }

  @PutMapping("/updateStatus")
  public ResponseEntity<RequesterProfileSettingResponse> updateStatus(@RequestBody RequesterProfileSettingRequest request) {
    return new ResponseEntity<>(requesterProfileSettingService.updateEnableStatus(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{requesterProfileSettingId}")
  public ResponseEntity<RequesterProfileSettingResponse> delete(
          @PathVariable(value = "requesterProfileSettingId") Long requesterProfileSettingId) {
    return new ResponseEntity<>(requesterProfileSettingService.delete(requesterProfileSettingId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<RequesterProfileSettingResponse>> retrieveAll() {
    return new ResponseEntity<>(requesterProfileSettingService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{requesterProfileSettingId}")
  public ResponseEntity<RequesterProfileSettingResponse> retrieveById(@PathVariable Long requesterProfileSettingId) {
    return new ResponseEntity<RequesterProfileSettingResponse>(requesterProfileSettingService.retrieveById(requesterProfileSettingId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<RequesterProfileSettingResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(requesterProfileSettingService.search(searchString), HttpStatus.OK);
  }
}
