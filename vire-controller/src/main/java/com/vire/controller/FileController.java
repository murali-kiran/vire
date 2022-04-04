package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.SocialPostChatRequest;
import com.vire.model.response.SocialPostChatResponse;
import com.vire.service.SocialPostChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.CHAT_REQUEST_PATH_API)
public class SocialPostChatController {

    @Autowired
    SocialPostChatService socialPostChatService;

    @PostMapping("/create")
    public ResponseEntity<SocialPostChatResponse> create(@RequestBody SocialPostChatRequest request){
        return new ResponseEntity<>(socialPostChatService.create(request), HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<SocialPostChatResponse> update(@RequestBody SocialPostChatRequest request){
        return new ResponseEntity<>(socialPostChatService.update(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{chatid}")
    public ResponseEntity<SocialPostChatResponse> delete(
            @PathVariable(value = "chatid") Long chatId) {
        return new ResponseEntity<>(socialPostChatService.delete(chatId), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SocialPostChatResponse>> retrieveAll(){
        return new ResponseEntity<>(socialPostChatService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{chatid}")
    public ResponseEntity<SocialPostChatResponse> retrieveById(@PathVariable Long chatId){
        return new ResponseEntity<SocialPostChatResponse>(socialPostChatService.retrieveById(chatId), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<SocialPostChatResponse>> search(
            @RequestParam(value = "search") String searchString) {
        return new ResponseEntity<>(socialPostChatService.search(searchString), HttpStatus.OK);
    }
}
