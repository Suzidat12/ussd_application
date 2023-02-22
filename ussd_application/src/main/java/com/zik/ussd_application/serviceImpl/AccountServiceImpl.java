package com.zik.ussd_application.serviceImpl;

import com.zik.ussd_application.accountRepo.AccountRepo;
import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.exception.DuplicationRecordException;
import com.zik.ussd_application.model.Accounts;
import com.zik.ussd_application.service.AccountService;

import com.zik.ussd_application.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


import static com.zik.ussd_application.utils.MessageUtil.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;


    @Override
    public ResponseEntity createAccount(AccountDto load) {
        //check if phone number or account number already exist
        List<Accounts> accountsList = accountRepo.isRecordExist(load.getPhoneNumber(), load.getAccountNumber());
        if(!accountsList.isEmpty()){
           throw new  DuplicationRecordException(DUPLICATE);
        }else{
            Accounts accounts = new Accounts();
            accounts.setFirstName(load.getFirstName());
            accounts.setLastName(load.getLastName());
            accounts.setDateOfBirth(load.getDateOfBirth());
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
    public ResponseEntity updateCreatedAccount(AccountDto load, Long id) {
        return null;
    }
}
