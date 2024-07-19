package com.med.acadmys.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResetPasswordData {
    private String token;
    private String email;
    private String password;
}