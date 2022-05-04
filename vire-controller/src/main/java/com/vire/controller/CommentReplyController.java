package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.CommentReplyRequest;
import com.vire.model.response.CommentReplyResponse;
import com.vire.service.CommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(VireConstants.REPLY_REQUEST_PATH_API)
public class CommentReplyController {

    @Autowired
    CommentReplyService commentReplyService;

    @PostMapping("/create")
    public ResponseEntity<CommentReplyResponse> createReply(@Valid @RequestBody CommentReplyRequest request){
        return new ResponseEntity<>(commentReplyService.createReply(request), HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<CommentReplyResponse> updateReply(@Valid @RequestBody CommentReplyRequest request){
        return new ResponseEntity<>(commentReplyService.updateReply(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{replyid}")
    public ResponseEntity<CommentReplyResponse> deleteReply(
            @PathVariable(value = "replyid") Long replyId) {
        return new ResponseEntity<>(commentReplyService.deleteReply(replyId), HttpStatus.OK);
    }

    @GetMapping("/{replyid}")
    public ResponseEntity<CommentReplyResponse> retrieveById(@PathVariable(name="replyid") Long replyId){
        return new ResponseEntity<CommentReplyResponse>(commentReplyService.retrieveById(replyId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<CommentReplyResponse>> searchReplies(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(commentReplyService.searchReplies(searchString), HttpStatus.OK);
    }
}
