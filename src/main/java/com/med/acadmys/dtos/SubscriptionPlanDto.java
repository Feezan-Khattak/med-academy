package com.med.acadmys.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubscriptionPlanDto extends BaseEntityDto {
    private String name;
    private String description;
    private int durationMonths;
    private int price;
    private Set<UserSubscriptionDto> userSubscriptions = new HashSet<>();
}
