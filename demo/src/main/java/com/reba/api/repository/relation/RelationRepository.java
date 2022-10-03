package com.reba.api.repository.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reba.api.domain.Relation;

@Repository
public interface RelationRepository extends JpaRepository<Relation,Long> {
}
