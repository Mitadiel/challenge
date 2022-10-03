package com.reba.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.reba.api.domain.enums.RelationType;

import lombok.Data;

@Entity
@Table(name = "relationship")
@Data
public class Relation {
    @Id
    @Column(name = "idRelation")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRelation;
    private RelationType relationType;
    private Long idPeople;


    @ManyToMany(mappedBy = "relations",fetch = FetchType.LAZY)
    private List<People> peoples = new ArrayList<>();

    public void addPeople(People people) {
        peoples.add(people);
    }
}