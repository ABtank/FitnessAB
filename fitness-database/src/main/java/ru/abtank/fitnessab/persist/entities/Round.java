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
    @Column(name = "set_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

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

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable = false)
    private Date createDate;


    public Date getCreateDate() {
        return createDate;
    }

    @PrePersist
    public void setCreateDate() {
        this.createDate = new Date();
    }
}
