package ru.abtank.fitnessab.persist.repositories;

import ru.abtank.fitnessab.persist.entities.Type;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {
    private static final ThreadLocal<IdentityMap> current = new ThreadLocal<>();

    public static void init(){
        current.set(new IdentityMap());
    }

    public static IdentityMap getCurrent(){
        return current.get();
    }

    private Map<Integer, Type> entities = new HashMap<>();

    public Type find(Integer id){
        return entities.get(id);
    }

    public void add(Type type){
        entities.put(type.getId(), type);
    }

    public void remove(Type type){
        entities.remove(type.getId(), type);
    }

    public void clear(){
        entities.clear();
    }
}
