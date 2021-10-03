package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workouts")
@Data
@NoArgsConstructor
public class Workout implements Serializable {
    private static final long SerialVersionUID = -209177205768159411L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Integer id;

    @Column(name = "workout", nullable = false)
    private String name;

    @Column(name = "descr")
    private String descr;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable = false)
    private Date createDate;


    public Workout(Integer id, String name, String descr, User creator) {
        this.id = id;
        this.name = name;
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
