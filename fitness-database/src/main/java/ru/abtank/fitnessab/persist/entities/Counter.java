package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Counter {
    private Integer id;
    @Column(name = "workout")
    private Integer workout;
    @Column(name = "exercise")
    private Integer exercise;
    @Column(name = "category")
    private Integer category;
    @Column(name = "round")
    private Integer round;
    @Column(name = "mode")
    private Integer mode;
    @Column(name = "type")
    private Integer type;
    @Column(name = "role")
    private Integer role;
    @Column(name = "user")
    private Integer user;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }
}
