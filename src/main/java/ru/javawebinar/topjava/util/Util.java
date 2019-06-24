package ru.javawebinar.topjava.util;

public class Util {
    public static <T extends Comparable<? super T>>  boolean isBetween(T lt,T start, T end){
        return lt.compareTo(start)>=0 && lt.compareTo(end)<=0;
    }

}
