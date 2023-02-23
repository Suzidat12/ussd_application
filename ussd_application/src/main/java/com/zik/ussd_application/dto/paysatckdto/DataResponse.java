package com.zik.ussd_application.dto.paysatckdto;

import lombok.Data;

@Data
public class DataResponse {
    private String authorization_url;
    private String access_code;
    private String reference;
}
