package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.CommentRequest;
import com.vire.model.response.CommentResponse;
import com.vire.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.COMMENT_REQUEST_PATH_API)
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentRequest request){
        return new ResponseEntity<>(commentService.createComment(request), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<CommentResponse> updateComment(@Valid @RequestBody CommentRequest request){
        return new ResponseEntity<>(commentService.updateComment(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentid}")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable(value = "commentid") Long commentId) {
        return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.OK);
    }

    @GetMapping("/{commentid}")
    public ResponseEntity<CommentResponse> retrieveById(@PathVariable(name = "commentid") Long commentId){
        return new ResponseEntity<CommentResponse>(commentService.retrieveById(commentId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<CommentResponse>> searchComment(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(commentService.searchComments(searchString), HttpStatus.OK);
    }
}
