package com.discoverorg.responsewrap.responsewrapper;

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
    private Map<Class, HttpStatus> exceptionToStatusMap;

    @PostConstruct
    public void setUpExceptionToStatusMap() {
        exceptionToStatusMap = new HashMap<>();
        exceptionToStatusMap.put(AuthenticationException.class, HttpStatus.UNAUTHORIZED);
    }

    private HttpStatus getStatusForException(Exception e) {
        HttpStatus status = getExceptionToStatusMap().get(e.getClass());
        if (null == status) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
    }

    @Pointcut("@target(com.discoverorg.responsewrap.responsewrapper.ResponseWrapped)")
    public void annotatedClass() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllersOnly() {}

//    @Pointcut("execution(public * *(..))")
//    public void anyPublicMethodPointcut() {}

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
    public Object wrapException(Exception cause) {
        return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(cause), getStatusForException(cause));
    }

    public Map<Class, HttpStatus> getExceptionToStatusMap() {
        return exceptionToStatusMap;
    }

    public void setExceptionToStatusMap(Map<Class, HttpStatus> exceptionToStatusMap) {
        this.exceptionToStatusMap = exceptionToStatusMap;
    }
}
