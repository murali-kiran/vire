package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ProfileReportRequest;
import com.vire.model.response.ProfileReportResponse;
import com.vire.service.ProfileReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.PROFILEREPORT_REQUEST_PATH_API)
public class ProfileReportController {

  @Autowired
  ProfileReportService profileReportService;

  @PostMapping("/create")
  public ResponseEntity<ProfileReportResponse> create(@RequestBody ProfileReportRequest request) {
    return new ResponseEntity<>(profileReportService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileReportResponse> update(@RequestBody ProfileReportRequest request) {
    return new ResponseEntity<>(profileReportService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{profileReportId}")
  public ResponseEntity<ProfileReportResponse> delete(
          @PathVariable(value = "profileReportId") Long profileReportId) {
    return new ResponseEntity<>(profileReportService.delete(profileReportId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProfileReportResponse>> retrieveAll() {
    return new ResponseEntity<>(profileReportService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{profileReportId}")
  public ResponseEntity<ProfileReportResponse> retrieveById(@PathVariable Long profileReportId) {
    return new ResponseEntity<ProfileReportResponse>(profileReportService.retrieveById(profileReportId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ProfileReportResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(profileReportService.search(searchString), HttpStatus.OK);
  }
}
