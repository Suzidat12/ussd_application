package com.zik.ussd_application.dto.paysatckdto;

import lombok.Data;

@Data
public class InitiateRequest {
    private String amount;
    private String email;

}
