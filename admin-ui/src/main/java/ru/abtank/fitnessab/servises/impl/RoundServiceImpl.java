package ru.abtank.fitnessab.servises.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.RoundDto;
import ru.abtank.fitnessab.persist.entities.Round;
import ru.abtank.fitnessab.persist.repositories.ExerciseRepository;
import ru.abtank.fitnessab.persist.repositories.RoundRepository;
import ru.abtank.fitnessab.persist.repositories.WorkoutExerciseRepository;
import ru.abtank.fitnessab.persist.repositories.WorkoutRepository;
import ru.abtank.fitnessab.servises.RoundService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Data
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoundServiceImpl.class);
    private final RoundRepository roundRepository;
    private final ModelMapper modelMapper;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<RoundDto> findAll() {
        return roundRepository.findAll().stream().map(obj -> modelMapper.map(obj, RoundDto.class)).collect(toList());
    }

    @Override
    public Optional<RoundDto> findById(Integer id) {
        return roundRepository.findById(id).map(obj -> modelMapper.map(obj, RoundDto.class));
    }

    @Override
    @Deprecated
    public Optional<RoundDto> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {
        roundRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all Rounds");
    }

    @Override
    public Optional<RoundDto> save(RoundDto o) {
        Round round = modelMapper.map(o, Round.class);
        Optional<Round> lustRound = roundRepository.findFirstByWorkoutExerciseOrderBySessionDateDesc(o.getWorkoutExerciseId());
        if(lustRound.isPresent()){
            Instant sessionDate = lustRound.get().getSessionDate().toInstant();
            Instant dtEndSession = sessionDate.plus(3, ChronoUnit.HOURS);
            if(dtEndSession.isAfter(Instant.now())){
                round.setSessionDate(new Date());
            }
        }
        roundRepository.save(round);
        return findById(round.getId());
    }

    @Override
    public long count() {
        return roundRepository.count();
    }
}
