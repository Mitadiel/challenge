package com.reba.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reba.api.domain.People;
import com.reba.api.domain.enums.DocumentType;

@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {
    
    Optional<People> findByCountryAndDocumentNumberAndDocumentType(String country, String documentNumber, DocumentType documentType);
}
