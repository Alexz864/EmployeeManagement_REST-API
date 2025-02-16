package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.SalaryDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.service.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/salaries")
@SuppressWarnings("unused")
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

    //Read all Salaries
    @GetMapping
    public List<SalaryDto> listSalaries() {
        List<Salary> salaries = salaryService.findAllSalaries();

        return salaries.stream()
                .map(salaryMapper::mapTo)
                .collect(Collectors.toList());
    }

    //Read one Salary
    @GetMapping(path = "/{id}")
    public ResponseEntity<SalaryDto> getSalary(@PathVariable("id") Long id) {
        Optional<Salary> foundSalary = salaryService.findOneSalary(id);

        //If it finds an SalaryEntity, convert it to Dto
        return foundSalary.map(salary -> {
            SalaryDto salaryDto = salaryMapper.mapTo(salary);
            return new ResponseEntity<>(salaryDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Full update Salary
    @PutMapping(path = "/{id}")
    public ResponseEntity<SalaryDto> fullUpdateSalary(
            @PathVariable("id") Long id,
            @RequestBody SalaryDto salaryDto
    ) {
        Salary salaryEntity = salaryMapper.mapFrom(salaryDto);  //Convert Dto to Entity
        Salary updatedSalaryEntity = salaryService.fullUpdateSalary(id, salaryEntity);  //Update Salary Entity
        SalaryDto updatedSalaryDto = salaryMapper.mapTo(updatedSalaryEntity);   //Convert Entity to Dto

        return new ResponseEntity<>(
                updatedSalaryDto,
                HttpStatus.OK
        );
    }

    //Delete Salary
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable("id") Long id) {
        salaryService.deleteSalary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
