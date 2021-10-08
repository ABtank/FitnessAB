package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.persist.entities.Mode;
import ru.abtank.fitnessab.persist.repositories.ModeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@NoArgsConstructor
public class ModeServiceImpl implements ModeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ModeServiceImpl.class);
    private Mapper mapper;
    private ModeRepository modeRepository;

    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ModeDto> findAll() {
        return modeRepository.findAll().stream().map(mapper::modeToDto).collect(toList());
    }

    @Override
    public Optional<ModeDto> findById(Integer id) {
        return modeRepository.findById(id).map(mapper::modeToDto);
    }

    @Override
    public Optional<ModeDto> findByName(String name) {
        return modeRepository.findByName(name).map(mapper::modeToDto);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all modes");
    }

    @Override
    public void deleteById(Integer id) {
        modeRepository.deleteById(id);
    }

    @Override
    public ModeDto save(ModeDto modeDto) {
        Mode mode = modeRepository.save(mapper.modeDtoToMode(modeDto));
        return mapper.modeToDto(mode);
    }
}
