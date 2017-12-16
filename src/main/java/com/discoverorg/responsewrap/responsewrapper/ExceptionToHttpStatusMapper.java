package com.discoverorg.responsewrap.responsewrapper;

import org.springframework.http.HttpStatus;

public interface ExceptionToHttpStatusMapper {
    HttpStatus getStatus(Exception e);
}
