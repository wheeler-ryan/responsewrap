package com.discoverorg.responsewrap.rest;

import com.discoverorg.responsewrap.responsewrapper.ResponseWrapped;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS ) // implementing interfaces breaks the normally proxying mechanism Spring uses
public class HelloWorldController implements ResponseWrapped {

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
