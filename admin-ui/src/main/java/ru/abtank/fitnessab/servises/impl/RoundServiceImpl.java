package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.RoundDto;
import ru.abtank.fitnessab.persist.entities.Round;
import ru.abtank.fitnessab.persist.repositories.RoundRepository;
import ru.abtank.fitnessab.servises.Mapper;
import ru.abtank.fitnessab.servises.RoundService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class RoundServiceImpl implements RoundService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoundServiceImpl.class);
    private Mapper mapper;
    private RoundRepository roundRepository;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setRoundRepository(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

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
        Round round = mapper.roundDtoToRound(o);
        round.setSessionDate(o.getSessionDate());
        roundRepository.save(round);
        return findById(round.getId());
    }

    @Override
    public long count() {
        return roundRepository.count();
    }
}
