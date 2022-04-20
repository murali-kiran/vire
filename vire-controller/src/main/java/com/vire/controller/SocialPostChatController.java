package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialChatRequest;
import com.vire.model.response.SocialChatResponse;
import com.vire.service.SocialChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.CHAT_REQUEST_PATH_API)
public class SocialPostChatController {

    @Autowired
    SocialChatService socialChatService;

    @PostMapping("/create")
    public ResponseEntity<SocialChatResponse> create(@RequestBody SocialChatRequest request){
        return new ResponseEntity<>(socialChatService.create(request), HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<SocialChatResponse> update(@RequestBody SocialChatRequest request){
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
