package com.vire.controller;

import com.vire.model.request.MasterRequest;
import com.vire.model.response.MasterResponse;
import com.vire.service.MasterService;
import com.vire.constant.VireConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.MASTER_REQUEST_PATH_API)
public class MasterController {

  @Autowired
  MasterService masterService;

  @PostMapping("/create")
  public ResponseEntity<MasterResponse> create(@RequestBody MasterRequest request) {
    return new ResponseEntity<>(masterService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<MasterResponse> update(@RequestBody MasterRequest request) {
    return new ResponseEntity<>(masterService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{masterId}")
  public ResponseEntity<MasterResponse> delete(
          @PathVariable(value = "masterId") Long masterId) {
    return new ResponseEntity<>(masterService.delete(masterId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<MasterResponse>> retrieveAll() {
    return new ResponseEntity<>(masterService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{masterId}")
  public ResponseEntity<MasterResponse> retrieveById(@PathVariable Long masterId) {
    return new ResponseEntity<MasterResponse>(masterService.retrieveById(masterId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<MasterResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(masterService.search(searchString), HttpStatus.OK);
  }
}
