package com.med.acadmys.controllers;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.dtos.UserSubscriptionRequest;
import com.med.acadmys.services.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/user-subscription")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    @PostMapping("/subscribe")
    ResponseEntity<Response> subscribeUser(@RequestBody UserSubscriptionRequest request) {
        Response response = userSubscriptionService.subscribeUser(request);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
