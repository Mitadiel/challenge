package com.reba.api.service.relation.v1.impl;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reba.api.dto.people.v1.PeopleDto;
import com.reba.api.dto.relation.v1.RelationDto;
import com.reba.api.service.people.v1.PeopleService;
import com.reba.api.service.relation.v1.RelationService;

@Service
public class RelationServiceImpl implements RelationService{

    private final PeopleService peopleService ;

    public RelationServiceImpl(
        PeopleService peopleService){
            this.peopleService = peopleService;
        }

    @Override
    public ResponseEntity<String> getRelationType(Long id1, Long id2) {
        
        PeopleDto PeopleDtoId1 = peopleService.findById(id1);
        PeopleDto PeopleDtoId2 = peopleService.findById(id1);
        String rsp = "";
        rsp = getTypeOfRelation(PeopleDtoId1,id2);
        if (rsp == ""){
            rsp = getTypeOfRelation(PeopleDtoId2,id1);
        }
        
         return (rsp == "") ?  ResponseEntity.ok("Not found relation") : ResponseEntity.ok(rsp);
    }


    private String getTypeOfRelation(PeopleDto peopleDto , Long id){
       Optional<RelationDto> optional = peopleDto.getRelations().stream().findAny().filter(relation -> relation.getIdPeople() == id);
        return (optional.isEmpty()) ? "" : optional.get().getRelationType().toString();
    }
}