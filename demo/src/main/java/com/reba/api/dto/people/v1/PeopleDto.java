package com.reba.api.dto.people.v1;

import java.time.LocalDate;
import java.util.List;

import com.reba.api.domain.enums.DocumentType;
import com.reba.api.dto.relation.v1.RelationDto;

import lombok.Data;

@Data
public class PeopleDto {
    private long id;
    private String name;
    private String documentNumber;
    private DocumentType documentType;
    private LocalDate birthDate;
    private String Email;
    private String lastName;
    private String Country;
    private List<RelationDto> relations;
}
