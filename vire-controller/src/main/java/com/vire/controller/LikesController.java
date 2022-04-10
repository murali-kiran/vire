package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.LikesRequest;
import com.vire.model.response.LikesResponse;
import com.vire.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.LIKE_REQUEST_PATH_API)
public class LikesController {

    @Autowired
    LikesService likesService;

    @PostMapping("/createLike")
    public ResponseEntity<LikesResponse> createLike(@RequestBody LikesRequest request){
        return new ResponseEntity<>(likesService.createLike(request), HttpStatus.CREATED);
    }
    @PostMapping("/updateLike")
    public ResponseEntity<LikesResponse> updateLike(@RequestBody LikesRequest request){
        return new ResponseEntity<>(likesService.updateLike(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{likeid}")
    public ResponseEntity<LikesResponse> deleteLike(
            @PathVariable(value = "likeid") Long likeid) {
        return new ResponseEntity<>(likesService.deleteLike(likeid), HttpStatus.OK);
    }

    @GetMapping("/{chatid}")
    public ResponseEntity<LikesResponse> retrieveById(@PathVariable Long likeId){
        return new ResponseEntity<LikesResponse>(likesService.retrieveById(likeId), HttpStatus.OK);
    }
    @GetMapping("searchLikes")
    public ResponseEntity<List<LikesResponse>> searchLikes(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(likesService.searchLikes(searchString), HttpStatus.OK);
    }
}
