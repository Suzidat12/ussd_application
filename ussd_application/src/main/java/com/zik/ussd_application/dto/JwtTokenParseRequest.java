package com.zik.ussd_application.dto;

import com.zik.ussd_application.IssueType.JwtIssuerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenParseRequest {
    private String jwt_token;
    private JwtIssuerType jwtIssuerType;
}
