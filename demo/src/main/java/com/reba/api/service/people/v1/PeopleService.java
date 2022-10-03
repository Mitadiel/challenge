package com.reba.api.service.people.v1;

import java.util.List;

import com.reba.api.dto.people.v1.PeopleDto;
import com.reba.api.dto.people.v1.PeopleRequest;

public interface PeopleService {
    PeopleDto save(PeopleRequest peopleRequest); 
    List<PeopleDto> findAll();
    PeopleDto findById(Long id);
    void delete(Long id);
    PeopleDto update(Long id, PeopleRequest peopleRequest);
    
}

