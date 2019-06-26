package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class Util {
    public static <T extends Comparable<? super T>>  boolean isBetween(T lt, @Nullable T start,@Nullable T end){
        return (start==null || lt.compareTo(start)>=0) && (end==null || lt.compareTo(end)<=0);
    }

}
