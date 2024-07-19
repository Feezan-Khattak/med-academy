package com.med.acadmys.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtAuthenticationResponse {
    private String status;
    private String token;
    private String refreshToken;
}