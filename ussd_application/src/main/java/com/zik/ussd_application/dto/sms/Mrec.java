/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zik.ussd_application.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Olasunkanmi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mrec implements Serializable {
    String sms;
     List<String> to;
     String category;

}
