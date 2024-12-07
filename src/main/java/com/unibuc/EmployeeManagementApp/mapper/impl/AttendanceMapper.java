package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.AttendanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper implements Mapper<Attendance, AttendanceDto> {

    private final ModelMapper modelMapper;

    //Inject ModelMapper Bean in constructor
    public AttendanceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public AttendanceDto mapTo(Attendance attendance) {
        return modelMapper.map(attendance, AttendanceDto.class);
    }

    //Covert Dto object to Entity object
    @Override
    public Attendance mapFrom(AttendanceDto attendanceDto) {
        return modelMapper.map(attendanceDto, Attendance.class);
    }
}
