package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.response.AdminHomeResponse;
import com.vire.service.AdminPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = VireConstants.ADMIN_PORTAL_API)
public class AdminPortalController {

    @Autowired
    AdminPortalService adminPortalService;

    @GetMapping(value = "/homepage")
    public AdminHomeResponse getHomePage(){
        return adminPortalService.getHomePageDetails();
    }
}
