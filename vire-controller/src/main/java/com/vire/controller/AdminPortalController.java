package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.response.AdminHomeResponse;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.model.response.ProfileResponse;
import com.vire.service.AdminPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = VireConstants.ADMIN_PORTAL_API)
@CrossOrigin
public class AdminPortalController {

  @Autowired AdminPortalService adminPortalService;

  @GetMapping(value = "/homepage")
  public AdminHomeResponse getHomePage() {
    return adminPortalService.getHomePageDetails();
  }

  @GetMapping(value = "/users")
  public PageWiseSearchResponse<ProfileResponse> getUsers(
          @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNumber,
          @RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize
  ) {
    return adminPortalService.getAllUsers(pageNumber, pageSize);
  }

  @GetMapping(value = "/deleteProfile/{profileId}")
  public ResponseEntity<ProfileResponse> deleteProfile(@PathVariable("profileId") String profileId) {
    var minimalProfile = adminPortalService.deleteProfile(Long.valueOf(profileId));
    return minimalProfile.isPresent() ? new ResponseEntity<>(minimalProfile.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "/blockProfile/{profileId}")
  public ResponseEntity<Boolean>  blockProfile(@PathVariable("profileId") String profileId,@RequestParam("isBlocked") Boolean isBlocked) {
     var blockedStatus = adminPortalService.blockProfile(Long.valueOf(profileId),isBlocked);
     return new ResponseEntity<>(blockedStatus, HttpStatus.OK);
  }

}
