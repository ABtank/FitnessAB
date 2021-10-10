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

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AdminUiApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
        SpringApplication.run(AdminUiApplication.class, args);

        WorkoutComponent workout = new Workout("first workout");
        List<WorkoutComponent> listEx = new ArrayList<>();
        listEx.add(new WorkoutExercise("Подтягивания"));
        listEx.add(new WorkoutExercise("Отжимания"));
        listEx.add(new WorkoutExercise("Приседания"));
        listEx.forEach(exercise -> workout.addChild(exercise));

        List<WorkoutComponent> listRounds = new ArrayList<>();
        listRounds.add(new Round("70", 7));
        listRounds.add(new Round("70", 7));
        listRounds.add(new Round("70", 7));
        listRounds.add(new Round("70", 7));
        listRounds.add(new Round("70", 7));
        listRounds.add(new Round("55", 12));
        listRounds.add(new Round("55", 12));
        listRounds.add(new Round("55", 12));
        listRounds.add(new Round("55", 12));
        listRounds.add(new Round("55", 12));
        listRounds.add(new Round("78", 15));
        listRounds.add(new Round("78", 15));
        listRounds.add(new Round("78", 15));
        listRounds.add(new Round("78", 15));
        listRounds.add(new Round("78", 15));

        for (int i = 0; i < listEx.size(); i++) {
            for (int j = i * 5; j < (5 + i * 5); j++) {
                listEx.get(i).addChild(listRounds.get(j));
            }
        }

        System.out.println("-=Workout=-");
        System.out.println(workout.toString());
        System.out.println("Тоннаж за тренировку = " + workout.getSumWeigth());
        System.out.println("Всего упражнений = " + workout.geSumChild());
        int allRounds = 0;
        int allRips = 0;
        for (int i = 0; i < workout.getChild().size(); i++) {
            allRounds += workout.getChild().get(i).geSumChild();
            allRips += workout.getChild().get(i).getSumRep();
        }
        System.out.println("Всего подходов = " + allRounds);
        System.out.println("Всего повторов = " + allRips);
        System.out.println("Упражнения ");
        for (int i = 0; i < workout.getChild().size(); i++) {
            System.out.println(" #" + workout.getChild().get(i).getOrdinal() + " " + workout.getChild().get(i).getName());
            System.out.println("Тоннаж: " + workout.getChild().get(i).getSumWeigth());
            System.out.println("Всего подходов: " + workout.getChild().size());
            System.out.println("Всего повторов: " + workout.getChild().get(i).getSumRep());
            System.out.println("1ПМ = " + workout.getChild().get(i).getOneRepMax());
        }

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
