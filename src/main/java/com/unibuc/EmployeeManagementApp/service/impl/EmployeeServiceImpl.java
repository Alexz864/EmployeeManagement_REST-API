package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    //Inject EmployeeRepository Bean in constructor
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Create Employee
    @Override
    public Employee createEmployee(Employee employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    //Read all Employees
    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    //Read one Employee
    @Override
    public Optional<Employee> findOneEmployee(Long id) {
        return employeeRepository.findById(id);
    }
}
