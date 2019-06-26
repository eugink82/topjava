package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryBaseRepository<T extends AbstractBaseEntity> {
    private Map<Integer,T> entranceMap=new ConcurrentHashMap<>();
    private AtomicInteger counter=new AtomicInteger(0);

    public T save(T entrance){
        if(entrance.isNew()){
           entrance.setId(counter.incrementAndGet());
           entranceMap.put(entrance.getId(),entrance);
           return entrance;
        }
        return entranceMap.computeIfPresent(entrance.getId(),(id,oldEntrance)->entrance);
    }

    public T get(int id){
        return entranceMap.get(id);
    }

    public boolean delete(int id){
        return entranceMap.remove(id)!=null;
    }

    public Collection<T> getCollection(){
        return entranceMap.values();
    }


}
