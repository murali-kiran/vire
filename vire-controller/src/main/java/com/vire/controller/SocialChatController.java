package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialChatRequest;
import com.vire.model.response.SocialChatResponse;
import com.vire.service.SocialChatService;
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

    @PostMapping("/create")
    public ResponseEntity<SocialChatResponse> create(@Valid @RequestBody SocialChatRequest request){
        return new ResponseEntity<>(socialChatService.create(request), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<SocialChatResponse> update(@Valid @RequestBody SocialChatRequest request){
        return new ResponseEntity<>(socialChatService.update(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{chatid}")
    public ResponseEntity<SocialChatResponse> delete(
            @PathVariable(value = "chatid") Long chatId) {
        return new ResponseEntity<>(socialChatService.delete(chatId), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SocialChatResponse>> retrieveAll(){
        return new ResponseEntity<>(socialChatService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{chatid}")
    public ResponseEntity<SocialChatResponse> retrieveById(@PathVariable Long chatId){
        return new ResponseEntity<SocialChatResponse>(socialChatService.retrieveById(chatId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<SocialChatResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(socialChatService.search(searchString), HttpStatus.OK);
    }
}
