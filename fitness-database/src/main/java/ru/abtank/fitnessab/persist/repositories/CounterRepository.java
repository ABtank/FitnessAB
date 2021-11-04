package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer> {

    @Query(nativeQuery = true, value = " SELECT\n"  +
            "1 as id\n" +
            ",(select count(*) from categories) as category\n" +
            ",(select count(*) from exercises) as exercise\n" +
            ",(select count(*) from modes) as mode\n" +
            ",(select count(*) from types) as type\n" +
            ",(select count(*) from roles) as role\n" +
            ",(select count(*) from rounds) as round\n" +
            ",(select count(*) from users) as user\n" +
            ",(select count(*) from workouts) as workout")
    Counter getAllCount();
}
