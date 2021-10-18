package ru.abtank.fitnessab.composite;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
//@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@NoArgsConstructor
//@Data
public class Workout extends WorkoutComponent {
    private String workout;
    private List<WorkoutComponent> exercises;

//    @PostConstruct
//    public void init() {
//        exercises = new ArrayList<>();
//    }

    public Workout(String workout) {
        this.workout = workout;
        this.exercises = new ArrayList<>();
    }

    @Override
    public BigDecimal getSumWeigth() {
        BigDecimal sumWeigth = new BigDecimal(BigInteger.ZERO);
        for (int i = 0; i < exercises.size(); i++) {
            sumWeigth = sumWeigth.add(exercises.get(i).getSumWeigth());
        }
        return sumWeigth;
    }

    @Override
    public Integer getSumRep() {
        Integer sumReps = 0;
        for (int i = 0; i < exercises.size(); i++) {
            sumReps += exercises.get(i).getSumRep();
        }
        return sumReps;
    }

    @Override
    public Integer geSumChild() {
        return exercises.size();
    }

    @Override
    public List<WorkoutComponent> addChild(WorkoutComponent we) {
        we.setOrdinal(exercises.size() + 1);
        exercises.add(we);
        return exercises;
    }

    @Override
    public void removeChild(int index) {
        exercises.remove(index);
        for (int i = 0; i < exercises.size(); i++) {
            exercises.get(i).setOrdinal(i + 1);
        }
    }

    @Override
    public List<WorkoutComponent> getChild() {
        return exercises;
    }

    @Override
    public String getName() {
        return workout;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nWorkout{");
        sb.append("workout=").append(workout);
        sb.append(", ");
        exercises.forEach(e -> sb.append(e.toString()).append(", "));
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    public void clear() {
        exercises.clear();
        workout="";
    }
}
