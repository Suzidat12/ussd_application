package com.zik.ussd_application.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nandom Gusen
 * @param <T>
 */ 

public class ApiResponse<T> {
    private String message;
    private String code;
    private T data;
    private Map<String, Object> meta = new HashMap<>();

    public ApiResponse() {
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public ApiResponse addMeta(String key, Object value){
        meta.put(key, value);
        return this;
    }
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public ApiResponse(String message, String code, T data) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    

}
