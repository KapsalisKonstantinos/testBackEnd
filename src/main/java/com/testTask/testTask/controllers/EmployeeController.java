package com.testTask.testTask.controllers;

import com.testTask.testTask.modelDTOs.EmployeeDTO;
import com.testTask.testTask.models.Company;
import com.testTask.testTask.models.Employee;
import com.testTask.testTask.services.CompanyService;
import com.testTask.testTask.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    CompanyService companyService;

//    @GetMapping
//    public ResponseEntity<List<Employee>> getAll() {
//        List employees = employeeService.findAll();
//        if(employees != null) {
//            return ResponseEntity.ok().body(employees);
//        } else {
//            return ResponseEntity.ok().body(new ArrayList<>());
//        }
//    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll(@RequestParam(defaultValue = "false", name = "listView", required = false) String listView) {
        List<Employee> employees = employeeService.findAll();
        if(employees != null && listView.equalsIgnoreCase("true")) {
            List response = new ArrayList<String>();
            employees.stream().map(Employee::getName).forEach(employee -> {
                response.add(employee);
            });
            return ResponseEntity.ok().body(response);
        } else if(employees != null){
            return ResponseEntity.ok().body(employees);
        } else {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
    }

    @GetMapping(path = "/avg")
    public ResponseEntity<Double> getAvgSalary() {
        List<Employee> employees = employeeService.findAll();
        if(employees != null) {
            return ResponseEntity.ok().body(employees.stream().mapToDouble(Employee::getSalary).average().getAsDouble());
        } else {
            return ResponseEntity.ok().body(0.0);
        }
    }



    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> get(@PathVariable Integer id) {
        Optional<Employee> employee = employeeService.find(id);
        if(employee.isPresent()) {
            return ResponseEntity.ok().body(employee.get());
        } else {
            return ResponseEntity.badRequest().body(new Employee());
        }
    }

    @PostMapping(path = "/employee")
    public ResponseEntity<Employee> create(@RequestBody EmployeeDTO employeeDTO) {
        Company company = companyService.find(employeeDTO.getCompany_id());

        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .email(employeeDTO.getEmail())
                .address(employeeDTO.getAddress())
                .company(company)
                .salary(employeeDTO.getSalary())
                .build();

        Employee response = this.employeeService.save(employee);
        if(response!=null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping(path = "/employee/{id}")
    public ResponseEntity<Employee> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id) {
        Company company = companyService.find(employeeDTO.getCompany_id());
        Optional<Employee> oldEmployee = employeeService.find(id);
        if (oldEmployee.isPresent()){
            Employee newEmployee = Employee.builder()
                    .name(employeeDTO.getName())
                    .surname(employeeDTO.getSurname())
                    .email(employeeDTO.getEmail())
                    .address(employeeDTO.getAddress())
                    .company(company)
                    .salary(employeeDTO.getSalary())
                    .build();

            return ResponseEntity.ok().body(this.employeeService.save(newEmployee));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping(path = "/employee/{id}")
    void delete(@PathVariable Integer id) {
        employeeService.deleteById(id);
    }

}
