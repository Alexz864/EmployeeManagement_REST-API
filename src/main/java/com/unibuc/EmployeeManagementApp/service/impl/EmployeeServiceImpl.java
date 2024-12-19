package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.RoleNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.RoleRepository;
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

    private final RoleRepository roleRepository;

    //Inject EmployeeRepository & RoleRepository Bean in constructor
    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            RoleRepository roleRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    //Create Employee
    @Override
    public Employee createEmployee(Employee employeeEntity) {
        //Ensure Role existence before assigning to the Employee
        Role role = roleRepository.findByRoleName(employeeEntity.getRole().getRoleName())
                .orElseThrow(() ->
                        new RoleNotFoundException(employeeEntity.getRole().getRoleName())
                );

        //Assign the resolved Role to the Employee
        employeeEntity.setRole(role);

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
