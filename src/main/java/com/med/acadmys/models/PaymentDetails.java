package com.med.acadmys.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "payment_detail")
@EqualsAndHashCode(callSuper = true)
public class PaymentDetails extends BaseEntity {
    @Column(name = "transaction_id")
    private String transactionId;
    private double amount;
    private String status;
}
