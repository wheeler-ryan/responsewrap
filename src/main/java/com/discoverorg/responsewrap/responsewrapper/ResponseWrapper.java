package com.discoverorg.responsewrap.responsewrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseWrapper<T> extends ResponseEntity {
    private boolean success = false;
    private Object response;
    private Exception cause;

    public ResponseWrapper(HttpStatus status) {
        super(status);
    }

    public ResponseWrapper(HttpStatus status, Exception e) {
        super(status);
        cause = e;
    }

    public ResponseWrapper(Object body, HttpStatus status) {
        super(body, status);
    }

    public ResponseWrapper(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseWrapper(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }


    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Exception getCause() {
        return cause;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }
}
