package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations= RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL="user.duplicateEmail";
    public static final String EXCEPTION_DUPLICATE_DATETIME="meal.duplicateDateTime";

    private static final Map<String,String> MAP_CONSTRAINT_I18N=Map.of(
        "users_unique_email_idx",EXCEPTION_DUPLICATE_EMAIL,
        "meals_unique_user_datetime_idx",EXCEPTION_DUPLICATE_DATETIME
    );

    @Autowired
    private MessageUtil messageUtil;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e){
        return logAndGetErrorInfo(req,e,false,DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req,DataIntegrityViolationException e){
        String rootExMessage=ValidationUtil.getRootCause(e).getMessage();
        if(rootExMessage!=null){
            String lowerExMessage=rootExMessage.toLowerCase();
            Optional<Map.Entry<String,String>> entry=MAP_CONSTRAINT_I18N.entrySet().stream()
                    .filter(elem->lowerExMessage.contains(elem.getKey()))
                    .findAny();
            if(entry.isPresent()){
                return logAndGetErrorInfo(req,e,true,DATA_ERROR,messageUtil.getMessage(entry.get().getValue()));
            }
        }
        return logAndGetErrorInfo(req,e,true,DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo validationErrors(HttpServletRequest req,Exception e){
        BindingResult result=e instanceof BindException ? ((BindException)e).getBindingResult() :
                ((MethodArgumentNotValidException)e).getBindingResult();
        String[] details=result.getFieldErrors().stream().
                map(fe->{
                    String msg=fe.getDefaultMessage();
                    return msg==null ? null : msg.startsWith(fe.getField()) ? msg : fe.getField()+' '+msg;
                }).filter(Objects::nonNull)
                .toArray(String[]::new);
        return logAndGetErrorInfo(req,e,false,VALIDATION_ERROR,details);
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


    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType,String... details){
        Throwable rootCase= ValidationUtil.getRootCause(e);
        if(logException){
            log.error(errorType+"at request "+req.getRequestURL().toString(),rootCase);
        } else {
            log.warn("{} at request {}: {}",errorType,req.getRequestURL().toString(),rootCase.toString());
        }
        return new ErrorInfo(req.getRequestURL(),errorType,details.length!=0 ? details : new String[]{ValidationUtil.getExceptionMessage(rootCase)});
    }

}
