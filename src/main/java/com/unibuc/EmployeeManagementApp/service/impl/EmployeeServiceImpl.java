package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired  //Inject EmployeeRepository
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override   //Create Employee
    public Employee createEmployee(Employee employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    @Override   //Find all Employees
    public List<Employee> findAllEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }
}
