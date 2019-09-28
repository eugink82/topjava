package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.Set;
import java.util.StringJoiner;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, Integer.toString(id));
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, Integer.toString(id));
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Не найдена сущность с id=" + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + "must be new(id=null");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable t){
        Throwable cause=null;
        Throwable result=t;
        while((cause=result.getCause())!=null && (cause!=result)){
            result=cause;
        }
        return result;
    }


    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.getValidator();
    }

    public static <T> void  validate(T bean){
        Set<ConstraintViolation<T>> violations=validator.validate(bean);
        if(!violations.isEmpty()){
            throw  new ConstraintViolationException(violations);
        }
    }

    public static String getExceptionMessage(Throwable e){
        return e.getLocalizedMessage()!=null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(HttpServletRequest req, Logger log, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCase= ValidationUtil.getRootCause(e);
        if(logException){
            log.error(errorType+"at request "+req.getRequestURL().toString(),rootCase);
        } else {
            log.warn("{} at request {}: {}",errorType,req.getRequestURL().toString(),rootCase.toString());
        }
        return rootCase;
    }
}
