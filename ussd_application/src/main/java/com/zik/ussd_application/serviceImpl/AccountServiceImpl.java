package com.zik.ussd_application.serviceImpl;

import com.zik.ussd_application.accountRepo.AccountRepo;
import com.zik.ussd_application.dto.AccountDto;
import com.zik.ussd_application.dto.sms.Mrec;
import com.zik.ussd_application.dto.sms.SmsRequest;
import com.zik.ussd_application.enums.AccountStatus;
import com.zik.ussd_application.exception.DuplicationRecordException;
import com.zik.ussd_application.exception.InsufficientException;
import com.zik.ussd_application.exception.RecordNotFoundException;
import com.zik.ussd_application.feign.MessageFeign;
import com.zik.ussd_application.model.Accounts;
import com.zik.ussd_application.service.AccountService;

import com.zik.ussd_application.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static com.zik.ussd_application.utils.MessageUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final MessageFeign messageFeign;
    @Value("${API_KEY}")
    private String API_KEY;
    @Value("${API_FROM}")
    private String API_FROM;
    @Value("${API_CHANNELOTP}")
    private String sms;
    @Value("${API_TYPE}")
    private String API_TYPE;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

//The SMS implementation is working fine, but I decided to commented it because I will not be
//    pushing the credentials along with it ,but during review I can share it.
    private String getPhone(String phone) {
        if(phone.startsWith("+234")){
            return phone.substring(1);
        }
        if(phone.startsWith("234")){
            return phone;
        }

        return "234"+phone.substring(1);

    }

    private void postToPhone(Mrec load) {

        SmsRequest req = SmsRequest.builder()
                .api_key(API_KEY)
                .sms(load.getSms())
                .channel(load.getCategory().equals("sms") ? sms : null)
                .from(API_FROM)
                .to(load.getTo())
                .type(API_TYPE)
                .build();
        HashMap<String, Object> result = messageFeign.sendMessage(req);
        log.info("{}", result);
    }
    private  Accounts validateAccount(String phoneNumber) {
        Optional<Accounts> accountsOptional = accountRepo.checkRecord(phoneNumber);
        if (accountsOptional.isEmpty())
            throw new RecordNotFoundException(ACCOUNT_NOT_FOUND);
        return accountsOptional.get();

    }

    private  Accounts validateWithdrawal(String phoneNumber,String pin) {
        Optional<Accounts> accountsOptional = accountRepo.checkPhoneNumberAndPin(phoneNumber, pin);
        if (accountsOptional.isEmpty())
            throw new RecordNotFoundException(PHONE_NUMBER_PIN_NOT_CORRECT);
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
            accounts.setAccountStatus(AccountStatus.COMPLETED.name());
            accountRepo.save(accounts);
//            if(accounts.getAccountStatus()!=null && accounts.getAccountStatus().equals("COMPLETED")){
//                postToPhone(Mrec.builder()
//                        .category("sms")
//                        .sms("You have successfully created an account with phone number: " + accounts.getPhoneNumber()+ " with account number "+ accounts.getAccountNumber())
//                        .to(Arrays.asList(getPhone(accounts.getPhoneNumber())))
//                        .build());
//            }
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
        //check if the phone number is valid
        Accounts accounts = validateAccount(phoneNumber);
        accounts.setLastTransactionDate(new Date());
        accounts.setBalance(accounts.getBalance()+amount);
        accountRepo.save(accounts);
//        if(accounts.getAccountStatus()!=null && accounts.getAccountStatus().equals("COMPLETED")){
//            postToPhone(Mrec.builder()
//                    .category("sms")
//                    .sms("You have successfully deposited ₦"+amount+ "into your account")
//                    .to(Arrays.asList(getPhone(accounts.getPhoneNumber())))
//                    .build());
//        }
        return ResponseEntity.ok(ACCOUNT_DEPOSIT+ amount +DEPOSIT_SUCCESSFUL);
    }

    @Override
    public ResponseEntity withdraw(Double amount, String phoneNumber,String pin) {
        // check if the phone number and the pin is correct
        Accounts accounts = validateWithdrawal(phoneNumber,pin);
        if(amount > accounts.getBalance()){
            throw  new InsufficientException(INSUFFICIENT_BALANCE);
        }
            accounts.setBalance(accounts.getBalance()-amount);
            accounts.setLastTransactionDate(new Date());
            accountRepo.save(accounts);
//        if(accounts.getAccountStatus()!=null && accounts.getAccountStatus().equals("COMPLETED")){
//            postToPhone(Mrec.builder()
//                    .category("sms")
//                    .sms("You have successfully withdraw ₦"+amount+ "from your account")
//                    .to(Arrays.asList(getPhone(accounts.getPhoneNumber())))
//                    .build());
//        }
            return ResponseEntity.ok(ACCOUNT_WITHDRAW+ amount +WITHDRAW_SUCCESSFUL);

    }

    @Override
    public ResponseEntity checkBalance(String phoneNumber) {
        Accounts accounts = validateAccount(phoneNumber);
        Double checkBalance = accounts.getBalance();
//        if(accounts.getAccountStatus()!=null && accounts.getAccountStatus().equals("COMPLETED")){
//            postToPhone(Mrec.builder()
//                    .category("sms")
//                    .sms("Your balance is ₦"+checkBalance)
//                    .to(Arrays.asList(getPhone(accounts.getPhoneNumber())))
//                    .build());
//        }
        return ResponseEntity.ok(CHECK_BALANCE + checkBalance);
    }
}
