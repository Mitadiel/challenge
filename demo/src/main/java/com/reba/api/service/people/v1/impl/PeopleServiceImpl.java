package com.reba.api.service.people.v1.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.reba.api.domain.People;
import com.reba.api.dto.people.v1.PeopleDto;
import com.reba.api.dto.people.v1.PeopleMapper;
import com.reba.api.dto.people.v1.PeopleRequest;
import com.reba.api.repository.PeopleRepository;
import com.reba.api.service.people.v1.PeopleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;
    private final ModelMapper modelMapper;

    public PeopleServiceImpl(PeopleRepository peopleRepository, PeopleMapper peopleMapper, ModelMapper modelMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public PeopleDto save(PeopleRequest peopleRequest) {
        log.trace("save -> People: " + peopleRequest);
        if (!validateBirthDate(peopleRequest.getBirthDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Must be > 18 years old.");
        }
        if(validateDuplicate(peopleRequest)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Can not save duplicate people.");
        }
        People people = this.peopleRepository.save(peopleMapper.toModel(peopleRequest));
        return peopleMapper.toDto(people);

    }

    @Override
    public List<PeopleDto> findAll() {
        log.trace("findAll -> People");
        List<People> peopleList = this.peopleRepository.findAll();
        List<PeopleDto> peopleDtoList = new ArrayList<>();
        peopleList.forEach(student -> peopleDtoList.add(modelMapper.map(student, PeopleDto.class)));
        return peopleDtoList;
    }

    @Override
    public PeopleDto findById(Long id) {
        log.trace("findById -> People " + id);

        Optional<People> optionalPeople = this.peopleRepository.findById(id);
        if (optionalPeople.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "People with id " + id + " not found");
        }
        People people = optionalPeople.get();
        return this.peopleMapper.toDto(people);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.trace("delete -> People " + id);
        Optional<People> optionalPeople = this.peopleRepository.findById(id);
        if (optionalPeople.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "People with id " + id + " not found");
        }
        this.peopleRepository.deleteById(id);

    }

    @Transactional
    @Override
    public PeopleDto update(Long id, PeopleRequest peopleRequest) {
        log.trace("update -> People " + id);
        Optional<People> optionalPeople = this.peopleRepository.findById(id);
        if (optionalPeople.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "People with id " + id + " not found");
        }
        People people = optionalPeople.get();
        peopleMapper.updateModel(peopleRequest, optionalPeople.get());
        this.peopleRepository.save(people);
        return peopleMapper.toDto(people);
    }

    private boolean validateBirthDate(LocalDate birthDate) {
        long old = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (old < 18)
            return false;
        return true;
    }

    private boolean validateDuplicate(PeopleRequest peopleRequest){
          Optional<People> optionalPeople = this.peopleRepository.findByCountryAndDocumentNumberAndDocumentType(
                peopleRequest.getCountry(),
                peopleRequest.getDocumentNumber(),
                peopleRequest.getDocumentType());
                if(optionalPeople.isEmpty()){
                    return false;
                }
        return true;
    }

}
