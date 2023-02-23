package com.zik.ussd_application.feign;


import com.zik.ussd_application.dto.sms.SmsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedHashMap;

@FeignClient(value = "SMS", url = "https://api.ng.termii.com/api/sms/send")
public interface MessageFeign {

    @PostMapping(value = "", produces = "application/json")
    LinkedHashMap<String,Object> sendMessage(@RequestBody SmsRequest payload);










}
