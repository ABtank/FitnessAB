package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.fitnessab.persist.entities.Type;

@Data
@NoArgsConstructor
public class TypeDto {
    private Integer id;
    private String name;
    private String descr;

    public TypeDto(Type type) {
        this.id = type.getId();
        this.name = type.getName();
        this.descr = type.getDescr();
    }

    public TypeDto(String name) {
        this.name = name;
    }

    public static TypeDtoBuilder builder(String name){
        return new TypeDtoBuilder(name);
    }

    public static class TypeDtoBuilder {
        private final TypeDto typeDto;

        private TypeDtoBuilder(String name){
            typeDto = new TypeDto(name);
        }

        public TypeDtoBuilder withDescr(String descr){
            typeDto.descr = descr;
            return this;
        }

        public TypeDtoBuilder withId(Integer id){
            typeDto.id = id;
            return this;
        }

        public TypeDto build(){
            return typeDto;
        }
    }
}
