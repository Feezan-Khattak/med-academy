package com.med.acadmys.dtos;

import com.med.acadmys.models.PaymentDetails;
import com.med.acadmys.models.SubscriptionPlan;
import com.med.acadmys.models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSubscriptionDto extends BaseEntityDto {
    private Timestamp startDate;
    private Timestamp endDate;
    private UserDto user;
    private SubscriptionPlanDto subscriptionPlan;
    private PaymentDetailsDto paymentDetails;

    @Data
    static class UserDto {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
    }
}
