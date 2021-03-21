package com.testTask.testTask.services;

import com.testTask.testTask.models.Company;
import com.testTask.testTask.models.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee save(Employee employee);
    Employee update(Employee employee);
    List<Employee> findAll();
    Optional<Employee> find(Integer id);
    void deleteById(Integer id);
}
