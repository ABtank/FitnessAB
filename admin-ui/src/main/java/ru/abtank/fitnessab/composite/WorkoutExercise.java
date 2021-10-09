package ru.abtank.fitnessab.composite;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WorkoutExercise extends WorkoutComponent {
    private String exercise;
    private Integer ordinal;
    private List<WorkoutComponent> rounds;

    public WorkoutExercise(String exercise) {
        this.exercise = exercise;
        this.rounds = new ArrayList<>();
    }

    @Override
    public BigDecimal getOneRepMax() {
        BigDecimal oneRepMax= new BigDecimal(BigInteger.ZERO);
        for (int i = 0; i < rounds.size(); i++) {
            oneRepMax = oneRepMax.add(rounds.get(i).getOneRepMax());
        }
        return oneRepMax.divide(new BigDecimal(rounds.size()), 2, RoundingMode.DOWN);
    }

    @Override
    public BigDecimal getSumWeigth() {
        BigDecimal sumWeigth= new BigDecimal(BigInteger.ZERO);
        for (int i = 0; i < rounds.size(); i++) {
            sumWeigth = sumWeigth.add(rounds.get(i).getSumWeigth());
        }
        return sumWeigth;
    }

    @Override
    public Integer getSumRep() {
        Integer sumReps = 0;
        for (int i = 0; i < rounds.size(); i++) {
            sumReps += rounds.get(i).getSumRep();
        }
        return sumReps;
    }

    @Override
    public Integer geSumChild() {
        return rounds.size();
    }

    @Override
    public void setOrdinal(int index) {
        this.ordinal = index;
    }

    @Override
    public List<WorkoutComponent> addChild(WorkoutComponent we) {
        we.setOrdinal(rounds.size() + 1);
        rounds.add(we);
        return rounds;
    }

    @Override
    public void removeChild(int index) {
        rounds.remove(index);
        for (int i = 0; i < rounds.size(); i++) {
            rounds.get(i).setOrdinal(i+1);
        }
    }

    @Override
    public List<WorkoutComponent> getChild() {
        return rounds;
    }

    public String getName() {
        return exercise;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n Exercise{");
        sb.append("ordinal=").append(ordinal);
        sb.append(", ");
        sb.append("exercise=").append(exercise);
        sb.append(", ");
        rounds.forEach(r -> sb.append(r.toString()).append(", "));
        sb.setLength(sb.length()-2);
        sb.append("}");
        return sb.toString();
    }
}
