package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee createEmployee(Employee employeeEntity);

    List<Employee> findAllEmployees();

    Optional<Employee> findOneEmployee(Long id);
}
