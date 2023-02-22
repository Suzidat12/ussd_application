package com.zik.ussd_application.utils;


import com.zik.ussd_application.accountRepo.AccountRepo;
import com.zik.ussd_application.exception.RecordNotFoundException;
import com.zik.ussd_application.model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.Random;

import static com.zik.ussd_application.utils.MessageUtil.ACCOUNT_NOT_FOUND;

public class AppUtils {

     private static final byte[] KEY = { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef };
     public static String generateAccountNumber(){
        Random random = new Random();
        int accountNumber = random.nextInt(900000000) + 1000000000;
        return Integer.toString(accountNumber);
    }

    public static String encryptPin(String pin) {
        byte[] pinBytes = pin.getBytes();
        byte[] encryptedBytes = new byte[pinBytes.length];
        for (int i = 0; i < pinBytes.length; i++) {
            encryptedBytes[i] = (byte) (pinBytes[i] ^ KEY[i % KEY.length]);
        }
        return new String(encryptedBytes);
    }

}
