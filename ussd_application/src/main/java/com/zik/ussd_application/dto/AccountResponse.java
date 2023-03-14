package com.zik.ussd_application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String accountStatus;
    private String accountNumber;
    private Double balance;
    private Date datecreated;
    private Date lastTransaction;
}
