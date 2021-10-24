package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.persist.entities.Mode;
import ru.abtank.fitnessab.persist.repositories.ModeRepository;
import ru.abtank.fitnessab.persist.repositories.specifications.ModeSpecification;
import ru.abtank.fitnessab.servises.ModeService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class ModeServiceImpl implements ModeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ModeServiceImpl.class);
    private ModeRepository modeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Override
    public List<ModeDto> findAll() {
        return modeRepository.findAll().stream().map(obj -> modelMapper.map(obj, ModeDto.class)).collect(toList());
    }

    @Override
    public Optional<ModeDto> findById(Integer id) {
        return modeRepository.findById(id).map(obj -> modelMapper.map(obj, ModeDto.class));
    }

    @Override
    public Optional<ModeDto> findByName(String name) {
        return modeRepository.findByName(name).map(obj -> modelMapper.map(obj, ModeDto.class));
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
    public Optional<ModeDto> save(ModeDto modeDto) {
        Mode mode = modeRepository.save(modelMapper.map(modeDto, Mode.class));
        return findById(mode.getId());
    }

    @Override
    public List<ModeDto> findAll(Specification<Mode> spec) {
        return modeRepository.findAll(spec).stream().map(obj -> modelMapper.map(obj, ModeDto.class)).collect(toList());
    }

    @Override
    public boolean checkIsUnique(String name, Integer id) {
        Specification<Mode> spec = ModeSpecification.trueLiteral();
        spec = spec.and(ModeSpecification.findByName(name));
        if (id != null) {
            spec = spec.and(ModeSpecification.idNotEqual(id));
        }
        List<ModeDto> chekEquals = findAll(spec);
        return chekEquals.isEmpty();
    }

    @Override
    public long count() {
        return modeRepository.count();
    }
}
