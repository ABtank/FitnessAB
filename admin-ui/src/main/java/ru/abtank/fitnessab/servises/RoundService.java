package ru.abtank.fitnessab.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.abtank.fitnessab.dto.RoundDto;

import java.util.Map;


public interface RoundService extends BasicService<RoundDto> {
    Page<RoundDto> findAll(Map<String, String> params, PageRequest pageRequest);
}
