package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialChatRequest;
import com.vire.model.response.SocialChatResponse;
import com.vire.service.SocialChatService;
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
@RequestMapping(VireConstants.CHAT_REQUEST_PATH_API)
public class SocialChatController {

    @Autowired
    SocialChatService socialChatService;

    @Operation(summary = "Create Social chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Social chat Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialChatResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Create Social chat Failed",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<SocialChatResponse> create(@Valid @RequestBody SocialChatRequest request){
        return new ResponseEntity<>(socialChatService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Social chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Social chat Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialChatResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Update Social chat Failed",
                    content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<SocialChatResponse> update(@Valid @RequestBody SocialChatRequest request){
        return new ResponseEntity<>(socialChatService.update(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Social chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Social chat Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialChatResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Delete Social chat Failed",
                    content = @Content) })
    @DeleteMapping("/{chatid}")
    public ResponseEntity<SocialChatResponse> delete(
            @PathVariable(value = "chatid") Long chatId) {
        return new ResponseEntity<>(socialChatService.delete(chatId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve All Social chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve All Social chat Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialChatResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Retrieve All Social chat Failed",
                    content = @Content) })
    @GetMapping("/all")
    public ResponseEntity<List<SocialChatResponse>> retrieveAll(){
        return new ResponseEntity<>(socialChatService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Social chat by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve Social chat by ID Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialChatResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Retrieve Social chat by ID Failed",
                    content = @Content) })
    @GetMapping("/{chatid}")
    public ResponseEntity<SocialChatResponse> retrieveById(@PathVariable Long chatId){
        return new ResponseEntity<SocialChatResponse>(socialChatService.retrieveById(chatId), HttpStatus.OK);
    }

    @Operation(summary = "Search Social chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Social chat Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocialChatResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Search Social chat Failed",
                    content = @Content) })
    @GetMapping("search")
    public ResponseEntity<List<SocialChatResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(socialChatService.search(searchString), HttpStatus.OK);
    }
}
