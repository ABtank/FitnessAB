package ru.abtank.fitnessab.persist.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.persist.entities.Type;

public final class TypeSpecification {

    public static Specification<Type> trueLiteral(){
        return (root,quary,builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Type> findByName(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), name);
    }

    public static Specification<Type> nameContains(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<Type> idNotEqual(Integer id){
        return (root,quary,builder) -> builder.notEqual(root.get("id"), id);
    }

}