package com.med.acadmys.services;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.dtos.SubscriptionPlanDto;
import com.med.acadmys.models.SubscriptionPlan;
import com.med.acadmys.repositories.SubscriptionPlanRepository;
import com.med.acadmys.repositories.UserSubscriptionRepository;
import com.med.acadmys.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionPlanService {
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final ResponseUtil responseUtil;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Response getSubscriptionPlans() {
        List<SubscriptionPlan> subscriptionPlans = subscriptionPlanRepository.findAll();
        if (!subscriptionPlans.isEmpty()) {
            Type type = new TypeToken<List<SubscriptionPlanDto>>() {
            }.getType();
            List<SubscriptionPlanDto> response = modelMapper.map(subscriptionPlans, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No subscription plans exists in the database");
        }
    }

    @Transactional
    public Response addSubscription(SubscriptionPlan subscriptionPlan) {
        try {
            SubscriptionPlan subscription = subscriptionPlanRepository.save(subscriptionPlan);
            Type type = new TypeToken<SubscriptionPlanDto>() {
            }.getType();
            SubscriptionPlanDto response = modelMapper.map(subscription, type);
            return responseUtil.generateSuccessResponse(response);
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }

    @Transactional
    public Response updateSubscription(SubscriptionPlan subscriptionPlan) {
        try {
            SubscriptionPlan subscription = subscriptionPlanRepository.save(subscriptionPlan);
            Type type = new TypeToken<SubscriptionPlanDto>() {
            }.getType();
            SubscriptionPlanDto response = modelMapper.map(subscription, type);
            return responseUtil.generateSuccessResponse(response);
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public Response getSubscriptionPlanById(Long id) {
        Optional<SubscriptionPlan> subscriptionPlan = subscriptionPlanRepository.findById(id);
        if(subscriptionPlan.isPresent()) {
            Type type = new TypeToken<SubscriptionPlanDto>() {
            }.getType();
            SubscriptionPlanDto response = modelMapper.map(subscriptionPlan, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No subscription plan exists in the database with the provided id");
        }
    }

    @Transactional(readOnly = true)
    public Response getSubscriptionPlanByName(String name) {
        Optional<SubscriptionPlan> subscriptionPlan = subscriptionPlanRepository.findByName(name);
        if(subscriptionPlan.isPresent()) {
            Type type = new TypeToken<SubscriptionPlanDto>() {
            }.getType();
            SubscriptionPlanDto response = modelMapper.map(subscriptionPlan, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No subscription plan exists in the database with the provided name");
        }
    }

    public Response deleteSubscriptionPlan(Long id) {
        try {
            subscriptionPlanRepository.deleteById(id);
            return responseUtil.generateSuccessResponse("Subscription completely removed from the system");
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }
}
