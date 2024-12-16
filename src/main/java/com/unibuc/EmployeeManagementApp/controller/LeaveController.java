package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaveController {

    private final LeaveService leaveService;

    private final Mapper<Leave, LeaveDto> leaveMapper;

    //Inject LeaveService & LeaveMapper to constructor
    public LeaveController(LeaveService leaveService, Mapper<Leave, LeaveDto> leaveMapper) {
        this.leaveService = leaveService;
        this.leaveMapper = leaveMapper;
    }

    @PostMapping(path = "/leaves")  //Define create Leave endpoint
    public ResponseEntity<LeaveDto> createLeave(@RequestBody LeaveDto leaveDto) {
        Leave leaveEntity = leaveMapper.mapFrom(leaveDto);  //Convert Dto object to Entity object
        Leave savedLeaveEntity = leaveService.createLeave(leaveEntity); //Create Leave Entity object
        LeaveDto savedLeaveDto = leaveMapper.mapTo(savedLeaveEntity);   //Convert Entity object to Dto object;
        return new ResponseEntity<>(
                savedLeaveDto,
                HttpStatus.CREATED
        );
    }
}
