package com.vire.controller;

import com.vire.model.request.ProfileRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/createProfile")
    public ResponseEntity<ProfileResponse> createProfile(@RequestBody ProfileRequest request){
        return new ResponseEntity<>(profileService.createProfile(request), HttpStatus.CREATED);
    }

    @GetMapping("/getProfiles")
    public ResponseEntity<List<ProfileResponse>> getProfiles(){
        return new ResponseEntity<>(profileService.getProfiles(), HttpStatus.OK);
    }
}
