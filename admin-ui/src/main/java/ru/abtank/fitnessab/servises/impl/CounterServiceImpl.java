package ru.abtank.fitnessab.servises.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.CounterDto;
import ru.abtank.fitnessab.persist.entities.Counter;
import ru.abtank.fitnessab.persist.repositories.CounterRepository;
import ru.abtank.fitnessab.servises.CounterService;

@Service
public class CounterServiceImpl implements CounterService {
    private CounterRepository counterRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setCounterRepository(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CounterDto allMyCount(Integer id) {
        System.out.println("allMyCount(Integer id) ="+id);
        Counter myCount = counterRepository.getMyCount(id);
        System.out.println(myCount);
        return modelMapper.map(myCount, CounterDto.class);
    }

    @Override
    public CounterDto allCount() {
        return modelMapper.map(counterRepository.getAllCount(), CounterDto.class);
    }
}