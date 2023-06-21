package com.zik.ussd_application.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PaymentScheduler {
    public static String calculateDueDate(String dateString, int daysToAdd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        LocalDate dueDate = localDate.plusDays(daysToAdd);
        return dueDate.format(formatter);
    }

    public static void main(String[] args) {
        PaymentScheduler paymentScheduler = new PaymentScheduler();
        String result= paymentScheduler.calculateDueDate("09-04-2023",1);

        System.out.println("Due date: " + result);
    }



}

