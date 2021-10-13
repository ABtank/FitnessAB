package ru.abtank.fitnessab.persist.repositories;

import ru.abtank.fitnessab.persist.entities.Type;

public class TypeFinder {
    public Type find(Integer id){
        return Type.builder().name("Глобальный").descr("").build();
    }
}
