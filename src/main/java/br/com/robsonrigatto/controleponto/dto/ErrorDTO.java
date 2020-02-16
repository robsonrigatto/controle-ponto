package br.com.robsonrigatto.controleponto.dto;

import java.io.Serializable;

public class ErrorDTO implements Serializable {

    private Integer code;
    private String message;
    private String details;

    public ErrorDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(Integer code, String message, String e) {
        this(code, message);
        this.details = e;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
