package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer> {

    @Query(nativeQuery = true, value = " SELECT\n" +
            "0 as id\n" +
            ",(select count(*) from categories) as category\n" +
            ",(select count(*) from exercises) as exercise\n" +
            ",(select count(*) from modes) as mode\n" +
            ",(select count(*) from types) as type\n" +
            ",(select count(*) from roles) as role\n" +
            ",(select count(*) from rounds) as round\n" +
            ",(select count(*) from users) as user\n" +
            ",(select count(*) from workouts) as workout")
    Counter getAllCount();

    @Query(nativeQuery = true, value = " SELECT\n" +
            ":id as id\n" +
            ",(select count(*) from categories c WHERE c.creator_id =:id) as category\n" +
            ",(select count(*) from exercises e WHERE e.creator_id =:id) as exercise\n" +
            ",0 as mode\n" +
            ",0 as type\n" +
            ",0 as role\n" +
            ",(select count(*) from rounds r " +
            "WHERE r.workout_id IN (select ww.workout_id from workouts ww WHERE ww.creator_id =:id)) as round\n" +
            ",0 as user\n" +
            ",(select count(*) from workouts w WHERE w.creator_id =:id) as workout")
    Counter getMyCount(@Param("id") Integer id);
}
