package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceReportRequest;
import com.vire.model.response.ExperienceReportResponse;
import com.vire.service.ExperienceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.EXPERIENCEREPORT_REQUEST_PATH_API)
public class ExperienceReportController {

  @Autowired
  ExperienceReportService experienceReportService;

  @PostMapping("/create")
  public ResponseEntity<ExperienceReportResponse> create(@RequestBody ExperienceReportRequest request) {
    return new ResponseEntity<>(experienceReportService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ExperienceReportResponse> update(@RequestBody ExperienceReportRequest request) {
    return new ResponseEntity<>(experienceReportService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{experienceReportId}")
  public ResponseEntity<ExperienceReportResponse> delete(
          @PathVariable(value = "experienceReportId") Long experienceReportId) {
    return new ResponseEntity<>(experienceReportService.delete(experienceReportId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ExperienceReportResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceReportService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{experienceReportId}")
  public ResponseEntity<ExperienceReportResponse> retrieveById(@PathVariable Long experienceReportId) {
    return new ResponseEntity<ExperienceReportResponse>(experienceReportService.retrieveById(experienceReportId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ExperienceReportResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceReportService.search(searchString), HttpStatus.OK);
  }
}
