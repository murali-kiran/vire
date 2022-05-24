package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialReportRequest;
import com.vire.model.response.SocialReportResponse;
import com.vire.service.SocialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.SOCIALREPORT_REQUEST_PATH_API)
public class SocialReportController {

  @Autowired
  SocialReportService socialReportService;

  @PostMapping("/create")
  public ResponseEntity<SocialReportResponse> create(@RequestBody SocialReportRequest request) {
    return new ResponseEntity<>(socialReportService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<SocialReportResponse> update(@RequestBody SocialReportRequest request) {
    return new ResponseEntity<>(socialReportService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{socialReportId}")
  public ResponseEntity<SocialReportResponse> delete(
          @PathVariable(value = "socialReportId") Long socialReportId) {
    return new ResponseEntity<>(socialReportService.delete(socialReportId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<SocialReportResponse>> retrieveAll() {
    return new ResponseEntity<>(socialReportService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{socialReportId}")
  public ResponseEntity<SocialReportResponse> retrieveById(@PathVariable Long socialReportId) {
    return new ResponseEntity<SocialReportResponse>(socialReportService.retrieveById(socialReportId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<SocialReportResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(socialReportService.search(searchString), HttpStatus.OK);
  }
}
