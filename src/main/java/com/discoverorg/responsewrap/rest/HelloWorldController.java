package com.discoverorg.responsewrap.rest;

import com.discoverorg.responsewrap.responsewrapper.ExceptionToHttpStatusMapper;
import com.discoverorg.responsewrap.responsewrapper.ResponseWrapped;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseWrapped
public class HelloWorldController implements ExceptionToHttpStatusMapper {

    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World!!!";
    }

    @RequestMapping("/exception")
    public String getOther() throws Exception {
        throw new Exception("Something bad happened");
    }

    @Override
    public HttpStatus getStatus(Exception e) {
        return HttpStatus.ACCEPTED;
    }
}
