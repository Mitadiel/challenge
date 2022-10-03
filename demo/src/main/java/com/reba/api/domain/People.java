package com.reba.api.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.reba.api.domain.enums.DocumentType;

import lombok.Data;

@Data
@Entity
@Table(name = "peoples")
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
   @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String documentNumber;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String country;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "people_relations",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "relationId")
    )
    private List<Relation> relations = new ArrayList<>();


    public void addRelation(Relation relation){
        relations.add(relation);
    }
}
