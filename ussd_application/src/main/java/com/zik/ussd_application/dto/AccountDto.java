package com.zik.ussd_application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDto {
    private String accountNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;
    private Date dateOfBirth;
    private String gender;
    private String nationality;
    private String accountType;
    private String accountStatus;
    private String pin;
    private Double balance;
}
