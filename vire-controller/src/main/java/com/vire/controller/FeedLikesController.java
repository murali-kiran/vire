package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.FeedLikesRequest;
import com.vire.model.response.FeedLikesResponse;
import com.vire.service.FeedLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.FEEDLIKES_REQUEST_PATH_API)
public class FeedLikesController {

  @Autowired
  FeedLikesService feedLikesService;

  @PostMapping("/create")
  public ResponseEntity<FeedLikesResponse> create(@Valid @RequestBody FeedLikesRequest request) {
    return new ResponseEntity<>(feedLikesService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<FeedLikesResponse> update(@Valid @RequestBody FeedLikesRequest request) {
    return new ResponseEntity<>(feedLikesService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{feedLikesId}")
  public ResponseEntity<FeedLikesResponse> delete(
          @PathVariable(value = "id") Long feedLikesId) {
    return new ResponseEntity<>(feedLikesService.delete(feedLikesId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FeedLikesResponse>> retrieveAll() {
    return new ResponseEntity<>(feedLikesService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{feedLikesId}")
  public ResponseEntity<FeedLikesResponse> retrieveById(@PathVariable Long feedLikesId) {
    return new ResponseEntity<FeedLikesResponse>(feedLikesService.retrieveById(feedLikesId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<FeedLikesResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(feedLikesService.search(searchString), HttpStatus.OK);
  }
}
