package ru.javawebinar.topjava.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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
            throw new IllegalArgumentException(bean + "must be new(id=null");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
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

    public static ResponseEntity<String> getResponseErrors(BindingResult result){
        StringJoiner joiner=new StringJoiner("<br>");
        result.getFieldErrors().forEach(fe->{
            String msg=fe.getDefaultMessage();
            if(msg!=null){
                if(!msg.startsWith(fe.getField())){
                    msg=fe.getField()+' '+msg;
                }
                joiner.add(msg);
            }
        });
        return ResponseEntity.unprocessableEntity().body(joiner.toString());
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
}
