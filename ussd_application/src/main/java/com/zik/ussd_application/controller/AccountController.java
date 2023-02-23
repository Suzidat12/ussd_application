package com.zik.ussd_application.controller;


import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ussd")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PostMapping("/create/account")
    public ResponseEntity createAccount(@RequestBody AccountDto load){
       return accountService.createAccount(load);
    }

    @PutMapping("/update/account")
    public ResponseEntity updateCreatedAccount(@RequestBody AccountDto load,@RequestParam("phoneNumber") String phoneNumber){
        return accountService.updateCreatedAccount(load,phoneNumber);
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestParam("amount") Double amount,@RequestParam("phoneNumber") String phoneNumber){
        return accountService.deposit(amount,phoneNumber);
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestParam("amount") Double amount,@RequestParam("phoneNumber") String phoneNumber){
        return accountService.withdraw(amount,phoneNumber);
    }

    @GetMapping("/checkBalance")
    public ResponseEntity checkBalance(@RequestParam("phoneNumber") String phoneNumber){
        return accountService.checkBalance(phoneNumber);
    }
}
