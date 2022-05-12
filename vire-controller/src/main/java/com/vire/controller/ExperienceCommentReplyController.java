package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceCommentReplyRequest;
import com.vire.model.response.ExperienceCommentReplyResponse;
import com.vire.service.ExperienceCommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.EXPERIENCECOMMENTREPLY_REQUEST_PATH_API)
public class ExperienceCommentReplyController {

  @Autowired
  ExperienceCommentReplyService experienceCommentReplyService;

  @PostMapping("/create")
  public ResponseEntity<ExperienceCommentReplyResponse> create(@Valid @RequestBody ExperienceCommentReplyRequest request) {
    return new ResponseEntity<>(experienceCommentReplyService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ExperienceCommentReplyResponse> update(@Valid @RequestBody ExperienceCommentReplyRequest request) {
    return new ResponseEntity<>(experienceCommentReplyService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{experienceCommentReplyId}")
  public ResponseEntity<ExperienceCommentReplyResponse> delete(
          @PathVariable(value = "id") Long experienceCommentReplyId) {
    return new ResponseEntity<>(experienceCommentReplyService.delete(experienceCommentReplyId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ExperienceCommentReplyResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceCommentReplyService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{experienceCommentReplyId}")
  public ResponseEntity<ExperienceCommentReplyResponse> retrieveById(@PathVariable Long experienceCommentReplyId) {
    return new ResponseEntity<ExperienceCommentReplyResponse>(experienceCommentReplyService.retrieveById(experienceCommentReplyId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ExperienceCommentReplyResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceCommentReplyService.search(searchString), HttpStatus.OK);
  }
}
