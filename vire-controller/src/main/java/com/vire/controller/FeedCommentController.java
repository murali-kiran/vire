package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedCommentRequest;
import com.vire.model.response.FeedCommentResponse;
import com.vire.service.FeedCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.FEEDCOMMENT_REQUEST_PATH_API)
public class FeedCommentController {

  @Autowired
  FeedCommentService feedCommentService;

  @PostMapping("/create")
  public ResponseEntity<FeedCommentResponse> create(@RequestBody FeedCommentRequest request) {
    return new ResponseEntity<>(feedCommentService.create(request), HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<FeedCommentResponse> update(@RequestBody FeedCommentRequest request) {
    return new ResponseEntity<>(feedCommentService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{feedCommentId}")
  public ResponseEntity<FeedCommentResponse> delete(
          @PathVariable(value = "id") Long feedCommentId) {
    return new ResponseEntity<>(feedCommentService.delete(feedCommentId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FeedCommentResponse>> retrieveAll() {
    return new ResponseEntity<>(feedCommentService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{feedCommentId}")
  public ResponseEntity<FeedCommentResponse> retrieveById(@PathVariable Long feedCommentId) {
    return new ResponseEntity<FeedCommentResponse>(feedCommentService.retrieveById(feedCommentId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<FeedCommentResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedCommentService.search(searchString), HttpStatus.OK);
  }
}
