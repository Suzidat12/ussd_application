package com.zik.ussd_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UssdApplication {

	public static void main(String[] args) {
		SpringApplication.run(UssdApplication.class, args);
	}

}
