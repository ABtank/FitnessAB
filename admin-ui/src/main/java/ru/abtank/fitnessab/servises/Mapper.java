package ru.abtank.fitnessab.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.abtank.fitnessab.dto.*;
import ru.abtank.fitnessab.persist.entities.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Component
public class Mapper {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto userToDto(User user) {
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
        return new User(userCreationDTO.getId(),
                userCreationDTO.getLogin(),
                passwordEncoder.encode(userCreationDTO.getPassword()),
                userCreationDTO.getEmail()
                );
    }

    public User userDtoToCreator(CreatorDto creatorDto){
        return new User(
                creatorDto.getId(),
                creatorDto.getLogin(),
                creatorDto.getEmail()
        );
    }

    public User creatorDtoToUser(CreatorDto creatorDto){
        return new User(
                creatorDto.getId(),
                creatorDto.getLogin(),
                creatorDto.getEmail());
    }

    public CreatorDto userToCreatorDto(User user){
        return new CreatorDto(
                user.getId(),
                user.getLogin(),
                user.getEmail()
        );
    }

    public Role roleDtoToRole(RoleDto roleDto) {
        return new Role(roleDto.getId(), roleDto.getName());
    }

    public RoleDto roleToDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }

    public Type typeDtoToType(TypeDto typeDto) {
        return new Type(typeDto.getId(), typeDto.getName(), typeDto.getDescr());
    }

    public TypeDto typeToDto(Type type) {
        return new TypeDto(type.getId(), type.getName(), type.getDescr());
    }

    public Mode modeDtoToMode(ModeDto modeDto) {
        return new Mode(modeDto.getId(), modeDto.getName(), modeDto.getIsStart(), modeDto.getDescr());
    }

    public ModeDto modeToDto(Mode mode) {
        return new ModeDto(mode.getId(), mode.getName(), mode.getIsStart(), mode.getDescr());
    }


    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getDescr(),
                userDtoToCreator(categoryDto.getCreator()));
    }

    public CategoryDto categoryToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescr(),
                userToCreatorDto(category.getCreator())
        );
    }

    public ExerciseDto exerciseToDto(Exercise exercise) {
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

    public WorkoutDto workoutToDto(Workout workout){
        return new WorkoutDto(
                workout.getId(),
                workout.getName(),
                workout.getDescr(),
                userToCreatorDto(workout.getCreator())
        );
    }
    public Workout workoutDtoToWorkout(WorkoutDto w){
        return new Workout(
                w.getId(),
                w.getName(),
                w.getDescr(),
                creatorDtoToUser(w.getCreator())
        );
    }

    public WorkoutExerciseDto workoutExerciseToDto (WorkoutExercise we){
        return new WorkoutExerciseDto(
                we.getId(),
                modeToDto(we.getMode()),
                we.getOrdinal(),
                we.getDescr(),
                userToCreatorDto(we.getCreator()));
    }

    public WorkoutExercise workoutExerciseDtoToWorkoutExercise (WorkoutExerciseDto we){
        return new WorkoutExercise(
                we.getId(),
                modeDtoToMode(we.getMode()),
                we.getOrdinal(),
                we.getDescr(),
                creatorDtoToUser(we.getCreator())
        );
    }


}
