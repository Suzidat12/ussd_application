package com.zik.ussd_application.serviceImpl;

import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public ResponseEntity createAccount(AccountDto load) {
        return null;
    }

    @Override
    public ResponseEntity updateCreatedAccount(AccountDto load, Long id) {
        return null;
    }
}
