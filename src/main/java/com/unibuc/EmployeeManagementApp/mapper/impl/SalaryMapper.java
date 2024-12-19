package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.SalaryDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Salary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class SalaryMapper implements Mapper<Salary, SalaryDto> {

    private final ModelMapper modelMapper;

    //Inject ModelMapper in constructor
    public SalaryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public SalaryDto mapTo(Salary salary) {
        return modelMapper.map(salary, SalaryDto.class);
    }

    //Convert Dto object to Entity object
    @Override
    public Salary mapFrom(SalaryDto salaryDto) {
        return modelMapper.map(salaryDto, Salary.class);
    }
}
