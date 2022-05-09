package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceCommentRequest;
import com.vire.model.response.ExperienceCommentResponse;
import com.vire.service.ExperienceCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.EXPERIENCECOMMENT_REQUEST_PATH_API)
public class ExperienceCommentController {

  @Autowired
  ExperienceCommentService experienceCommentService;

  @PostMapping("/create")
  public ResponseEntity<ExperienceCommentResponse> create(@Valid @RequestBody ExperienceCommentRequest request) {
    return new ResponseEntity<>(experienceCommentService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ExperienceCommentResponse> update(@Valid @RequestBody ExperienceCommentRequest request) {
    return new ResponseEntity<>(experienceCommentService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{experienceCommentId}")
  public ResponseEntity<ExperienceCommentResponse> delete(
          @PathVariable(value = "id") Long experienceCommentId) {
    return new ResponseEntity<>(experienceCommentService.delete(experienceCommentId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ExperienceCommentResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceCommentService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{experienceCommentId}")
  public ResponseEntity<ExperienceCommentResponse> retrieveById(@PathVariable Long experienceCommentId) {
    return new ResponseEntity<ExperienceCommentResponse>(experienceCommentService.retrieveById(experienceCommentId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ExperienceCommentResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceCommentService.search(searchString), HttpStatus.OK);
  }
}
