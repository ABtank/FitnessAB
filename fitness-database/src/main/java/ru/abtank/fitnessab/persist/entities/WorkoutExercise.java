package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "workouts_exercises")
@Data
@NoArgsConstructor
public class WorkoutExercise implements Serializable {
    private static final long SerialVersionUID = -4929812469346284072L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_exercise_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "mode_id", nullable = false)
    private Mode mode;

    @Column(name = "ordinal", nullable = false)
    private Integer ordinal;

    @Column(name = "descr")
    private String descr;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable = false)
    private Date createDate;

    public WorkoutExercise(Integer id,Workout workout, Exercise exercise, Mode mode, Integer ordinal, String descr, User creator) {
        this.id = id;
        this.workout = workout;
        this.exercise = exercise;
        this.mode = mode;
        this.ordinal = ordinal;
        this.descr = descr;
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @PrePersist
    public void setCreateDate() {
        this.createDate = new Date();
    }

}
