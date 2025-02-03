package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Leaves endpoint
@RestController
@RequestMapping(path = "/leaves")
@SuppressWarnings("unused")
public class LeaveController {

    private final LeaveService leaveService;

    private final Mapper<Leave, LeaveDto> leaveMapper;

    //Inject LeaveService & LeaveMapper Beans in constructor
    public LeaveController(LeaveService leaveService, Mapper<Leave, LeaveDto> leaveMapper) {
        this.leaveService = leaveService;
        this.leaveMapper = leaveMapper;
    }

    //Create Leave
    @PostMapping
    public ResponseEntity<LeaveDto> createLeave(@RequestBody LeaveDto leaveDto) {
        Leave leaveEntity = leaveMapper.mapFrom(leaveDto);  //Convert Dto to Entity
        Leave savedLeaveEntity = leaveService.createLeave(leaveEntity); //Create Leave Entity
        LeaveDto savedLeaveDto = leaveMapper.mapTo(savedLeaveEntity);   //Convert Entity to Dto
        
        return new ResponseEntity<>(
                savedLeaveDto,
                HttpStatus.CREATED
        );
    }

    //Read all Leaves

    //Read one Leave

    //Partial update Leaves

    //Full update Leaves

    //Delete Leaves
}
