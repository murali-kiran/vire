package com.vire.controller;

import com.vire.constant.VireConstants;
import com.vire.model.request.NotificationRequest;
import com.vire.model.response.NotificationCountResponse;
import com.vire.model.response.NotificationResponse;
import com.vire.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VireConstants.NOTIFICATION_REQUEST_PATH_API)
public class NotificationController {

  @Autowired
  NotificationService notificationService;

  @PostMapping("/create")
  public ResponseEntity<NotificationResponse> create(@RequestBody NotificationRequest request) {
    return new ResponseEntity<>(notificationService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<NotificationResponse> update(@RequestBody NotificationRequest request) {
    return new ResponseEntity<>(notificationService.update(request), HttpStatus.CREATED);
  }

  @DeleteMapping("/{notificationId}")
  public ResponseEntity<NotificationResponse> delete(
          @PathVariable(value = "notificationId") Long notificationId) {
    return new ResponseEntity<>(notificationService.delete(notificationId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<NotificationResponse>> retrieveAll() {
    return new ResponseEntity<>(notificationService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{notificationId}")
  public ResponseEntity<NotificationResponse> retrieveById(@PathVariable Long notificationId) {
    return new ResponseEntity<NotificationResponse>(notificationService.retrieveById(notificationId), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<List<NotificationResponse>> search(
          @RequestParam(value = "search") String searchString) {
    return new ResponseEntity<>(notificationService.search(searchString), HttpStatus.OK);
  }

  @GetMapping("/getcount/{profileId}")
  public ResponseEntity<NotificationCountResponse> retrieveCountByProfileId(@PathVariable Long profileId) {
    return new ResponseEntity<NotificationCountResponse>(notificationService.retrieveCountByProfileId(profileId), HttpStatus.OK);
  }

  @GetMapping("/getnotifications/{notificationtype}/{profileid}")
  public ResponseEntity<List<NotificationResponse>> retrieveByNotificationTypeProfile(@PathVariable(name = "notificationtype") String notificationType, @PathVariable(name = "profileid") String profileId) {
    return new ResponseEntity<>(notificationService.retrieveNotificationsByTypeProfile(notificationType, profileId), HttpStatus.OK);
  }
}
