package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Employees endpoint
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final Mapper<Employee, EmployeeDto> employeeMapper;

    //Inject EmployeeService & EmployeeMapper Beans in constructor
    public EmployeeController(
            EmployeeService employeeService,
            Mapper<Employee, EmployeeDto> employeeMapper
    ) {
        this.employeeMapper = employeeMapper;
        this.employeeService = employeeService;
    }

    //Create Employee
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employeeEntity = employeeMapper.mapFrom(employeeDto);  //Convert Dto to Entity
        Employee savedEmployeeEntity = employeeService.createEmployee(employeeEntity);  //Create Employee Entity
        EmployeeDto savedEmployeeDto = employeeMapper.mapTo(savedEmployeeEntity);   //Convert Entity to Dto

        return new ResponseEntity<>(
                savedEmployeeDto,
                HttpStatus.CREATED
        );
    }

    //Read all Employees
    @GetMapping
    public List<EmployeeDto> listEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        
        return employees.stream()
                .map(employeeMapper::mapTo)
                .collect(Collectors.toList());
    }

    //Read one Employee
    @GetMapping(path = "{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long id) {   //Reference the id passed in the URL
        Optional<Employee> foundEmployee = employeeService.findOneEmployee(id);

        //If it finds a EmployeeEntity, convert it to Dto
        return foundEmployee.map(employee -> {
            EmployeeDto employeeDto = employeeMapper.mapTo(employee);
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Partial update Employee
    @PatchMapping(path = "{id}")
    public ResponseEntity<EmployeeDto> partialUpdateRole(
            @PathVariable("id") Long id,
            @RequestBody EmployeeDto employeeDto
    ) {
        //Check if the Employee exists
        if(!employeeService.employeeExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Employee employeeEntity = employeeMapper.mapFrom(employeeDto); //Convert Dto to Entity
        Employee updatedEmployee = employeeService.partialUpdateEmployee(id, employeeEntity);   //Update Employee Entity
        EmployeeDto updatedEmployeeDto = employeeMapper.mapTo(updatedEmployee); //Convert Entity to Dto

        return new ResponseEntity<>(
                updatedEmployeeDto,
                HttpStatus.OK
        );
    }
}
