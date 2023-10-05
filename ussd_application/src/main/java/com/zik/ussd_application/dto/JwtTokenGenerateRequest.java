package com.zik.ussd_application.dto;

import com.zik.ussd_application.IssueType.JwtIssuerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenGenerateRequest {
    private String subject;
    private JwtIssuerType jwtIssuer;  // Enum representing the issuer type (e.g., ACCESS or REFRESH)
}

