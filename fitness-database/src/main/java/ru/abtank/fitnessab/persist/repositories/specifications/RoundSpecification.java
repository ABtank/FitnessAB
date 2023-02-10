package ru.abtank.fitnessab.persist.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.persist.entities.Round;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;

public final class RoundSpecification {

    //    аналог where 1=1
    public static Specification<Round> trueLiteral() {
        return (root, quary, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Round> workoutExerciseEquals(WorkoutExercise id) {
        return (root, quary, builder) -> builder.equal(root.get("workoutExercise"), id);
    }

}