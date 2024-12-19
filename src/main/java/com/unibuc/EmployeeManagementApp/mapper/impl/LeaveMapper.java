package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Leave;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class LeaveMapper implements Mapper<Leave, LeaveDto> {

    private final ModelMapper modelMapper;

    //Inject ModelMapper Bean in constructor
    public LeaveMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public LeaveDto mapTo(Leave leave) {
        return modelMapper.map(leave, LeaveDto.class);
    }

    //Covert Dto object to Entity object
    @Override
    public Leave mapFrom(LeaveDto leaveDto) {
        return modelMapper.map(leaveDto, Leave.class);
    }
}
