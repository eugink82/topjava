package ru.javawebinar.topjava;

public class Profiles {
    public static final String JPA="JPA",
    JDBC="JDBC";
    public static final String REPOSITORY_IMPLEMENTATION=JPA;
    public static final String POSTGRES_DB="postgres",
            HSQL_DB="hsqldb";
    public static final String ACTIVE_DB=POSTGRES_DB;

    public static String getActiveDBProfile(){
        try{
            Class.forName("org.postgresql.Driver");
            return Profiles.POSTGRES_DB;
        }
        catch(ClassNotFoundException e){
            try{
               Class.forName("org.hsqldb.jdbcDriver");
               return Profiles.HSQL_DB;
            }
            catch(ClassNotFoundException ex){
               throw new IllegalStateException("Нет подходящего драйвера БД");
            }
        }
    }
}
