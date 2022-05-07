package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialSendToRequest;
import com.vire.model.response.SocialSendToResponse;
import com.vire.service.SocialSendToService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Validated
@RestController
@RequestMapping(VireConstants.SENT_REQUEST_PATH_API)
public class SocialSendToController {

    @Autowired
    SocialSendToService socialSendToService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SocialSendToResponse> createSent(@Valid @RequestBody SocialSendToRequest request){

        return new ResponseEntity<>(socialSendToService.create(request), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<SocialSendToResponse> updateSent(@Valid @RequestBody SocialSendToRequest request){
        return new ResponseEntity<>(socialSendToService.update(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{sendtoid}")
    public ResponseEntity<SocialSendToResponse> deleteSent(
            @PathVariable(value = "sendtoid") Long sentId) {
        return new ResponseEntity<>(socialSendToService.deletePostSent(sentId), HttpStatus.OK);
    }

    @GetMapping("/{sendtoid}")
    public ResponseEntity<SocialSendToResponse> retrieveById(@PathVariable(name="sendtoid") Long postSentId){
        return new ResponseEntity<SocialSendToResponse>(socialSendToService.retrieveById(postSentId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<SocialSendToResponse>> searchSent(
            @RequestParam(value = "searchpostsent") String searchString) {
        return new ResponseEntity<>(socialSendToService.searchSent(searchString), HttpStatus.OK);
    }
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$ERROR MSG:"+errors);
        return ResponseEntity.badRequest().body(errors);
    }*/
}
