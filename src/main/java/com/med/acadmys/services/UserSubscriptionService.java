package com.med.acadmys.services;

import com.med.acadmys.dtos.Response;
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
                Type type = new TypeToken<UserSubscription>(){}.getType();
                UserSubscription response = modelMapper.map(savedUserSubscription, type);
                return responseUtil.generateSuccessResponse(response);
            } catch (Exception e) {
                return responseUtil.generateFailureResponse(e.getLocalizedMessage());
            }
        } else {
            return responseUtil.generateFailureResponse("No user or subscription plan found in the system, please provide the valid details");
        }
    }
}
