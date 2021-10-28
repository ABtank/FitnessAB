package ru.abtank.fitnessab.persist.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "modes")
@Data
@NoArgsConstructor
@Builder
public class Mode implements Serializable {
    private static final long SerialVersionUID = 3526649283349890875L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mode_id")
    private Integer id;

    @Column(name = "mode", nullable = false, unique = true)
    private String name;

    @Column(name = "is_start")
    private Boolean isStart;

    @Column(name = "descr")
    private String descr;

    public Mode(Integer id, String name, Boolean isStart, String descr) {
        this.id = id;
        this.name = name;
        this.isStart = isStart;
        this.descr = descr;
    }
}
