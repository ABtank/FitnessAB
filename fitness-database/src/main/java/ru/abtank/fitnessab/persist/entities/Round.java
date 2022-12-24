package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rounds")
@Data
@NoArgsConstructor
public class Round implements Serializable {
    private static final long SerialVersionUID = 2944526495564810297L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;

    @Column(name = "weight")
    private String weight;

    @Column(name = "reps")
    private Integer reps;

    @Column(name = "descr")
    private String descr;

    @Column(name = "cardio_1")
    private String cardio1;

    @Column(name = "cardio_2")
    private String cardio2;

    @Column(name = "cardio_3")
    private String cardio3;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_session")
    private Date sessionDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable = false)
    private Date createDate;


    public Round(Integer id, WorkoutExercise workoutExercise, String weight, Integer reps, String descr, String cardio1, String cardio2, String cardio3) {
        this.id = id;
        this.workoutExercise = workoutExercise;
        this.weight = weight;
        this.reps = reps;
        this.descr = descr;
        this.cardio1 = cardio1;
        this.cardio2 = cardio2;
        this.cardio3 = cardio3;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @PrePersist
    public void setCreateDate() {
        this.createDate = new Date();
    }
}
