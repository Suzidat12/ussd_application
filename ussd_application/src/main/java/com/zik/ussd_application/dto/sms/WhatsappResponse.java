/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zik.ussd_application.dto.sms;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author Olasunkanmi
 */
@Data
public class WhatsappResponse implements Serializable {
//    String session_id;
    String message_id;
    String message;
    Long balance;
    String user;
    
    
}
