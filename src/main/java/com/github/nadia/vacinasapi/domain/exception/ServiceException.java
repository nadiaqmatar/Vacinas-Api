package com.github.nadia.vacinasapi.domain.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String msg){
        super(msg);
    }
}
