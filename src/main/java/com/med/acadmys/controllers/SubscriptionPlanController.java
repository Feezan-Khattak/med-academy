package com.med.acadmys.controllers;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.SubscriptionPlan;
import com.med.acadmys.services.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/subscription")
public class SubscriptionPlanController {
    private final SubscriptionPlanService subscriptionPlanService;

    @PostMapping("/add")
    ResponseEntity<Response> addSubscription(@RequestBody SubscriptionPlan subscriptionPlan) {
        Response response = subscriptionPlanService.addSubscription(subscriptionPlan);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateSubscription(@RequestBody SubscriptionPlan subscriptionPlan) {
        Response response = subscriptionPlanService.updateSubscription(subscriptionPlan);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    ResponseEntity<Response> getSubscriptionPlanById(@PathVariable Long id) {
        Response response = subscriptionPlanService.getSubscriptionPlanById(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    ResponseEntity<Response> getSubscriptionPlanByName(@PathVariable String name) {
        Response response = subscriptionPlanService.getSubscriptionPlanByName(name);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    ResponseEntity<Response> getAllSubscriptionPlans() {
        Response response = subscriptionPlanService.getSubscriptionPlans();
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/delete/{id}")
    ResponseEntity<Response> deleteSubscriptionPlan(@PathVariable Long id) {
        Response response = subscriptionPlanService.deleteSubscriptionPlan(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
