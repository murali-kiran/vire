package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedReportRequest;
import com.vire.model.response.FeedReportResponse;
import com.vire.service.FeedReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.FEEDREPORT_REQUEST_PATH_API)
public class FeedReportController {

  @Autowired
  FeedReportService feedReportService;

  @PostMapping("/create")
  public ResponseEntity<FeedReportResponse> create(@RequestBody FeedReportRequest request) {
    return new ResponseEntity<>(feedReportService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<FeedReportResponse> update(@RequestBody FeedReportRequest request) {
    return new ResponseEntity<>(feedReportService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{feedReportId}")
  public ResponseEntity<FeedReportResponse> delete(
          @PathVariable(value = "feedReportId") Long feedReportId) {
    return new ResponseEntity<>(feedReportService.delete(feedReportId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FeedReportResponse>> retrieveAll() {
    return new ResponseEntity<>(feedReportService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{feedReportId}")
  public ResponseEntity<FeedReportResponse> retrieveById(@PathVariable Long feedReportId) {
    return new ResponseEntity<FeedReportResponse>(feedReportService.retrieveById(feedReportId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<FeedReportResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedReportService.search(searchString), HttpStatus.OK);
  }
}
