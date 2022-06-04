package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.LocationMasterRequest;
import com.vire.model.response.LocationMasterResponse;
import com.vire.service.LocationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.LOCATIONMASTER_REQUEST_PATH_API)
public class LocationMasterController {

  @Autowired
  LocationMasterService locationMasterService;

  @PostMapping("/create")
  public ResponseEntity<LocationMasterResponse> create(@RequestBody LocationMasterRequest request) {
    return new ResponseEntity<>(locationMasterService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<LocationMasterResponse> update(@RequestBody LocationMasterRequest request) {
    return new ResponseEntity<>(locationMasterService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{locationMasterId}")
  public ResponseEntity<LocationMasterResponse> delete(
          @PathVariable(value = "locationMasterId") Long locationMasterId) {
    return new ResponseEntity<>(locationMasterService.delete(locationMasterId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<LocationMasterResponse>> retrieveAll() {
    return new ResponseEntity<>(locationMasterService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{locationMasterId}")
  public ResponseEntity<LocationMasterResponse> retrieveById(@PathVariable Long locationMasterId) {
    return new ResponseEntity<LocationMasterResponse>(locationMasterService.retrieveById(locationMasterId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<LocationMasterResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(locationMasterService.search(searchString), HttpStatus.OK);
  }
}
