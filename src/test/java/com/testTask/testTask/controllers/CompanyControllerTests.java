package com.testTask.testTask.controllers;

import com.testTask.testTask.models.Company;
import com.testTask.testTask.services.CompanyService;
import com.testTask.testTask.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest
@RunWith(SpringRunner.class)
public class CompanyControllerTests{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService service;

    @MockBean
    private EmployeeService employeeService;


    @Test
    public void getAllCompanies() throws Exception {
        Company company = Company.builder().id(1).name("test").build();
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        when(this.service.findAll()).thenReturn(companies);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"test\"}]"));
    }

}