package com.discoverorg.responsewrap.responsewrapper;

import org.springframework.http.HttpStatus;

public interface ResponseWrapped {
    HttpStatus getStatus(Exception e);
}
