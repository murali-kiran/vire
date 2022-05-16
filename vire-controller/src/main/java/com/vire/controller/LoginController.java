package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.LoginRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Login user by phone number or email id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials",
                    content = @Content) })
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