package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.AttendanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Attendances endpoint
@RestController
@RequestMapping(path = "/attendances")
@SuppressWarnings("unused")
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

    //Read all Attendances
    @GetMapping
    public List<AttendanceDto> listAttendances() {
        List<Attendance> attendances = attendanceService.findAllAttendances();

        return attendances.stream()
                .map(attendanceMapper::mapTo)
                .collect(Collectors.toList());
    }

    //Read one Attendance
    @GetMapping(path = "/{id}")
    public ResponseEntity<AttendanceDto> getAttendance(@PathVariable("id") Long id) {   //Reference the id passed in the URL
        Optional<Attendance> foundAttendance = attendanceService.findOneAttendance(id);

        //If it finds an AttendanceEntity, convert it to Dto
        return foundAttendance.map(attendance -> {
            AttendanceDto attendanceDto = attendanceMapper.mapTo(attendance);
            return new ResponseEntity<>(attendanceDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Full update Attendance
    @PutMapping(path = "/{id}")
    public ResponseEntity<AttendanceDto> fullUpdateAttendance(
            @PathVariable("id") Long id,
            @RequestBody AttendanceDto attendanceDto
    ) {

        Attendance attendanceEntity = attendanceMapper.mapFrom(attendanceDto); //Convert Dto to Entity
        Attendance updatedAttendanceEntity = attendanceService.fullUpdateAttendance(id ,attendanceEntity);   //Update Attendance Entity
        AttendanceDto updatedAttendanceDto = attendanceMapper.mapTo(updatedAttendanceEntity);  //Convert Entity to Dto

        return new ResponseEntity<>(
                updatedAttendanceDto,
                HttpStatus.OK
        );
    }

    //Delete Attendance
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable("id") Long id) {
        attendanceService.deleteAttendance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
