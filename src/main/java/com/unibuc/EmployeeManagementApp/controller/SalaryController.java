package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.SalaryDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.service.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaryController {

    private final SalaryService salaryService;

    private final Mapper<Salary, SalaryDto> salaryMapper;

    //Inject SalaryService & SalaryMapper to constructor
    public SalaryController(SalaryService salaryService, Mapper<Salary, SalaryDto> salaryMapper) {
        this.salaryService = salaryService;
        this.salaryMapper = salaryMapper;
    }

    @PostMapping(path = "/salaries")   //Define create Salary endpoint
    public ResponseEntity<SalaryDto> createSalary(@RequestBody SalaryDto salaryDto) {
        Salary salaryEntity = salaryMapper.mapFrom(salaryDto);  //Convert Dto object to Entity object
        Salary savedSalaryEntity = salaryService.createSalary(salaryEntity);    //Create Salary Entity object
        SalaryDto savedSalaryDto = salaryMapper.mapTo(savedSalaryEntity);   //Convert Entity object to Dto object
        return new ResponseEntity<>(
                savedSalaryDto,
                HttpStatus.CREATED
        );
    }

}
