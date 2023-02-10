package ru.abtank.fitnessab.persist.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.persist.entities.Workout;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;

public final class WorkoutExerciseSpecification {

    //    аналог where 1=1
    public static Specification<WorkoutExercise> trueLiteral() {
        return (root, quary, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<WorkoutExercise> workoutEquals(Workout id) {
        return (root, quary, builder) -> builder.equal(root.get("workout"), id);
    }

}