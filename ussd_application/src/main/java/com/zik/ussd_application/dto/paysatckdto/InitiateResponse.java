package com.zik.ussd_application.dto.paysatckdto;

import lombok.Data;

@Data
public class InitiateResponse  {
    private Boolean status;
    private String message;
    private DataResponse data;
}
