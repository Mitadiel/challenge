package com.reba.api.repository.people;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.reba.api.domain.People;
import com.reba.api.domain.enums.DocumentType;
import com.reba.api.dto.people.v1.PeopleStat;

@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {
    
    Optional<People> findByCountryAndDocumentNumberAndDocumentType(String country, String documentNumber, DocumentType documentType);

    /*
    
    @Query("SELECT Country, COUNT (*) * 100.0 / (SELECT count(*) from PEOPLES ) AS Number FROM PEOPLES GROUP BY COUNTRY ORDER BY COUNTRY")
    List<PeopleStat> getStatsFromCountries();
    */
}
