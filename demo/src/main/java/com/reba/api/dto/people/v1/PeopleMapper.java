package com.reba.api.dto.people.v1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.reba.api.domain.People;

@Mapper(componentModel = "spring")
public interface PeopleMapper{
    
    PeopleMapper INSTANCE =
    Mappers.getMapper(PeopleMapper.class);

    @Mapping(target = "id", ignore = true)
    People toModel(PeopleRequest peopleRequest);

    PeopleDto toDto(People people);
    
    @Mapping(target = "id", ignore = true)
    void updateModel(PeopleRequest peopleRequest, @MappingTarget People people);
}
