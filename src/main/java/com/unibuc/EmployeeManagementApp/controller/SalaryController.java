package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.SalaryDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.service.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    private final Mapper<Salary, SalaryDto> salaryMapper;

    //Inject SalaryService & SalaryMapper Beans in constructor
    public SalaryController(SalaryService salaryService, Mapper<Salary, SalaryDto> salaryMapper) {
        this.salaryService = salaryService;
        this.salaryMapper = salaryMapper;
    }

    //Create Salary
    @PostMapping
    public ResponseEntity<SalaryDto> createSalary(@RequestBody SalaryDto salaryDto) {
        Salary salaryEntity = salaryMapper.mapFrom(salaryDto);  //Convert Dto to Entity
        Salary savedSalaryEntity = salaryService.createSalary(salaryEntity);    //Create Salary Entity
        SalaryDto savedSalaryDto = salaryMapper.mapTo(savedSalaryEntity);   //Convert Entity to Dto
        
        return new ResponseEntity<>(
                savedSalaryDto,
                HttpStatus.CREATED
        );
    }

}
