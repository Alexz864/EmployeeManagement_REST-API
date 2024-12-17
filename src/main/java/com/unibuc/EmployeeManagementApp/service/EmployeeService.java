package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employeeEntity);

    List<Employee> findAllEmployees();
}
