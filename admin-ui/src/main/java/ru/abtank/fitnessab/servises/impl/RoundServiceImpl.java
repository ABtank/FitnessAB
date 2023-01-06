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
import ru.abtank.fitnessab.servises.Mapper;
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
    private final Mapper mapper;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<RoundDto> findAll() {
        return roundRepository.findAll().stream().map(mapper::roundToDto).collect(toList());
    }

    @Override
    public Optional<RoundDto> findById(Integer id) {
        return roundRepository.findById(id).map(mapper::roundToDto);
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
        if(round.getId() == null) {
            Optional<Round> lustRound = roundRepository
                    .findFirstByWorkoutExerciseOrderBySessionDateDesc(round.getWorkoutExercise());
            if (lustRound.isPresent()) {
                Instant instantNow = Instant.now().plus(3, ChronoUnit.HOURS);
                Date sessionDate = lustRound.get().getSessionDate();
                Instant dtEndSession = sessionDate.toInstant().plus(6, ChronoUnit.HOURS);
                if (dtEndSession.isAfter(instantNow)) {
                    round.setSessionDate(sessionDate);
                }
            }
        } else {
            Round r = roundRepository.getById(o.getId());
            round.setSessionDate(r.getSessionDate());
            round.setCreateDate(r.getCreateDate());
        }
        return findById(roundRepository.save(round).getId());
    }

    @Override
    public long count() {
        return roundRepository.count();
    }
}
