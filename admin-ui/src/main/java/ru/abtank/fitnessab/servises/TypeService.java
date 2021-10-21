package ru.abtank.fitnessab.servises;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.persist.entities.Type;

import java.util.List;

public interface TypeService extends BasicService<TypeDto> {
    boolean checkIsUnique(String name, Integer id);

    List<TypeDto> findAll(Specification<Type> spec);
}
