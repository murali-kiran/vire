package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedbackRequest;
import com.vire.model.response.FeedbackResponse;
import com.vire.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.FEEDBACK_REQUEST_PATH_API)
public class FeedbackController {

  @Autowired
  FeedbackService feedbackService;

  @PostMapping("/create")
  public ResponseEntity<FeedbackResponse> create(@RequestBody FeedbackRequest request) {
    return new ResponseEntity<>(feedbackService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<FeedbackResponse> update(@RequestBody FeedbackRequest request) {
    return new ResponseEntity<>(feedbackService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{feedbackId}")
  public ResponseEntity<FeedbackResponse> delete(
          @PathVariable(value = "feedbackId") Long feedbackId) {
    return new ResponseEntity<>(feedbackService.delete(feedbackId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FeedbackResponse>> retrieveAll() {
    return new ResponseEntity<>(feedbackService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{feedbackId}")
  public ResponseEntity<FeedbackResponse> retrieveById(@PathVariable Long feedbackId) {
    return new ResponseEntity<FeedbackResponse>(feedbackService.retrieveById(feedbackId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<FeedbackResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedbackService.search(searchString), HttpStatus.OK);
  }
}
