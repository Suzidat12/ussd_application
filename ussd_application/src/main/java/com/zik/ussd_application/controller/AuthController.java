package com.zik.ussd_application.controller;

import com.zik.ussd_application.IssueType.JwtIssuerType;
import com.zik.ussd_application.accountRepo.AccountRepo;
import com.zik.ussd_application.dto.AuthResponse;
import com.zik.ussd_application.dto.JwtTokenGenerateRequest;
import com.zik.ussd_application.dto.LoginRequest;
import com.zik.ussd_application.model.Accounts;
import com.zik.ussd_application.utils.JwtTokenUtil;
import com.zik.ussd_application.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepo accountRepo;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Fetch user details from your user repository or service
        Optional<Accounts> user = accountRepo.checkRecord(loginRequest.getUsername());

        if (user.isPresent() && loginRequest.getPassword().equals(user.get().getPin())) {
            // User is authenticated, generate a JWT token
            JwtTokenGenerateRequest tokenRequest = new JwtTokenGenerateRequest();
            tokenRequest.setSubject(user.get().getPhoneNumber());
            tokenRequest.setJwtIssuer(JwtIssuerType.HelloAI_Access); // Set issuer type
            String token = jwtTokenUtil.generateToken(tokenRequest);

            // Return the token in the response
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



}
