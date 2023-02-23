package com.zik.ussd_application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


//@FeignClient(value = "Paystack", url = "https://api.paystack.co/")
public interface PaystackFeign {
//    @PostMapping(value = "transaction/initialize", produces = "applicaion/json")
//    InitiateResponse initiateTransaction(@RequestBody InitiateRequest payload,
//                                         @RequestHeader(value = "Authorization", required = true) String authorizationHeader);


}
