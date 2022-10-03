package com.reba.api.dto.relation.v1;

import com.reba.api.domain.enums.RelationType;

import lombok.Data;

@Data
public class RelationDto {
    private RelationType relationType;
    private Long idPeople;
    private Long idRelation;
}
