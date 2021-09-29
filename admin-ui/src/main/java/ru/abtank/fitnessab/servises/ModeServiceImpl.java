package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.persist.entities.Mode;
import ru.abtank.fitnessab.persist.repositories.ModeRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class ModeServiceImpl implements ModeService {
    private ModeRepository modeRepository;

    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Override
    public Set<ModeDto> findAllDto() {
        return modeRepository.findAll().stream().map(ModeDto::new).collect(Collectors.toSet());
    }
    @Override
    public Optional<ModeDto> findByIdDto(Integer id) {
        return modeRepository.findById(id).map(ModeDto::new);
    }

    @Override
    public Optional<ModeDto> findByName(String name) {
        return modeRepository.findByName(name).map(ModeDto::new);
    }

    @Override
    public void deleteById(Integer id) {
        modeRepository.deleteById(id);
    }

    @Override
    public void save(ModeDto modeDto) {
        Mode mode = new Mode();
        modeRepository.save(mode);
    }
}
