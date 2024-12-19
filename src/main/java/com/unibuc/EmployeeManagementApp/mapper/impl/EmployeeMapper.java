package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class EmployeeMapper implements Mapper<Employee, EmployeeDto> {

    private final ModelMapper modelMapper;

    //Inject ModelMapper in constructor
    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public EmployeeDto mapTo(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    //Covert Dto object to Entity object
    @Override
    public Employee mapFrom(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }
}
