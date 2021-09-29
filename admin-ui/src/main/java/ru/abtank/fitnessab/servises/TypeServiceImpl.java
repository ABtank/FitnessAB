package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.persist.entities.Type;
import ru.abtank.fitnessab.persist.repositories.TypeRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class TypeServiceImpl implements TypeService {
    private TypeRepository typeRepository;

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Set<TypeDto> findAllDto() {
        return typeRepository.findAll().stream().map(TypeDto::new).collect(Collectors.toSet());
    }

    @Override
    public Optional<TypeDto> findByIdDto(Integer id) {
        return typeRepository.findById(id).map(TypeDto::new);
    }

    @Override
    public TypeDto findByIdTypeDto(Integer id) {
        Optional<Type> optionalType = typeRepository.findById(id);
        if (!optionalType.isEmpty()) {
            Type type = optionalType.get();
            return TypeDto.builder(type.getName())
                    .withDescr(type.getDescr())
                    .withId(type.getId())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public Optional<TypeDto> findByName(String name) {
        return typeRepository.findByName(name).map(TypeDto::new);
    }

    @Override
    public void deleteById(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    public void save(TypeDto typeDto) {
        typeRepository.save(
                Type.builder(typeDto.getName())
                        .withDescr(typeDto.getDescr())
                        .withId(typeDto.getId())
                        .build()
        );
    }
}
