package com.reba.api.dto.people.v1;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


import com.reba.api.domain.enums.DocumentType;

import static com.reba.api.validation.PeopleValidation.BIRTH_DATE_MSG;
import static com.reba.api.validation.PeopleValidation.EMAIL_MSG;
import static com.reba.api.validation.PeopleValidation.NAME_MAX_LENGTH;
import static com.reba.api.validation.PeopleValidation.LAST_NAME_MAX_LENGTH;
import static com.reba.api.validation.PeopleValidation.LAST_NAME_VALUE;
import static com.reba.api.validation.PeopleValidation.NAME_VALUE;
import static com.reba.api.validation.PeopleValidation.COUNTRY_MAX_LENGTH;
import static com.reba.api.validation.PeopleValidation.NULL_MSG;
import static com.reba.api.validation.PeopleValidation.COUNTRY_VALUE;
import static com.reba.api.validation.PeopleValidation.EMPTY_MSG;
import static com.reba.api.validation.PeopleValidation.DOCUMENT_VALUE;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PeopleRequest {
	
    @NotNull(message = NULL_MSG)
	@Past(message = BIRTH_DATE_MSG)
    @DateTimeFormat(iso = ISO.DATE)
    @ApiModelProperty(example = "yyyy-MM-dd", required = true)
    private LocalDate birthDate;

    @ApiModelProperty(value = "Identificador del usuario", required = true)
    @NotBlank(message = DOCUMENT_VALUE)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    
    @NotEmpty(message = EMPTY_MSG)
    @Email(message = EMAIL_MSG)
    private String Email;

    @Size(max = LAST_NAME_MAX_LENGTH, message = LAST_NAME_VALUE)
    @NotEmpty(message = EMPTY_MSG)
    @NotNull( message = NULL_MSG)
    private String lastName;

    @NotEmpty(message = EMPTY_MSG)
    @NotNull( message = NULL_MSG)
	@Size(max = NAME_MAX_LENGTH, message = NAME_VALUE)
    private String name;

    @NotEmpty(message = EMPTY_MSG)
    @NotNull( message = NULL_MSG)
    @Size(max = COUNTRY_MAX_LENGTH, message = COUNTRY_VALUE)
    private String Country;
}
