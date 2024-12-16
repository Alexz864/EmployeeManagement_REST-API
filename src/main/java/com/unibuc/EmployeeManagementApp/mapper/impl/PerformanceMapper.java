package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.PerformanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Performance;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PerformanceMapper implements Mapper<Performance, PerformanceDto> {

    private final ModelMapper modelMapper;

    //Inject ModelMapper Bean in constructor
    public PerformanceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public PerformanceDto mapTo(Performance performance) {
        return modelMapper.map(performance, PerformanceDto.class);
    }

    //Convert Dto object to Entity object
    @Override
    public Performance mapFrom(PerformanceDto performanceDto) {
        return modelMapper.map(performanceDto, Performance.class);
    }
}
