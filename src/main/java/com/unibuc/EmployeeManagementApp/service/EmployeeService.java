package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    //Create Employee
    Employee createEmployee(Employee employeeEntity);

    //Read all Employees
    List<Employee> findAllEmployees();

    //Read one Employee
    Optional<Employee> findOneEmployee(Long id);

    //Check if Employee exists
    boolean employeeExists(Long id);

    //Employee partial update
    Employee partialUpdateEmployee(Long id, Employee employeeEntity);
    
}
