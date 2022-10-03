package com.reba.api.dto.relation.v1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.reba.api.domain.Relation;

@Mapper(componentModel = "spring")
public interface RelationMapper{
    
    RelationMapper INSTANCE =
    Mappers.getMapper(RelationMapper.class);

    @Mapping(target = "idRelation", ignore = true)
    @Mapping(target = "peoples", ignore = true)
    Relation toModel(RelationRequest relationRequest);
    
    @Mapping(target = "idRelation", ignore = true)
    @Mapping(target = "peoples", ignore = true)
    Relation toModelFromDto(RelationDto relationDto);

    @Mapping(target = "idRelation", ignore = true)
    @Mapping(target = "peoples", ignore = true)
    void updateModel(RelationRequest relationRequest, @MappingTarget Relation relation);
}
