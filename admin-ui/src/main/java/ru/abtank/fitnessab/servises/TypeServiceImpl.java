package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.persist.entities.Type;
import ru.abtank.fitnessab.persist.repositories.TypeRepository;

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
    public TypeDto save(TypeDto o) {
        Type type = typeRepository.save(mapper.typeDtoToType(o));
        return mapper.typeToDto(type);
    }
}
