package ru.abtank.fitnessab;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.persist.entities.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeDtoUnitTest {
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertTypeEntityToTypeDto_thenCorrect() {
        Type type = new Type();
        type.setId(1);
        type.setName("dafffega");
        type.setDescr("www.test.com");

        TypeDto typeDto = modelMapper.map(type, TypeDto.class);
        assertEquals(type.getId(), typeDto.getId());
        assertEquals(type.getName(), typeDto.getName());
        assertEquals(type.getDescr(), typeDto.getDescr());
    }

    @Test
    public void whenConvertTypeDtoToTypeEntity_thenCorrect() {
        TypeDto typeDto = new TypeDto();
        typeDto.setId(1);
        typeDto.setName("Adewhfsdnve");
        typeDto.setDescr("www.test.com");

        Type type = modelMapper.map(typeDto, Type.class);
        assertEquals(typeDto.getId(), type.getId());
        assertEquals(typeDto.getName(), type.getName());
        assertEquals(typeDto.getDescr(), type.getDescr());
    }
}
