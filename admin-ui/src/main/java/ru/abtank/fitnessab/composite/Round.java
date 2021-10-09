package ru.abtank.fitnessab.composite;

import java.math.BigDecimal;

public class Round extends WorkoutComponent {

    private String weight;
    private Integer reps;
    private Integer ordinal;

    public Round(String weight, Integer reps) {
        this.weight = weight;
        this.reps = reps;
    }

    @Override  // w(1+r/30)
    public BigDecimal getOneRepMax() {
        BigDecimal bReps = new BigDecimal(reps);
        BigDecimal bWeigth = new BigDecimal(weight);
        return bWeigth.multiply(new BigDecimal(1).add(bReps.multiply(new BigDecimal("0.03"))));
    }

    @Override
    public BigDecimal getSumWeigth() {
        return new BigDecimal(reps).multiply(new BigDecimal(weight));
    }

    @Override
    public Integer getSumRep() {
        return reps;
    }

    @Override
    public void setOrdinal(int index) {
        this.ordinal = index;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n  Round{");
        sb.append("ordinal=").append(ordinal);
        sb.append(", ");
        sb.append("reps=").append(reps);
        sb.append(", ");
        sb.append("weight=").append(weight).append(" }");
        return sb.toString();
    }
}
