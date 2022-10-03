package com.reba.api.service.people.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.reba.api.dto.people.v1.PeopleStat;
import com.reba.api.dto.people.v1.PeopleDto;
import com.reba.api.dto.people.v1.PeopleRequest;

public interface PeopleService {
    PeopleDto save(PeopleRequest peopleRequest); 
    List<PeopleDto> findAll();
    PeopleDto findById(Long id);
    void delete(Long id);
    PeopleDto update(Long id, PeopleRequest peopleRequest);
    ResponseEntity<String> createRelation(Long id1, Long id2);
    List<PeopleStat> getStatsFromPeople();
    
}

