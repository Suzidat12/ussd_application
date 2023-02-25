package com.zik.ussd_application.utils;


import java.util.Random;



public class AppUtils {

      public static String generateAccountNumber(){
        Random random = new Random();
        int accountNumber = random.nextInt(900000000) + 1000000000;
        return Integer.toString(accountNumber);
    }



}
