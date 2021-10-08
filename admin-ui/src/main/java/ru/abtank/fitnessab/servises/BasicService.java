package ru.abtank.fitnessab.servises;

import java.util.List;
import java.util.Optional;

public interface BasicService<E> {
    List<E> findAll();
    Optional<E> findById(Integer id);
    Optional<E> findByName(String name);
    void deleteById(Integer id);
    void deleteAll();
    E save(E o);
}
