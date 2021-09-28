package ru.abtank.fitnessab.persist.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.persist.entities.Mode;

public final class ModeSpecification {

    public static Specification<Mode> findByName(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), name);
    }

    public static Specification<Mode> nameContains(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<Mode> idNotEqual(Integer id){
        return (root,quary,builder) -> builder.notEqual(root.get("id"), id);
    }

}