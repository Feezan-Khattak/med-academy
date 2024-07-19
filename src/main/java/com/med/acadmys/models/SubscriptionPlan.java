package com.med.acadmys.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "subscription_plan")
@EqualsAndHashCode(callSuper = true)
public class SubscriptionPlan extends BaseEntity {
    private String name;
    private String description;

    @Column(name = "duration_months")
    private int durationMonths;

    @Column(name = "price")
    private int price;

    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSubscription> userSubscriptions = new HashSet<>();

}
