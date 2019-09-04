package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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
}
