package com.zik.ussd_application.controller;


import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zik.ussd_application.utils.MessageUtil.*;


@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PostMapping(CREATE_ACCOUNT)
    public ResponseEntity createAccount(@RequestBody AccountDto load){
       return accountService.createAccount(load);
    }

    @PutMapping(UPDATE_ACCOUNT)
    public ResponseEntity updateCreatedAccount(@RequestBody AccountDto load,@RequestParam("phoneNumber") String phoneNumber){
        return accountService.updateCreatedAccount(load,phoneNumber);
    }

    @PostMapping(DEPOSIT)
    public ResponseEntity deposit(@RequestParam("amount") Double amount,@RequestParam("phoneNumber") String phoneNumber){
        return accountService.deposit(amount,phoneNumber);
    }

    @PostMapping(WITHDRAW)
    public ResponseEntity withdraw(@RequestParam("amount") Double amount,@RequestParam("phoneNumber") String phoneNumber, @RequestParam("pin") String pin){
        return accountService.withdraw(amount,phoneNumber,pin);
    }

    @GetMapping(BALANCE_CHECK)
    public ResponseEntity checkBalance(@RequestParam("phoneNumber") String phoneNumber){
        return accountService.checkBalance(phoneNumber);
    }

}
