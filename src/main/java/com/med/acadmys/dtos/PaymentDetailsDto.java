package com.med.acadmys.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentDetailsDto extends BaseEntityDto {
    private String transactionId;
    private double amount;
    private String status;
}
