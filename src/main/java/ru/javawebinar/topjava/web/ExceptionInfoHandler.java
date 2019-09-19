package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations= RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e){
        return logAndGetErrorInfo(req,e,false,DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req,DataIntegrityViolationException e){
        return logAndGetErrorInfo(req,e,true,DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req,Exception e){
        return logAndGetErrorInfo(req,e,false,VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req,Exception e){
        return logAndGetErrorInfo(req,e,true,APP_ERROR);
    }







    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType){
        Throwable rootCase= ValidationUtil.getRootCause(e);
        if(logException){
            log.error(errorType+"at request "+req.getRequestURL().toString(),rootCase);
        } else {
            log.warn("{} at request {}: {}",errorType,req.getRequestURL().toString(),rootCase.toString());
        }
        return new ErrorInfo(req.getRequestURL(),errorType,rootCase.toString());
    }

}
