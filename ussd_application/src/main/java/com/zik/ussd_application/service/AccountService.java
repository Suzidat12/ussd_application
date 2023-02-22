package com.zik.ussd_application.service;

import com.zik.ussd_application.dto.AccountDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    public ResponseEntity createAccount(AccountDto load);
    public ResponseEntity updateCreatedAccount(AccountDto load, String phoneNumber);
    public ResponseEntity deposit(Double amount,String phoneNumber);
}
