package com.discoverorg.responsewrap.responsewrapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseWrappingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllersOnly() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappedMethodsOnly() {}

    @Pointcut("target(com.discoverorg.responsewrap.responsewrapper.ResponseWrapped)")
    public void classesThatImplementResponseWrapped() {}

    @Pointcut("restControllersOnly() && requestMappedMethodsOnly() && classesThatImplementResponseWrapped()")
    public void controllersThatImplementResponseWrapped() {}

    @AfterReturning(
            value = "controllersThatImplementResponseWrapped()",
            returning = "response")
    public Object wrapResponse(Object response) {
        return new ResponseWrapper(response);
    }

    @AfterThrowing(
            value = "controllersThatImplementResponseWrapped()",
            throwing = "cause")
    public Object wrapException(JoinPoint joinPoint, Exception cause) {
        return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(cause), ((ResponseWrapped) joinPoint.getTarget()).getStatus(cause));
    }
}
