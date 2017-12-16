package com.discoverorg.responsewrap.responsewrapper;

public class ResponseWrapper {
    private boolean success = false;
    private Object response;
    private Exception cause;

    public ResponseWrapper(Object response) {
        this.response = response;
        success = true;
    }

    public ResponseWrapper(Exception cause) {
        this.cause = cause;
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
