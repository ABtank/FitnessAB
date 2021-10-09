package ru.abtank.fitnessab.composite;

import java.math.BigDecimal;
import java.util.List;

public abstract class WorkoutComponent {


    public BigDecimal getOneRepMax() {
        throw new UnsupportedOperationException();
    }

    public BigDecimal getSumWeigth() {
        throw new UnsupportedOperationException();
    }

    public Integer getSumRep() {
        throw new UnsupportedOperationException();
    }

    public Integer geSumChild() {
        throw new UnsupportedOperationException();
    }

    public List<WorkoutComponent> addChild(WorkoutComponent we) {
        throw new UnsupportedOperationException();
    }

    public void removeChild(int index) {
        throw new UnsupportedOperationException();
    }

    public void setOrdinal(int index) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public Integer getOrdinal() {
        throw new UnsupportedOperationException();
    }

    public List<WorkoutComponent> getChild() {
        throw new UnsupportedOperationException();
    }
}