package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceRequest;
import com.vire.model.response.ExperienceResponse;
import com.vire.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.EXPERIENCE_REQUEST_PATH_API)
public class ExperienceController {

  @Autowired
  ExperienceService experienceService;

  @PostMapping("/create")
  public ResponseEntity<ExperienceResponse> create(@Valid @RequestBody ExperienceRequest request) {
    return new ResponseEntity<>(experienceService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ExperienceResponse> update(@Valid @RequestBody ExperienceRequest request) {
    return new ResponseEntity<>(experienceService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{experienceId}")
  public ResponseEntity<ExperienceResponse> delete(
          @PathVariable(value = "id") Long experienceId) {
    return new ResponseEntity<>(experienceService.delete(experienceId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ExperienceResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{experienceId}")
  public ResponseEntity<ExperienceResponse> retrieveById(@PathVariable Long experienceId) {
    return new ResponseEntity<ExperienceResponse>(experienceService.retrieveById(experienceId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ExperienceResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceService.search(searchString), HttpStatus.OK);
  }
}
