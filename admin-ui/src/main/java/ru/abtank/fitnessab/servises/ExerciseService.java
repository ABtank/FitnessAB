package ru.abtank.fitnessab.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.abtank.fitnessab.dto.ExerciseDto;

import java.util.Map;

public interface ExerciseService extends BasicService<ExerciseDto> {
    Page<ExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest);
}
