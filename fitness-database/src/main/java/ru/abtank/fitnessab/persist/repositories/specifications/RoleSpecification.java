package ru.abtank.fitnessab.persist.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.persist.entities.Role;

public final class RoleSpecification {

    public static Specification<Role> nameLike(String name) {
        return (root, quary, builder) -> builder.like(root.get("name"), name);
    }

    public static Specification<Role> nameContains(String name) {
        return (root, quary, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Role> emailContains(String email) {
        return (root, quary, builder) -> builder.like(root.get("email"), "%" + email + "%");
    }

}