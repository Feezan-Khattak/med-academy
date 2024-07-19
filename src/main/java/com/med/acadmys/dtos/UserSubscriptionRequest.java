package com.med.acadmys.dtos;

import lombok.Data;

@Data
public class UserSubscriptionRequest {
    private Long userId;
    private Long subscriptionPlanId;
    private String transactionId;
}
