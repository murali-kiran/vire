package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.ExperienceLikesRequest;
import com.vire.model.response.ExperienceLikesResponse;
import com.vire.service.ExperienceLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.EXPERIENCELIKES_REQUEST_PATH_API)
public class ExperienceLikesController {

  @Autowired
  ExperienceLikesService experienceLikesService;

  @PostMapping("/create")
  public ResponseEntity<ExperienceLikesResponse> create(@Valid @RequestBody ExperienceLikesRequest request) {
    return new ResponseEntity<>(experienceLikesService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ExperienceLikesResponse> update(@Valid @RequestBody ExperienceLikesRequest request) {
    return new ResponseEntity<>(experienceLikesService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{experienceLikesId}")
  public ResponseEntity<ExperienceLikesResponse> delete(
          @PathVariable(value = "id") Long experienceLikesId) {
    return new ResponseEntity<>(experienceLikesService.delete(experienceLikesId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ExperienceLikesResponse>> retrieveAll() {
    return new ResponseEntity<>(experienceLikesService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{experienceLikesId}")
  public ResponseEntity<ExperienceLikesResponse> retrieveById(@PathVariable Long experienceLikesId) {
    return new ResponseEntity<ExperienceLikesResponse>(experienceLikesService.retrieveById(experienceLikesId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<ExperienceLikesResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(experienceLikesService.search(searchString), HttpStatus.OK);
  }
}
