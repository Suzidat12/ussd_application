package com.zik.ussd_application.serviceImpl;

import com.zik.ussd_application.accountRepo.AccountRepo;
import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.exception.DuplicationRecordException;
import com.zik.ussd_application.exception.InsufficientException;
import com.zik.ussd_application.exception.RecordNotFoundException;
import com.zik.ussd_application.model.Accounts;
import com.zik.ussd_application.service.AccountService;

import com.zik.ussd_application.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static com.zik.ussd_application.utils.MessageUtil.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private  Accounts validateAccount(String phoneNumber) {
        Optional<Accounts> accountsOptional = accountRepo.checkRecord(phoneNumber);
        if (accountsOptional.isEmpty())
            throw new RecordNotFoundException(ACCOUNT_NOT_FOUND);
        return accountsOptional.get();

    }
    @Override
    public ResponseEntity createAccount(AccountDto load) {
        //check if phone number  already exist
        List<Accounts> accountsList = accountRepo.isRecordExist(load.getPhoneNumber());
        if(!accountsList.isEmpty()){
           throw new  DuplicationRecordException(DUPLICATE_ACCOUNT);
        }else{
            Accounts accounts = new Accounts();
            accounts.setFirstName(load.getFirstName());
            accounts.setLastName(load.getLastName());
            accounts.setPhoneNumber(load.getPhoneNumber());
            accounts.setDateOfBirth(LocalDate.parse(load.getDateOfBirth(),formatter));
            accounts.setGender(load.getGender());
            accounts.setAddress(load.getAddress());
            accounts.setAccountNumber(AppUtils.generateAccountNumber());
            accounts.setAccountType(load.getAccountType());
            accounts.setPin(AppUtils.encryptPin(load.getPin()));
            accounts.setBalance(0.00);
            accounts.setDatecreated(new Date());
            accountRepo.save(accounts);
            return ResponseEntity.ok(ACCOUNT_CREATED);

        }
    }

    @Override
    public ResponseEntity updateCreatedAccount(AccountDto load, String phoneNumber) {
        Accounts accounts = validateAccount(phoneNumber);
        accounts.setFirstName(load.getFirstName());
        accounts.setLastName(load.getLastName());
        accounts.setAddress(load.getAddress());
        accounts.setPin(AppUtils.encryptPin(load.getPin()));
        accounts.setAccountType(load.getAccountType());
        accounts.setPhoneNumber(load.getPhoneNumber());
        accounts.setDateOfBirth(LocalDate.parse(load.getDateOfBirth(),formatter));
        accounts.setGender(load.getGender());
        accounts.setDatecreated(new Date());
        accountRepo.save(accounts);
        return ResponseEntity.ok(ACCOUNT_UPDATED);
    }

    @Override
    public ResponseEntity deposit(Double amount , String phoneNumber) {
        Accounts accounts = validateAccount(phoneNumber);
        accounts.setLastTransactionDate(new Date());
        accounts.setBalance(accounts.getBalance()+amount);
        accountRepo.save(accounts);
        return ResponseEntity.ok(ACCOUNT_DEPOSIT+ amount +DEPOSIT_SUCCESSFUL);
    }

    @Override
    public ResponseEntity withdraw(Double amount, String phoneNumber) {
        Accounts accounts = validateAccount(phoneNumber);
        if(amount > accounts.getBalance()){
            throw  new InsufficientException(INSUFFICIENT_BALANCE);
        }
            accounts.setBalance(accounts.getBalance()-amount);
            accounts.setLastTransactionDate(new Date());
            accountRepo.save(accounts);
            return ResponseEntity.ok(ACCOUNT_WITHDRAW+ amount +WITHDRAW_SUCCESSFUL);


    }

    @Override
    public ResponseEntity checkBalance(String phoneNumber) {
        Accounts accounts = validateAccount(phoneNumber);
        Double checkBalance = accounts.getBalance();
        return ResponseEntity.ok(CHECK_BALANCE + checkBalance);
    }
}
