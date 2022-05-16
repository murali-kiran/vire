package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.CommentReplyRequest;
import com.vire.model.response.CommentReplyResponse;
import com.vire.service.CommentReplyService;
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
@RequestMapping(VireConstants.REPLY_REQUEST_PATH_API)
public class CommentReplyController {

    @Autowired
    CommentReplyService commentReplyService;

    @Operation(summary = "Create Comment Reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "comment reply created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentReplyResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment reply creation failed",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<CommentReplyResponse> createReply(@Valid @RequestBody CommentReplyRequest request){
        return new ResponseEntity<>(commentReplyService.createReply(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update comment reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "updated comment reply successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentReplyResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment reply updation failed",
                    content = @Content) })
    @PostMapping("/update")
    public ResponseEntity<CommentReplyResponse> updateReply(@Valid @RequestBody CommentReplyRequest request){
        return new ResponseEntity<>(commentReplyService.updateReply(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Deletion of comment reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletion of comment reply successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentReplyResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment reply deletion failed",
                    content = @Content) })
    @DeleteMapping("/{replyid}")
    public ResponseEntity<CommentReplyResponse> deleteReply(
            @PathVariable(value = "replyid") Long replyId) {
        return new ResponseEntity<>(commentReplyService.deleteReply(replyId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve comment reply by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved comment reply by ID sucessfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentReplyResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "comment reply retrieval failed",
                    content = @Content) })
    @GetMapping("/{replyid}")
    public ResponseEntity<CommentReplyResponse> retrieveById(@PathVariable(name="replyid") Long replyId){
        return new ResponseEntity<CommentReplyResponse>(commentReplyService.retrieveById(replyId), HttpStatus.OK);
    }

    @Operation(summary = "Searching of comment reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "searching comments reply successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentReplyResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "searching of comment replies failed",
                    content = @Content) })
    @GetMapping("search")
    public ResponseEntity<List<CommentReplyResponse>> searchReplies(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(commentReplyService.searchReplies(searchString), HttpStatus.OK);
    }
}
