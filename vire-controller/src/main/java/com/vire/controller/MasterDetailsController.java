package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.MasterDetailsRequest;
import com.vire.model.response.MasterDetailsResponse;
import com.vire.service.MasterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.MASTERDETAILS_REQUEST_PATH_API)
public class MasterDetailsController {

  @Autowired
  MasterDetailsService masterDetailsService;

  @PostMapping("/create")
  public ResponseEntity<MasterDetailsResponse> create(@RequestBody MasterDetailsRequest request) {
    return new ResponseEntity<>(masterDetailsService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<MasterDetailsResponse> update(@RequestBody MasterDetailsRequest request) {
    return new ResponseEntity<>(masterDetailsService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{masterDetailsId}")
  public ResponseEntity<MasterDetailsResponse> delete(
          @PathVariable(value = "masterDetailsId") Long masterDetailsId) {
    return new ResponseEntity<>(masterDetailsService.delete(masterDetailsId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<MasterDetailsResponse>> retrieveAll() {
    return new ResponseEntity<>(masterDetailsService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{masterDetailsId}")
  public ResponseEntity<MasterDetailsResponse> retrieveById(@PathVariable Long masterDetailsId) {
    return new ResponseEntity<MasterDetailsResponse>(masterDetailsService.retrieveById(masterDetailsId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<MasterDetailsResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(masterDetailsService.search(searchString), HttpStatus.OK);
  }
}
