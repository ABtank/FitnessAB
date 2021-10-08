package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
public class Exercise implements Serializable {
    private static final long SerialVersionUID = -54354687931379394L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Integer id;

    @Column(name = "exercise", unique = true, nullable = false)
    private String name;

    @Column(name = "descr")
    private String descr;

    @Column(name = "is_cardio", nullable = false)
    private Boolean isCardio;

    @Column(name = "cardio_name_1")
    private String cardioName1;

    @Column(name = "cardio_name_2")
    private String cardioName2;

    @Column(name = "cardio_name_3")
    private String cardioName3;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable = false, nullable = false)
    private Date createDate;


    public Exercise(Integer id, String name, String descr, Boolean isCardio, String cardioName1, String cardioName2, String cardioName3, Category category, Type type, User creator) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.isCardio = isCardio;
        this.cardioName1 = cardioName1;
        this.cardioName2 = cardioName2;
        this.cardioName3 = cardioName3;
        this.category = category;
        this.type = type;
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
