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

    public Type(String name) {
        this.name = name;
    }

    public static TypeBuilder builder(String name){
        return new TypeBuilder(name);
    }

    public static class TypeBuilder {
        private final Type type;

        private TypeBuilder(String name){
            type = new Type(name);
        }

        public TypeBuilder withDescr(String descr){
            type.descr = descr;
            return this;
        }

        public TypeBuilder withId(Integer id){
            type.id = id;
            return this;
        }

        public Type build(){
            return type;
        }
    }
}
