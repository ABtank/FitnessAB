package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "types")
@Data
@NoArgsConstructor
public class Type implements Serializable {
    private static final long SerialVersionUID = -9165236563057600523L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String name;

    @Column(name = "descr")
    private String descr;

    public Type(Integer id, @NotBlank String name, String descr) {
        this.id = id;
        this.name = name;
        this.descr = descr;
    }
}
