package com.testTask.testTask.controllers;

import com.testTask.testTask.models.Company;
import com.testTask.testTask.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        List<Company> companies = service.findAll();
        if(companies != null) {
            return ResponseEntity.ok().body(companies);
        } else {
            return ResponseEntity.ok().body(new ArrayList<Company>());
        }
    }
}
