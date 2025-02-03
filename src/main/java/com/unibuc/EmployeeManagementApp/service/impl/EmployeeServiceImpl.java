package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmailExistsException;
import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
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

        //Check if the email already exists
        if(employeeRepository.existsByEmail(employeeEntity.getEmail())) {
            throw new EmailExistsException((employeeEntity.getEmail()));
        }

        //Save the Employee
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

    //Partial update Employee
    @Override
    public Employee partialUpdateEmployee(Long id, Employee employeeEntity) {
        employeeEntity.setId(id);

        //If an employeeEntity field is not null, set that on existingEmployee
        return employeeRepository.findById(id).map(existingEmployee -> {
            Optional.ofNullable(employeeEntity.getFirstName()).ifPresent(existingEmployee::setFirstName);
            Optional.ofNullable(employeeEntity.getLastName()).ifPresent(existingEmployee::setLastName);
            Optional.ofNullable(employeeEntity.getEmail()).ifPresent(existingEmployee::setEmail);
            Optional.ofNullable(employeeEntity.getDesignation()).ifPresent(existingEmployee::setDesignation);
            Optional.ofNullable(employeeEntity.getDepartment()).ifPresent(existingEmployee::setDepartment);
            Optional.ofNullable(employeeEntity.getRole()).ifPresent(role -> {
                Role existingRole = roleRepository.findByRoleName(role.getRoleName())
                        .orElseThrow(() -> new RoleNotFoundException(employeeEntity.getRole().getRoleName()));
                existingEmployee.setRole(existingRole);
            });

            return employeeRepository.save(existingEmployee);
        }).orElseThrow(() -> new EmployeeNotFoundException(""));
    }


    //Full update Employee
    @Override
    public Employee fullUpdateEmployee(Long id, Employee employeeEntity) {
        employeeEntity.setId(id);

        return employeeRepository.findById(id).map(existingEmployee -> {
            existingEmployee.setFirstName(employeeEntity.getFirstName());
            existingEmployee.setLastName(employeeEntity.getLastName());
            existingEmployee.setEmail(employeeEntity.getEmail());
            existingEmployee.setDesignation(employeeEntity.getDesignation());
            existingEmployee.setDepartment(employeeEntity.getDepartment());

            Role existingRole = roleRepository.findByRoleName(
                employeeEntity.getRole().getRoleName())
                .orElseThrow(() -> new RoleNotFoundException(employeeEntity.getRole().getRoleName())
            );
            existingEmployee.setRole(existingRole);

            return employeeRepository.save(existingEmployee);
        }).orElseThrow(() -> new EmployeeNotFoundException(""));
    }

    //Delete Employee
    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
