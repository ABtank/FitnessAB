package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.WorkoutDto;
import ru.abtank.fitnessab.persist.entities.Workout;
import ru.abtank.fitnessab.persist.repositories.UserRepository;
import ru.abtank.fitnessab.persist.repositories.WorkoutRepository;
import ru.abtank.fitnessab.servises.Mapper;
import ru.abtank.fitnessab.servises.WorkoutService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutServiceImpl.class);
    private WorkoutRepository workoutRepository;
    private UserRepository userRepository;
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
    public void setWorkoutRepository(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(obj -> modelMapper.map(obj, WorkoutDto.class)).collect(toList());
    }

    @Override
    public Optional<WorkoutDto> findById(Integer id) {
        return workoutRepository.findById(id).map(obj -> modelMapper.map(obj, WorkoutDto.class));
    }

    @Override
    public Optional<WorkoutDto> findByName(String name) {
        return workoutRepository.findByName(name).map(obj -> modelMapper.map(obj, WorkoutDto.class));
    }

    @Override
    public void deleteById(Integer id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all Workouts");
    }

    @Override
    public Optional<WorkoutDto> save(WorkoutDto o) {
        Workout workout = mapper.workoutDtoToWorkout(o);
        workout.setCreator(userRepository.findByLogin(o.getCreatorLogin()).get());
        workoutRepository.save(workout);
        return findById(workout.getId());
    }

    @Override
    public long count() {
        return workoutRepository.count();
    }
}
