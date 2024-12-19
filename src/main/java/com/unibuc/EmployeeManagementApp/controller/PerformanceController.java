package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.PerformanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.service.PerformanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
