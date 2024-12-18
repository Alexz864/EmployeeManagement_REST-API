package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.AttendanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Attendances endpoint
@RestController
@RequestMapping(path = "/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    private final Mapper<Attendance, AttendanceDto> attendanceMapper;

    //Inject AttendanceService & AttendanceMapper Beans in constructor
    public AttendanceController(
            AttendanceService attendanceService,
            Mapper<Attendance, AttendanceDto> attendanceMapper
    ) {
        this.attendanceService = attendanceService;
        this.attendanceMapper = attendanceMapper;
    }

    //Create Attendance
    @PostMapping
    public ResponseEntity<AttendanceDto> createAttendance(@RequestBody AttendanceDto attendanceDto) {
        Attendance attendanceEntity =
                attendanceMapper.mapFrom(attendanceDto);  //Convert Dto to Entity
        Attendance savedAttendanceEntity =
                attendanceService.createAttendance(attendanceEntity);    //Create Attendance Entity
        AttendanceDto savedAttendanceDto =
                attendanceMapper.mapTo(savedAttendanceEntity);   //Covert Entity to Dto
        
        return new ResponseEntity<>(
                savedAttendanceDto,
                HttpStatus.CREATED
        );
    }
}
