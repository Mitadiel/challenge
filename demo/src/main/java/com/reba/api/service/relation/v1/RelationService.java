package com.reba.api.service.relation.v1;

import org.springframework.http.ResponseEntity;

public interface RelationService {
    
    ResponseEntity<String> getRelationType(Long id1, Long id2);
}
