package ru.abtank.fitnessab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AdminUiApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(AdminUiApplication.class, args);

//		System.out.println("/////////////SerialVersionUID///////////////");
//		System.out.println("category "+ ObjectStreamClass.lookup(Category.class).getSerialVersionUID());
//		System.out.println("WorkoutExerciseId "+ObjectStreamClass.lookup(WorkoutExerciseId.class).getSerialVersionUID());
//		System.out.println("WorkoutExercise "+ObjectStreamClass.lookup(WorkoutExercise.class).getSerialVersionUID());
//		System.out.println("Character "+ObjectStreamClass.lookup(Character.class).getSerialVersionUID());
//		System.out.println("Exercise "+ObjectStreamClass.lookup(Exercise.class).getSerialVersionUID());
//		System.out.println("Mode "+ObjectStreamClass.lookup(Mode.class).getSerialVersionUID());
//		System.out.println("Round "+ObjectStreamClass.lookup(Round.class).getSerialVersionUID());
//		System.out.println("Type "+ObjectStreamClass.lookup(Type.class).getSerialVersionUID());
//		System.out.println("User "+ObjectStreamClass.lookup(User.class).getSerialVersionUID());
//		System.out.println("Role "+ObjectStreamClass.lookup(Role.class).getSerialVersionUID());
//		System.out.println("Workout "+ObjectStreamClass.lookup(Workout.class).getSerialVersionUID());
	}

}
