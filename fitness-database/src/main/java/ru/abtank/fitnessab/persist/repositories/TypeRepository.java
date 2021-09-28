package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.Type;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>, JpaSpecificationExecutor<Type> {

    Optional<Type> findByName(String type);

    Optional<Type> findByNameLike(String name);
}
