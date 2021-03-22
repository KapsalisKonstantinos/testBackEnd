package com.testTask.testTask.controllers;

import com.testTask.testTask.modelDTOs.CompanyDTO;
import com.testTask.testTask.models.Company;
import com.testTask.testTask.services.CompanyService;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        List<CompanyDTO> companies = service.findAll().stream().map(company -> modelMapper.map(company, CompanyDTO.class))
                .collect(Collectors.toList());
        if(companies != null) {
            return ResponseEntity.ok().body(companies);
        } else {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
    }
}
