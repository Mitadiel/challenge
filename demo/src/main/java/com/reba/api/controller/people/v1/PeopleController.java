package com.reba.api.controller.people.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reba.api.dto.people.v1.PeopleDto;
import com.reba.api.dto.people.v1.PeopleRequest;
import com.reba.api.service.people.v1.PeopleService;

import io.swagger.annotations.Api;

@Validated
@RestController
@RequestMapping("v1/peoples")
@Api(tags = {"people"})
public class PeopleController {
    
    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<PeopleDto> create(@Valid @RequestBody PeopleRequest peopleRequest){
        return new ResponseEntity<PeopleDto>(peopleService.save(peopleRequest),HttpStatus.CREATED);
    }

    @GetMapping
	public ResponseEntity<List<PeopleDto>> findAll() {
        List<PeopleDto> peopleList = peopleService.findAll();
        return new ResponseEntity<>(peopleList, HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<PeopleDto> show(@PathVariable("id") Long id) {
		return  new ResponseEntity<>(peopleService.findById(id),HttpStatus.OK); 
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        peopleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
	public ResponseEntity<PeopleDto> update(@PathVariable("id") Long id, @RequestBody PeopleRequest peopleRequest) {
		return new ResponseEntity<>(peopleService.update(id,peopleRequest),HttpStatus.ACCEPTED);
	}

    @PostMapping("/{id1}/father/{id2}")
    public ResponseEntity<String> createRelation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2){
        return peopleService.createRelation(id1,id2);
    }


}

 
