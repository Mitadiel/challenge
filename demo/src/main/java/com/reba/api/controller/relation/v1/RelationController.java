package com.reba.api.controller.relation.v1;



import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reba.api.service.relation.v1.RelationService;

import io.swagger.annotations.Api;

@Validated
@RestController
@RequestMapping("v1/relations")
@Api(tags = {"relation"})
public class RelationController {
    
    private final RelationService relationService;

    public RelationController(RelationService relationService){
        this.relationService = relationService;
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<String> getRelationType(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        return relationService.getRelationType(id1,id2);
	}

}

 
