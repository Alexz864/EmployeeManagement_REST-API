package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.AttendanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceController {

    private final AttendanceService attendanceService;

    private final Mapper<Attendance, AttendanceDto> attendanceMapper;

    //Inject AttendanceService and AttendanceMapper in constructor
    public AttendanceController(AttendanceService attendanceService, Mapper<Attendance, AttendanceDto> attendanceMapper) {
        this.attendanceService = attendanceService;
        this.attendanceMapper = attendanceMapper;
    }

    @PostMapping(path = "/attendances") //Define create Attendance endpoint
    public ResponseEntity<AttendanceDto> createAttendance(@RequestBody AttendanceDto attendanceDto) {
        Attendance attendanceEntity = attendanceMapper.mapFrom(attendanceDto);  //Convert Dto object to Entity object
        Attendance savedAttendanceEntity = attendanceService.createAttendance(attendanceEntity);    //Create Attendance Entity object
        AttendanceDto savedAttendanceDto = attendanceMapper.mapTo(savedAttendanceEntity);   //Covert Entity object to Dto object
        return new ResponseEntity<>(
                savedAttendanceDto,
                HttpStatus.CREATED
        );
    }
}
