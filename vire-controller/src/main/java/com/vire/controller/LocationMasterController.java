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

  /*@GetMapping(value = {"/state/{state}"})
  public ResponseEntity<List<LocationMasterResponse>> retrieveDistrictsByState(@PathVariable String state) {
    return new ResponseEntity<>(locationMasterService.retrieveDistrictsByState(state), HttpStatus.OK);
  }*/

  @GetMapping(value = {"/cities/{state}/{district}"})
  public ResponseEntity<List<String>> retrieveCitiesByStateAndDist(@PathVariable String state, @PathVariable String district) {
    return new ResponseEntity<>(locationMasterService.retrieveCitiesByStateAndDist(state, district), HttpStatus.OK);
  }
  @GetMapping(value = {"/districts/{state}"})
  public ResponseEntity<List<String>> retrieveDistsByState(@PathVariable String state) {
    return new ResponseEntity<>(locationMasterService.retrieveDistsByState(state), HttpStatus.OK);
  }
  @GetMapping(value = {"/states/{country}"})
  public ResponseEntity<List<String>> retrieveStatesByCountry(@PathVariable String country) {
    return new ResponseEntity<>(locationMasterService.retrieveStatesByCountry(country), HttpStatus.OK);
  }
  @GetMapping("search")
  public ResponseEntity<List<LocationMasterResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(locationMasterService.search(searchString), HttpStatus.OK);
  }
}
