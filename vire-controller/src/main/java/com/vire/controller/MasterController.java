package com.vire.controller;

import com.vire.model.request.MasterRequest;
import com.vire.model.response.MasterResponse;
import com.vire.service.MasterService;
import com.vire.constant.VireConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.MASTER_REQUEST_PATH_API)
public class MasterController {

  @Autowired
  MasterService masterService;

  @Operation(summary = "Create Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Create Master Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Create Master Failed",
                  content = @Content) })
  @PostMapping("/create")
  public ResponseEntity<MasterResponse> create(@Valid @RequestBody MasterRequest request) {
    return new ResponseEntity<>(masterService.create(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Update Master Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Update Master Failed",
                  content = @Content) })
  @PutMapping("/update")
  public ResponseEntity<MasterResponse> update(@Valid @RequestBody MasterRequest request) {
    return new ResponseEntity<>(masterService.update(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Delete Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Delete Master Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Delete Master Failed",
                  content = @Content) })
  @DeleteMapping("/{masterId}")
  public ResponseEntity<MasterResponse> delete(
          @PathVariable(value = "masterId") Long masterId) {
    return new ResponseEntity<>(masterService.delete(masterId), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve All Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve All Master Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve All Master Failed",
                  content = @Content) })
  @GetMapping("/all")
  public ResponseEntity<List<MasterResponse>> retrieveAll() {
    return new ResponseEntity<>(masterService.getAll(), HttpStatus.OK);
  }

  @Operation(summary = "Retrieve Master by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retrieve Master by ID Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Retrieve Master by ID Failed",
                  content = @Content) })
  @GetMapping("/{masterId}")
  public ResponseEntity<MasterResponse> retrieveById(@PathVariable Long masterId) {
    return new ResponseEntity<MasterResponse>(masterService.retrieveById(masterId), HttpStatus.OK);
  }

  @Operation(summary = "Search Master ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Search Master Successful",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MasterResponse.class)) }),
          @ApiResponse(responseCode = "500", description = "Search Master Failed",
                  content = @Content) })
  @GetMapping("search")
  public ResponseEntity<List<MasterResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(masterService.search(searchString), HttpStatus.OK);
  }
}
