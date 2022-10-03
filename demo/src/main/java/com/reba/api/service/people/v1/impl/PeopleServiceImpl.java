package com.reba.api.service.people.v1.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.reba.api.domain.People;
import com.reba.api.domain.Relation;
import com.reba.api.domain.enums.RelationType;
import com.reba.api.dto.people.v1.PeopleStat;
import com.reba.api.dto.people.v1.PeopleDto;
import com.reba.api.dto.people.v1.PeopleMapper;
import com.reba.api.dto.people.v1.PeopleRequest;
import com.reba.api.dto.relation.v1.RelationMapper;
import com.reba.api.dto.relation.v1.RelationRequest;
import com.reba.api.repository.people.PeopleRepository;
import com.reba.api.repository.relation.RelationRepository;
import com.reba.api.service.people.v1.PeopleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;
    private final ModelMapper modelMapper;
    private final RelationRepository relationRepository;
    private final RelationMapper relationMapper;
    
    public PeopleServiceImpl(
        PeopleRepository peopleRepository, 
        PeopleMapper peopleMapper, 
        ModelMapper modelMapper,
        RelationRepository relationRepository,
        RelationMapper relationMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
        this.modelMapper = modelMapper;
        this.relationRepository = relationRepository;
        this.relationMapper = relationMapper;
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
        Optional<People> optionalPeople = getOne(id);
        People people = optionalPeople.get();
        return this.peopleMapper.toDto(people);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.trace("delete -> People " + id);
        getOne(id);
        this.peopleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public PeopleDto update(Long id, PeopleRequest peopleRequest) {
        log.trace("update -> People " + id);
        Optional<People> optionalPeople = getOne(id);
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

    @Transactional
    @Override
    public ResponseEntity<String> createRelation(Long id1, Long id2)  {
        log.trace("save -> relation people"+ id1 + "with" + id2);
        //validate same person
        if(id1 == id2) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "the same person cannot relate to himself");
        
        //validate exist
        Optional<People> optionalPeople = getOne(id1);
        getOne(id2);

        RelationRequest data = new RelationRequest();
        data.setIdPeople(id2);
        data.setRelationType(RelationType.padre);
    
        Relation rsp = this.relationRepository.save(relationMapper.toModel(data));
        optionalPeople.get().addRelation(rsp);
        this.peopleRepository.save(optionalPeople.get());
        return ResponseEntity.ok("People "+ id1 + " is parent of "+ id2);
    }

    private Optional<People> getOne(Long id){
        Optional<People> optionalPeople = this.peopleRepository.findById(id);
        if (optionalPeople.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "People with id " + id + " not found");
        }
        return optionalPeople;
    }

    @Override
    public List<PeopleStat> getStatsFromPeople() {
        
        //query armada para traer datos exactos de forma optioma
        //error al mapear
        //return this.peopleRepository.getStatsFromCountries();

        List<People> peopleList = this.peopleRepository.findAll();
        List<PeopleStat> peopleStatList = new ArrayList<>();
        Integer size = peopleList.size();
            Function<People, String> classificationFunction = student -> student.getCountry();

            Map<String, Long> groupedStudents = peopleList.stream().collect(Collectors.groupingBy(classificationFunction, Collectors.counting()));
            
            for (Map.Entry<String, Long> entry : groupedStudents.entrySet()) {
                PeopleStat people = new PeopleStat();
                people.setCountry(entry.getKey());
                Long data = entry.getValue()*100/size;
                people.setPercentage(data.toString());
                peopleStatList.add(people);
            }


        return peopleStatList;
    }

}
