package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY,reason="No data found")
public class NotFoundException extends ApplicationException{
    public static final String EXCEPTION_NOT_FOUND="exception.common.notFound";

    public NotFoundException(String args) {
        super(ErrorType.DATA_NOT_FOUND,EXCEPTION_NOT_FOUND,HttpStatus.UNPROCESSABLE_ENTITY,args);
    }
}
