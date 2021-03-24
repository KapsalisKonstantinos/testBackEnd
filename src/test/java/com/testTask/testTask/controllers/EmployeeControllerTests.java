package com.testTask.testTask.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testTask.testTask.models.Company;
import com.testTask.testTask.models.Employee;
import com.testTask.testTask.services.CompanyService;
import com.testTask.testTask.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sound.sampled.AudioFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@RunWith(SpringRunner.class)
public class EmployeeControllerTests{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private EmployeeService employeeService;


    @Test
    public void getAllEmployees() throws Exception {
        Employee employee = Employee.builder()
                .id(1)
                .name("test")
                .surname("test123")
                .email("test@test.com")
                .salary(1000.0F)
                .address("street 1")
                .company_id(1).build();
        List employees = new ArrayList<Employee>();
        employees.add(employee);
        when(employeeService.findAll()).thenReturn(employees);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(employee);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + json +"]"));
    }

    @Test
    public void getAllEmployeesListView() throws Exception {
        Employee employee = Employee.builder()
                .id(1)
                .name("test")
                .surname("test123")
                .email("test@test.com")
                .salary(1000.0F)
                .address("street 1")
                .company_id(1).build();
        List employees = new ArrayList<Employee>();
        employees.add(employee);
        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees?listView=true"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"test\"]"));
    }

    @Test
    public void getAverageSalary() throws Exception {
        Employee employee1 = Employee.builder()
                .id(1)
                .name("test")
                .surname("test123")
                .email("test@test.com")
                .salary(1100.0F)
                .address("street 1")
                .company_id(1)
                .build();

        Employee employee2 = Employee.builder()
                .id(2)
                .name("test2")
                .surname("test2123")
                .email("test2@test.com")
                .salary(1200.0F)
                .address("street 2")
                .company_id(1)
                .build();

        List employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);

        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees/avg"))
                .andExpect(status().isOk())
                .andExpect(content().string("1150.0"));
    }

    @Test
    public void getEmployee() throws Exception {
        Employee employee = Employee.builder()
                .id(1)
                .name("test")
                .surname("test123")
                .email("test@test.com")
                .salary(1000.0F)
                .address("street 1")
                .company_id(1).build();
        List employees = new ArrayList<Employee>();
        employees.add(employee);
        Optional<Employee> optionalEmployee = Optional.of(employee);
        when(employeeService.find(1)).thenReturn(optionalEmployee);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(employee);

        mockMvc.perform(get("/employees/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    public void deleteEmployee() throws Exception {

        mockMvc.perform(delete("/employees/employee/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void putEmployee() throws Exception {
        String json = "{" +
                "\"name\":\"test\"," +
                "\"surname\":\"test123\"," +
                "\"email\":\"test@test.com\"," +
                "\"address\":\"street 1\"," +
                "\"salary\":1000," +
                "\"company_id\":1" +
                "}";

        Employee employee = Employee.builder()
                .id(1)
                .name("test")
                .surname("test123")
                .email("test@test.com")
                .salary(1000.0F)
                .address("street 1")
                .company_id(1)
                .build();

        Optional<Employee> optionalEmployee = Optional.of(employee);

        when(employeeService.find(1)).thenReturn(optionalEmployee);
        when(employeeService.save(any())).thenReturn(employee);

        System.out.println(json);
        mockMvc.perform(put("/employees/employee/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void postEmployee() throws Exception {
        String json = "{" +
                "\"name\":\"test\"," +
                "\"surname\":\"test123\"," +
                "\"email\":\"test@test.com\"," +
                "\"address\":\"street 1\"," +
                "\"salary\":1000," +
                "\"company_id\":1" +
                "}";

        Employee employee = Employee.builder()
                .id(1)
                .name("test")
                .surname("test123")
                .email("test@test.com")
                .salary(1000.0F)
                .address("street 1")
                .company_id(1)
                .build();

        Company company = Company.builder().id(1).name("testCompany").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonNew = mapper.writeValueAsString(employee);

        when(companyService.find(1)).thenReturn(company);
        when(employeeService.save(any())).thenReturn(employee);

        System.out.println(json);
        mockMvc.perform(post("/employees/employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonNew));
    }

}
