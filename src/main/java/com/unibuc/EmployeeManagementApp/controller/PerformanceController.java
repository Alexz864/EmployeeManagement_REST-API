package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.PerformanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.service.PerformanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Performances endpoint
@RestController
@RequestMapping(path = "/performances")
@SuppressWarnings("unused")
public class PerformanceController {

    private final PerformanceService performanceService;

    private final Mapper<Performance, PerformanceDto> performanceMapper;

    //Inject PerformanceService & PerformanceMapper Beans in constructor
    public PerformanceController(
            PerformanceService performanceService,
            Mapper<Performance, PerformanceDto> performanceMapper
    ) {
        this.performanceService = performanceService;
        this.performanceMapper = performanceMapper;
    }

    //Create Performance
    @PostMapping
    public ResponseEntity<PerformanceDto> createPerformance(@RequestBody PerformanceDto performanceDto) {
        Performance performanceEntity =
                performanceMapper.mapFrom(performanceDto);  //Convert Dto to Entity
        Performance savedPerformanceEntity =
                performanceService.createPerformance(performanceEntity);   //Create Performance Entity
        PerformanceDto savedPerformanceDto =
                performanceMapper.mapTo(savedPerformanceEntity);   //Convert Entity to Dto
        
        return new ResponseEntity<>(
                savedPerformanceDto,
                HttpStatus.CREATED
        );
    }

    //Read all Performances
    @GetMapping
    public List<PerformanceDto> listPerformances() {
        List<Performance> performances = performanceService.findAllPerformances();

        return performances.stream()
                .map(performanceMapper::mapTo)
                .collect(Collectors.toList());
    }

    //Read one Performance
    @GetMapping(path = "/{id}")
    public ResponseEntity<PerformanceDto> getPerformance(@PathVariable("id") Long id) {
        Optional<Performance> foundPerformance = performanceService.findOnePerformance(id);

        //If it finds an PerformanceEntity, convert it to Dto
        return foundPerformance.map(performance -> {
            PerformanceDto performanceDto = performanceMapper.mapTo(performance);
            return new ResponseEntity<>(performanceDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Full update Performance
    @PutMapping(path = "/{id}")
    public ResponseEntity<PerformanceDto> fullUpdatePerformance(
            @PathVariable("id") Long id,
            @RequestBody PerformanceDto performanceDto
    ) {
        Performance performanceEntity = performanceMapper.mapFrom(performanceDto);  //Convert Dto to Entity
        Performance updatedPerformanceEntity = performanceService.fullUpdatePerformance(id, performanceEntity); //Update Performance Entity
        PerformanceDto updatedPerformanceDto = performanceMapper.mapTo(updatedPerformanceEntity);   //Convert Entity to Dto

        return new ResponseEntity<>(
                updatedPerformanceDto,
                HttpStatus.OK
        );
    }

    //Delete Performance
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePerformance(@PathVariable("id") Long id) {
        performanceService.deletePerformance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
