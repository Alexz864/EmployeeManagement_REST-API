package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "employees")     //Employees endpoint
public class EmployeeController {

    private final EmployeeService employeeService;

    private final Mapper<Employee, EmployeeDto> employeeMapper;

    //Inject EmployeeService & EmployeeMapper Beans to constructor
    public EmployeeController( EmployeeService employeeService,Mapper<Employee, EmployeeDto> employeeMapper) {
        this.employeeMapper = employeeMapper;
        this.employeeService = employeeService;
    }

    @PostMapping    //Create
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employeeEntity = employeeMapper.mapFrom(employeeDto);  //Convert Dto object to Entity object
        System.out.println(employeeEntity);
        Employee savedEmployeeEntity = employeeService.createEmployee(employeeEntity);  //Create Employee Entity object
        EmployeeDto savedEmployeeDto = employeeMapper.mapTo(savedEmployeeEntity);   //Convert Entity object to Dto object
        return new ResponseEntity<>(
                savedEmployeeDto,
                HttpStatus.CREATED
        );
    }

    @GetMapping     //Read all
    public List<EmployeeDto> listEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return employees.stream()
                .map(employeeMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")  //Read one
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long id) {   //Reference the id passed in the URL
        Optional<Employee> foundEmployee = employeeService.findOneEmployee(id);
        return foundEmployee.map(employee -> {
            EmployeeDto employeeDto = employeeMapper.mapTo(employee);   //Convert to Dto
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
