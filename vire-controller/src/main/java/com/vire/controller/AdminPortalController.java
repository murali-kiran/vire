package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.response.AdminHomeResponse;
import com.vire.model.response.MinimalProfileResponse;
import com.vire.service.AdminPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public List<MinimalProfileResponse> getUsers() {
    return adminPortalService.getAllUsers();
  }


}
