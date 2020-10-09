package com.loginext.taxidriver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TaxiDriverApplicationException extends Exception {

    private static final long serialVersionUID = 4025856187797199395L;

    private HttpStatus errorCode;

    public TaxiDriverApplicationException(String message) {
        super(message);
    }

    public TaxiDriverApplicationException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
