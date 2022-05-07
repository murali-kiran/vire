package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.LoginRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(VireConstants.LOGIN_REQUEST_API)
public class LoginController {

    @Autowired
    LoginService loginService;

/*    @PostMapping
    public ResponseEntity<ProfileResponse> login(@RequestBody LoginRequest loginRequest){
        var response = loginService.login(loginRequest);
        return response.isPresent() ? new ResponseEntity<>(response.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

 */

    @PostMapping
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        var response = loginService.login(loginRequest);
        return response
                .stream()
                .map(profileResponse -> new ResponseEntity<>(profileResponse, HttpStatus.OK))
                .findFirst()
                .orElse(new ResponseEntity(HttpStatus.UNAUTHORIZED));
        //return response;
    }
}