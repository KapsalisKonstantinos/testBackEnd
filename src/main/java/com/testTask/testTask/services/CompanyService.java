package com.testTask.testTask.services;

import com.testTask.testTask.models.Company;
import com.testTask.testTask.repositories.ICompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyService implements ICompanyService {

    ICompanyRepository companyRepository;

    @Override
    public Company save(Company company) { return companyRepository.save(company); }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company find(Integer id) {
        return companyRepository.getOne(id);
    }
}
