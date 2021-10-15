package ru.abtank.fitnessab.servises;

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
    private final static Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

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

    public User userDtoToCreator(CreatorDto creatorDto) {
        LOGGER.info("-=userDtoToCreator(CreatorDto creatorDto)=-");
        return new User(
                creatorDto.getId(),
                creatorDto.getLogin(),
                creatorDto.getEmail()
        );
    }

    public User creatorDtoToUser(CreatorDto creatorDto) {
        LOGGER.info("-=creatorDtoToUser(CreatorDto creatorDto)=-");
        return new User(
                creatorDto.getId(),
                creatorDto.getLogin(),
                creatorDto.getEmail());
    }

    public CreatorDto userToCreatorDto(User user) {
        LOGGER.info("-=userToCreatorDto(User user)=-");
        return new CreatorDto(
                user.getId(),
                user.getLogin(),
                user.getEmail()
        );
    }

    public Role roleDtoToRole(RoleDto roleDto) {
        LOGGER.info("-=roleDtoToRole(RoleDto roleDto)=-");
        return new Role(roleDto.getId(), roleDto.getName());
    }

    public RoleDto roleToDto(Role role) {
        LOGGER.info("-=roleToDto(Role role)=-");
        return new RoleDto(role.getId(), role.getName());
    }

    public Type typeDtoToType(TypeDto typeDto) {
        LOGGER.info("-=typeDtoToType(TypeDto typeDto)=-");
        return new Type(typeDto.getId(), typeDto.getName(), typeDto.getDescr());
    }

    public TypeDto typeToDto(Type type) {
        LOGGER.info("-=typeToDto(Type type)=-");
        return new TypeDto(type.getId(), type.getName(), type.getDescr());
    }

    public Mode modeDtoToMode(ModeDto modeDto) {
        LOGGER.info("-=odeDtoToMode(ModeDto modeDto)=-");
        return new Mode(modeDto.getId(), modeDto.getName(), modeDto.getIsStart(), modeDto.getDescr());
    }

    public ModeDto modeToDto(Mode mode) {
        LOGGER.info("-=modeToDto(Mode mode)=-");
        return new ModeDto(mode.getId(), mode.getName(), mode.getIsStart(), mode.getDescr());
    }


    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        LOGGER.info("-=categoryDtoToCategory(CategoryDto categoryDto)=-");
        return new Category(categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getDescr(),
                userDtoToCreator(categoryDto.getCreator()));
    }

    public CategoryDto categoryToDto(Category category) {
        LOGGER.info("-=categoryToDto(Category category)=-");
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescr(),
                userToCreatorDto(category.getCreator())
        );
    }

    public ExerciseDto exerciseToDto(Exercise exercise) {
        LOGGER.info("-=exerciseToDto(Exercise exercise)=-");
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescr(),
                exercise.getIsCardio(),
                exercise.getCardioName1(),
                exercise.getCardioName2(),
                exercise.getCardioName3(),
                typeToDto(exercise.getType()),
                userToCreatorDto(exercise.getCreator()),
                categoryToDto(exercise.getCategory())
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
                categoryDtoToCategory(exerciseDto.getCategory()),
                typeDtoToType(exerciseDto.getType()),
                userDtoToCreator(exerciseDto.getCreator())
        );
    }

    public WorkoutDto workoutToDto(Workout workout) {
        LOGGER.info("-=workoutToDto(Workout workout) =-");
        return new WorkoutDto(
                workout.getId(),
                workout.getName(),
                workout.getDescr(),
                userToCreatorDto(workout.getCreator())
        );
    }

    public Workout workoutDtoToWorkout(WorkoutDto w) {
        LOGGER.info("-=workoutDtoToWorkout(WorkoutDto w)=-");
        return new Workout(
                w.getId(),
                w.getName(),
                w.getDescr(),
                creatorDtoToUser(w.getCreator())
        );
    }

    public WorkoutExerciseDto workoutExerciseToDto(WorkoutExercise we){
        LOGGER.info("-=workoutExerciseToDto(WorkoutExercise we)=-");
        return new WorkoutExerciseDto(
                we.getId(),
                modeToDto(we.getMode()),
                we.getOrdinal(),
                we.getDescr(),
                new WorkoutDtoId(we.getWorkout().getId()),
                new ExerciseDtoId(we.getExercise().getId()));
    }

    public WorkoutExercise workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto we) {
        LOGGER.info("-=workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto we)=-");
        return new WorkoutExercise(
                we.getId(),
                new Workout(we.getWorkout().getId()),
                new Exercise(we.getExercise().getId()),
                modeDtoToMode(we.getMode()),
                we.getOrdinal(),
                we.getDescr());
    }

    public RoundDto roundToDto(Round round){
        LOGGER.info("-=roundToDto(Round round)=-");
        return new RoundDto(
                round.getId(),
                new WorkoutDtoId(round.getWorkout().getId()),
                new ExerciseDtoId(round.getExercise().getId()),
                round.getWeight(),
                round.getReps(),
                round.getDescr(),
                round.getCardio1(),
                round.getCardio2(),
                round.getCardio3()
        );
    }

    public Round roundDtoToRound(RoundDto roundDto){
        LOGGER.info("-=roundDtotoRound(RoundDto roundDto)=-");
        return new Round(
                roundDto.getId(),
                new Workout(roundDto.getWorkout().getId()),
                new Exercise(roundDto.getExercise().getId()),
                roundDto.getWeight(),
                roundDto.getReps(),
                roundDto.getDescr(),
                roundDto.getCardio1(),
                roundDto.getCardio2(),
                roundDto.getCardio3()
        );
    }


}
