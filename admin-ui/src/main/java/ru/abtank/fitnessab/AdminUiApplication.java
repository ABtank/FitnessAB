package ru.abtank.fitnessab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.abtank.fitnessab.composite.Round;
import ru.abtank.fitnessab.composite.Workout;
import ru.abtank.fitnessab.composite.WorkoutComponent;
import ru.abtank.fitnessab.composite.WorkoutExercise;

@SpringBootApplication
public class AdminUiApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(AdminUiApplication.class, args);

		WorkoutComponent workout = new Workout("ferst workout");
		WorkoutComponent we1 = new WorkoutExercise("Подтягивания");
		WorkoutComponent we2 = new WorkoutExercise("Отжимания");
		WorkoutComponent we3 = new WorkoutExercise("Приседания");

		workout.addChild(we1);
		workout.addChild(we2);
		workout.addChild(we3);

		WorkoutComponent r11 = new Round("70", 7);
		WorkoutComponent r12 = new Round("70", 7);
		WorkoutComponent r13 = new Round("70", 7);
		WorkoutComponent r14 = new Round("70", 7);
		WorkoutComponent r15 = new Round("70", 7);
		WorkoutComponent r21 = new Round("55", 12);
		WorkoutComponent r22 = new Round("55", 12);
		WorkoutComponent r23 = new Round("55", 12);
		WorkoutComponent r24 = new Round("55", 12);
		WorkoutComponent r25 = new Round("55", 12);
		WorkoutComponent r31 = new Round("78", 15);
		WorkoutComponent r32 = new Round("78", 15);
		WorkoutComponent r33 = new Round("78", 15);
		WorkoutComponent r34 = new Round("78", 15);
		WorkoutComponent r35 = new Round("78", 15);

		we1.addChild(r11);
		we1.addChild(r12);
		we1.addChild(r13);
		we1.addChild(r14);
		we1.addChild(r15);
		we2.addChild(r21);
		we2.addChild(r22);
		we2.addChild(r23);
		we2.addChild(r24);
		we2.addChild(r25);
		we3.addChild(r31);
		we3.addChild(r32);
		we3.addChild(r33);
		we3.addChild(r34);
		we3.addChild(r35);

		System.out.println("-=Workout=-");
        System.out.println(workout.toString());
		System.out.println("Тоннаж за тренировку = "+workout.getSumWeigth());
		System.out.println("Всего упражнений = "+workout.geSumChild());
		System.out.println("Упражнения ");
		int r = 0;
		for (int i = 0; i < workout.getChild().size(); i++) {
			System.out.println(" #"+workout.getChild().get(i).getOrdinal()+" "+workout.getChild().get(i).getName());
			System.out.println(workout.getChild().get(i).getSumWeigth());
			System.out.println(workout.getChild().get(i).getSumRep());
			System.out.println(workout.getChild().get(i).getOneRepMax());
			r+=workout.getChild().get(i).geSumChild();
		}
		System.out.println("Всего подходов = "+ r);

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
