package com.discoverorg.responsewrap.rest;

import com.discoverorg.responsewrap.responsewrapper.ResponseWrapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public ResponseWrapper<String>  helloWorld() {
        return new ResponseWrapper("Hello World!!!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/exception")
    public ResponseWrapper<String> getOther() throws Exception {
        throw new Exception("Something bad happened");
    }

    @ExceptionHandler
    public ResponseWrapper<Exception> exceptionHandler(Exception e) {
        return new ResponseWrapper<Exception>(e, HttpStatus.UNAUTHORIZED);
    }
}
