package com.discoverorg.responsewrap.responsewrapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ResponseWrappingAspect {

    @Pointcut("@target(com.discoverorg.responsewrap.responsewrapper.ResponseWrapped)")
    public void annotatedClass() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllersOnly() {}

    @Pointcut("annotatedClass() && restControllersOnly()")
    public void anyPublicMethodOfAnnotatedClass() {}

    @AfterReturning(
            value = "anyPublicMethodOfAnnotatedClass()",
            returning = "response")
    public Object wrapResponse(Object response) {
        return new ResponseWrapper(response);
    }

    @AfterThrowing(
            value = "anyPublicMethodOfAnnotatedClass()",
            throwing = "cause")
    public Object wrapException(JoinPoint joinPoint, Exception cause) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (joinPoint.getTarget() instanceof ExceptionToHttpStatusMapper) {
            status = ((ExceptionToHttpStatusMapper) joinPoint.getTarget()).getStatus(cause);
        }
        return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(cause), status);
    }
}
