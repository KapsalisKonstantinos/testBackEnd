package com.testTask.testTask.controllers;

import com.testTask.testTask.modelDTOs.CompanyDTO;
import com.testTask.testTask.modelDTOs.EmployeeDTO;
import com.testTask.testTask.models.Company;
import com.testTask.testTask.models.Employee;
import com.testTask.testTask.services.CompanyService;
import com.testTask.testTask.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    CompanyService companyService;
    ModelMapper modelMapper = new ModelMapper();


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll(@RequestParam(defaultValue = "false", name = "listView", required = false) String listView) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        List<EmployeeDTO> employees = employeeService.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());

        if(listView.equalsIgnoreCase("true")) {
            List response = new ArrayList<String>();
            employees.stream().map(EmployeeDTO::getName).forEach(employee -> {
                response.add(employee);
            });
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body(employees);
        }
    }

    @GetMapping(path = "/avg")
    public ResponseEntity<Double> getAvgSalary() {
        List<EmployeeDTO> employees = employeeService.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());

        if(employees.size() > 0) {
            return ResponseEntity.ok().body(employees.stream().mapToDouble(EmployeeDTO::getSalary).average().getAsDouble());
        } else {
            return ResponseEntity.ok().body(0.0d);
        }

    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> get(@PathVariable Integer id) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        return ResponseEntity.ok().body(modelMapper.map(employeeService.find(id).get(), EmployeeDTO.class));
    }


    @PostMapping(path = "/employee")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Company company = companyService.find(employeeDTO.getCompany_id());

        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .email(employeeDTO.getEmail())
                .address(employeeDTO.getAddress())
                .salary(employeeDTO.getSalary())
                .company_id(company.getId())
                .build();

        return ResponseEntity.ok(modelMapper.map(employeeService.save(employee), EmployeeDTO.class));
    }


    @PutMapping(path = "/employee/{id}")
    public ResponseEntity<EmployeeDTO> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id) {
        Optional<Employee> oldEmployee = employeeService.find(id);

        Employee newEmployee = Employee.builder()
                .id(oldEmployee.get().getId())
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .email(employeeDTO.getEmail())
                .address(employeeDTO.getAddress())
                .company_id(employeeDTO.getCompany_id())
                .salary(employeeDTO.getSalary())
                .build();

        return ResponseEntity.ok().body(modelMapper.map(employeeService.save(newEmployee), EmployeeDTO.class));
    }


    @DeleteMapping(path = "/employee/{id}")
    void delete(@PathVariable Integer id) {
        employeeService.deleteById(id);
    }

}
