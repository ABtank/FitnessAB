package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.persist.entities.Mode;
import ru.abtank.fitnessab.persist.entities.Type;
import ru.abtank.fitnessab.persist.repositories.TypeRepository;
import ru.abtank.fitnessab.persist.repositories.specifications.ModeSpecification;
import ru.abtank.fitnessab.persist.repositories.specifications.TypeSpecification;
import ru.abtank.fitnessab.servises.Mapper;
import ru.abtank.fitnessab.servises.TypeService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TypeServiceImpl.class);
    private TypeRepository typeRepository;
    private Mapper mapper;

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<TypeDto> findAll() {
        return typeRepository.findAll().stream().map(mapper::typeToDto).collect(toList());
    }

    @Override
    public Optional<TypeDto> findById(Integer id) {
        return typeRepository.findById(id).map(mapper::typeToDto);
    }

    @Override
    public Optional<TypeDto> findByName(String name) {
        return typeRepository.findByName(name).map(mapper::typeToDto);
    }

    @Override
    public void deleteById(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all Types");
    }

    @Override
    public Optional<TypeDto> save(TypeDto o) {
        Type type = typeRepository.save(mapper.typeDtoToType(o));
        return findById(type.getId());
    }

    @Override
    public boolean checkIsUnique(String name, Integer id) {
        Specification<Type> spec = TypeSpecification.trueLiteral();
        spec = spec.and(TypeSpecification.findByName(name));
        if (id != null) {
            spec = spec.and(TypeSpecification.idNotEqual(id));
        }
        List<TypeDto> chekEquals = findAll(spec);
        return chekEquals.isEmpty();
    }

    @Override
    public List<TypeDto> findAll(Specification<Type> spec) {
        return typeRepository.findAll(spec).stream().map(mapper::typeToDto).collect(toList());
    }

    @Override
    public long count() {
        return typeRepository.count();
    }
}
