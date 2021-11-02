package ru.abtank.fitnessab.servises;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.abtank.fitnessab.dto.*;
import ru.abtank.fitnessab.persist.entities.*;

import static java.util.stream.Collectors.toList;


@Component
public class Mapper {
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto userToDto(User user) {
        LOGGER.info("-=userToDto(User user)=-");
        return new UserDto(user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(toList()),
                user.getCreateDate(),
                user.getModifyDate());
    }

    public User userCreationDTOtoUser(UserCreationDto userCreationDTO) {
        LOGGER.info("-=userCreationDTOtoUser(UserCreationDto userCreationDTO)=-");
        return new User(userCreationDTO.getId(),
                userCreationDTO.getLogin(),
                passwordEncoder.encode(userCreationDTO.getPassword()),
                userCreationDTO.getEmail()
        );
    }

    public UserCreationDto userToUserCreationDto(User user) {
        LOGGER.info("-=userCreationDTOtoUser(UserCreationDto userCreationDTO)=-");
        return new UserCreationDto(user.getId(),
                user.getLogin(),
                null,
                null,
                user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(toList())
        );
    }

    public Exercise exerciseDtoToExercise(ExerciseDto exerciseDto) {
        LOGGER.info("-=exerciseDtoToExercise(ExerciseDto exerciseDto)=-");
        return new Exercise(
                exerciseDto.getId(),
                exerciseDto.getName(),
                exerciseDto.getDescr(),
                exerciseDto.getIsCardio(),
                exerciseDto.getCardioName1(),
                exerciseDto.getCardioName2(),
                exerciseDto.getCardioName3(),
                new Category(),
                new Type(),
                modelMapper.map(exerciseDto.getCreator(), User.class)
        );
    }

    public WorkoutExercise workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto we) {
        LOGGER.info("-=workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto we)=-");
        return new WorkoutExercise(
                we.getId(),
                new Workout(),
                new Exercise(),
                we.getOrdinal(),
                we.getDescr());
    }

    public Workout workoutDtoToWorkout(WorkoutDto w) {
        LOGGER.info("-=workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto we)=-");
        return new Workout(
                w.getId(),
                w.getName(),
                w.getDescr(),
                new User());
    }

    public RoundDto roundToDto(Round round) {
        LOGGER.info("-=roundToDto(Round round)=-");
        return modelMapper.map(round, RoundDto.class);
//        return new RoundDto(
//                round.getId(),
//                new WorkoutDtoId(round.getWorkout().getId()),
//                new ExerciseDtoId(round.getExercise().getId()),
//                round.getWeight(),
//                round.getReps(),
//                round.getDescr(),
//                round.getCardio1(),
//                round.getCardio2(),
//                round.getCardio3()
//        );
    }

    public Round roundDtoToRound(RoundDto roundDto) {
        LOGGER.info("-=roundDtotoRound(RoundDto roundDto)=-");
        return modelMapper.map(roundDto, Round.class);
//        return new Round(
//                roundDto.getId(),
//                new Workout(roundDto.getWorkout().getId()),
//                new Exercise(roundDto.getExercise().getId()),
//                roundDto.getWeight(),
//                roundDto.getReps(),
//                roundDto.getDescr(),
//                roundDto.getCardio1(),
//                roundDto.getCardio2(),
//                roundDto.getCardio3()
//        );
    }


}
