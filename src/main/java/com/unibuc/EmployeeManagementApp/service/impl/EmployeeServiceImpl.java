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
@SuppressWarnings("unused")
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

    //Check if Employee exists
    @Override
    public boolean employeeExists(Long id) {
        return employeeRepository.existsById(id);
    }

    //Partial update Employee
    @Override
    public Employee partialUpdateEmployee(Long id, Employee employeeEntity) {
        employeeEntity.setId(id);

        //If employeeEntity has a name and is not null, set that on existingEmployee
        return employeeRepository.findById(id).map(existingEmployee -> {
            Optional.ofNullable(employeeEntity.getFirstName()).ifPresent(existingEmployee::setFirstName);
            return employeeRepository.save(existingEmployee);
        }).orElseThrow(() -> new RuntimeException("Employee does not exist."));
    }

}
