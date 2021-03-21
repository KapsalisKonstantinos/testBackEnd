package com.testTask.testTask.services;

import com.testTask.testTask.models.Company;

import java.util.List;

public interface ICompanyService {
    Company save(Company company);
    List<Company> findAll();
    Company find(Integer id);
}
