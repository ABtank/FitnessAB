package ru.abtank.fitnessab.servises;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.persist.entities.Mode;

import java.util.List;

public interface ModeService extends BasicService<ModeDto> {
    boolean checkIsUnique(String name, Integer id);

    List<ModeDto> findAll(Specification<Mode> spec);
}
