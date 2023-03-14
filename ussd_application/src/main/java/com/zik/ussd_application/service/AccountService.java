package com.zik.ussd_application.service;

import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.dto.ApiResponse;
import com.zik.ussd_application.model.Accounts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AccountService {
    public ResponseEntity createAccount(AccountDto load);
    public ResponseEntity updateCreatedAccount(AccountDto load, String phoneNumber);
    public ResponseEntity deposit(Double amount,String phoneNumber);
    public ResponseEntity withdraw(Double amount,String pin);
    public ResponseEntity checkBalance(String phoneNumber);
    StreamingResponseBody downloadByPageable(HttpServletResponse response, int pageNumber, int pageSize) throws IOException, ParseException;
    StreamingResponseBody downloadByYear(HttpServletResponse response, Integer year) throws IOException, ParseException;



}
