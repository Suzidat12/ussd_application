package com.zik.ussd_application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String accountType;
    private String pin;

}
