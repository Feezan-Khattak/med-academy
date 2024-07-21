package com.med.acadmys.services;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.dtos.UserSubscriptionDto;
import com.med.acadmys.dtos.UserSubscriptionRequest;
import com.med.acadmys.models.PaymentDetails;
import com.med.acadmys.models.SubscriptionPlan;
import com.med.acadmys.models.User;
import com.med.acadmys.models.UserSubscription;
import com.med.acadmys.repositories.PaymentDetailsRepository;
import com.med.acadmys.repositories.SubscriptionPlanRepository;
import com.med.acadmys.repositories.UserRepository;
import com.med.acadmys.repositories.UserSubscriptionRepository;
import com.med.acadmys.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final ResponseUtil responseUtil;
    private final ModelMapper modelMapper;

    @Transactional
    public Response subscribeUser(UserSubscriptionRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<SubscriptionPlan> plan = subscriptionPlanRepository.findById(request.getSubscriptionPlanId());
        if (user.isPresent() && plan.isPresent()) {
            try {
                PaymentDetails paymentDetails = new PaymentDetails();
                paymentDetails.setTransactionId(request.getTransactionId());
                paymentDetails.setAmount(plan.get().getPrice());
                paymentDetails.setStatus("COMPLETED");
                paymentDetailsRepository.save(paymentDetails);

                UserSubscription userSubscription = new UserSubscription();
                userSubscription.setActive(true);
                userSubscription.setUser(user.get());
                userSubscription.setSubscriptionPlan(plan.get());
                userSubscription.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
                userSubscription.setEndDate(Timestamp.valueOf(LocalDateTime.now().plusMonths(plan.get().getDurationMonths())));
                userSubscription.setPaymentDetails(paymentDetails);

                UserSubscription savedUserSubscription = userSubscriptionRepository.save(userSubscription);
                Type type = new TypeToken<UserSubscriptionDto>() {
                }.getType();
                UserSubscriptionDto response = modelMapper.map(savedUserSubscription, type);
                return responseUtil.generateSuccessResponse(response);
            } catch (Exception e) {
                return responseUtil.generateFailureResponse(e.getLocalizedMessage());
            }
        } else {
            return responseUtil.generateFailureResponse("No user or subscription plan found in the system, please provide the valid details");
        }
    }

    @Transactional
    public Response unsubscribeUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<UserSubscription> userSubscription = userSubscriptionRepository.findByUserAndActive(user.get(), true);
            if (userSubscription.isPresent()) {
                userSubscription.get().setActive(false);
                userSubscription.get().setEndDate(Timestamp.valueOf(LocalDateTime.now()));
                userSubscriptionRepository.save(userSubscription.get());
                return responseUtil.generateSuccessResponse("User unsubscribe from the system successfully");
            } else {
                return responseUtil.generateFailureResponse("No active subscription found against the user");
            }
        } else {
            return responseUtil.generateFailureResponse("No user found in the system, please provide the valid one");
        }
    }

    @Transactional
    public Response updateUserSubscriptionPlan(UserSubscriptionRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isPresent()) {
            Optional<UserSubscription> userSubscription = userSubscriptionRepository.findByUserAndActive(user.get(), true);
            Optional<SubscriptionPlan> newSubscriptionPlan = subscriptionPlanRepository.findById(request.getSubscriptionPlanId());
            if (userSubscription.isPresent() && newSubscriptionPlan.isPresent()) {

                PaymentDetails paymentDetails = new PaymentDetails();
                paymentDetails.setTransactionId(request.getTransactionId());
                paymentDetails.setAmount(newSubscriptionPlan.get().getPrice());
                paymentDetails.setStatus("COMPLETED");
                paymentDetailsRepository.save(paymentDetails);

                userSubscription.get().setSubscriptionPlan(newSubscriptionPlan.get());
                userSubscription.get().setEndDate(Timestamp.valueOf(LocalDateTime.now().plusMonths(newSubscriptionPlan.get().getDurationMonths())));
                userSubscription.get().setPaymentDetails(paymentDetails);
                UserSubscription updatedUserSubscription = userSubscriptionRepository.save(userSubscription.get());
                Type type = new TypeToken<UserSubscriptionDto>() {
                }.getType();
                UserSubscriptionDto response = modelMapper.map(updatedUserSubscription, type);
                return responseUtil.generateSuccessResponse(response);
            } else {
                return responseUtil.generateFailureResponse("No active or user subscription found in the system");
            }
        } else {
            return responseUtil.generateFailureResponse("No user found in the system with the provided id");
        }
    }
}
