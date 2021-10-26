package ru.abtank.fitnessab.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;

import java.util.List;
import java.util.Map;

public interface WorkoutExerciseService extends BasicService<WorkoutExerciseDto> {
    Page<WorkoutExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest);
}
