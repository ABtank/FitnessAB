package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.ExerciseDto;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.persist.entities.Exercise;
import ru.abtank.fitnessab.persist.entities.Workout;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;
import ru.abtank.fitnessab.persist.repositories.ExerciseRepository;
import ru.abtank.fitnessab.persist.repositories.ModeRepository;
import ru.abtank.fitnessab.persist.repositories.WorkoutExerciseRepository;
import ru.abtank.fitnessab.persist.repositories.WorkoutRepository;
import ru.abtank.fitnessab.persist.repositories.specifications.ExerciseSpecification;
import ru.abtank.fitnessab.persist.repositories.specifications.WorkoutExerciseSpecification;
import ru.abtank.fitnessab.servises.Mapper;
import ru.abtank.fitnessab.servises.WorkoutExerciseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutExerciseServiceImpl.class);
    private WorkoutExerciseRepository workoutExerciseRepository;
    private WorkoutRepository workoutRepository;
    private ModeRepository modeRepository;
    private ExerciseRepository exerciseRepository;
    private ModelMapper modelMapper;
    private Mapper mapper;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWorkoutExerciseRepository(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Autowired
    public void setWorkoutRepository(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<WorkoutExerciseDto> findAll() {
        return workoutExerciseRepository.findAll().stream().map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class)).collect(toList());
    }

    @Override
    public Optional<WorkoutExerciseDto> findById(Integer id) {
        return workoutExerciseRepository.findById(id).map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class));
    }

    @Override
    @Deprecated
    public Optional<WorkoutExerciseDto> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {
        workoutExerciseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all workoutExercise");
    }

    @Override
    public Optional<WorkoutExerciseDto> save(WorkoutExerciseDto o) {
        LOGGER.info("-=Optional<WorkoutExerciseDto> save(WorkoutExerciseDto o)=-");
        WorkoutExercise we = mapper.workoutExerciseDtoToWorkoutExercise(o);
        we.setExercise(exerciseRepository.getById(o.getExerciseId()));
        we.setWorkout(workoutRepository.getById(o.getWorkoutId()));
        if (o.getModeId() != null && o.getModeId() > 0) {
            we.setMode(modeRepository.getById(o.getModeId()));
        }
        if (we.getId() != null) {

        } else {
            int count = count(o.getWorkoutId());
            we.setOrdinal((count > 0) ? ++count : 1);
        }
        we = workoutExerciseRepository.save(we);
        return workoutExerciseRepository.findById(we.getId()).map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class));
    }

    @Override
    public Page<WorkoutExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest) {
        LOGGER.info("-=Page<WorkoutExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest)=-");
        Specification<WorkoutExercise> spec = WorkoutExerciseSpecification.trueLiteral();
        if (!params.isEmpty() && params.containsKey("workoutId") && !params.get("workoutId").isBlank()) {
            LOGGER.info("spec=Page<WorkoutExerciseDto>");
            Workout workout = workoutRepository.findById(Integer.valueOf(params.get("workoutId"))).orElseThrow(NotFoundException::new);
            spec = spec.and(WorkoutExerciseSpecification.workoutEquals(workout));
        }
        List<WorkoutExercise> all = workoutExerciseRepository.findAll(spec);
         return workoutExerciseRepository.findAll(spec, pageRequest).map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class));
    }

    @Override
    public long count() {
        return workoutExerciseRepository.count();
    }

    @Override
    public int count(Integer workoutId) {
        Workout workout = workoutRepository.getById(workoutId);
        Specification<WorkoutExercise> spec = WorkoutExerciseSpecification.trueLiteral();
        spec = spec.and(WorkoutExerciseSpecification.workoutEquals(workout));
        return workoutExerciseRepository.findAll(spec).size();
    }
}
