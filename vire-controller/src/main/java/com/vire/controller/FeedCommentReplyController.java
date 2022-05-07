package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedCommentReplyRequest;
import com.vire.model.response.FeedCommentReplyResponse;
import com.vire.service.FeedCommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.FEEDCOMMENTREPLY_REQUEST_PATH_API)
public class FeedCommentReplyController {

  @Autowired
  FeedCommentReplyService feedCommentReplyService;

  @PostMapping("/create")
  public ResponseEntity<FeedCommentReplyResponse> create(@Valid @RequestBody FeedCommentReplyRequest request) {
    return new ResponseEntity<>(feedCommentReplyService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<FeedCommentReplyResponse> update(@Valid @RequestBody FeedCommentReplyRequest request) {
    return new ResponseEntity<>(feedCommentReplyService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{feedCommentReplyId}")
  public ResponseEntity<FeedCommentReplyResponse> delete(
          @PathVariable(value = "id") Long feedCommentReplyId) {
    return new ResponseEntity<>(feedCommentReplyService.delete(feedCommentReplyId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FeedCommentReplyResponse>> retrieveAll() {
    return new ResponseEntity<>(feedCommentReplyService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{feedCommentReplyId}")
  public ResponseEntity<FeedCommentReplyResponse> retrieveById(@PathVariable Long feedCommentReplyId) {
    return new ResponseEntity<FeedCommentReplyResponse>(feedCommentReplyService.retrieveById(feedCommentReplyId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<FeedCommentReplyResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedCommentReplyService.search(searchString), HttpStatus.OK);
  }
}
