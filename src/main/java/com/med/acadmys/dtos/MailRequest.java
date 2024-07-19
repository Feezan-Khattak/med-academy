package com.med.acadmys.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class MailRequest {
    private String subject;
    private String email;
    private String template;
    private HashMap<String, String> data;
}