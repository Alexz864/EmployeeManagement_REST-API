package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @GetMapping
    public List<LeaveDto> listLeaves() {
        List<Leave> leaves = leaveService.findAllLeaves();

        return leaves.stream()
                .map(leaveMapper::mapTo)
                .collect(Collectors.toList());
    }

    //Read one Leave
    @GetMapping(path = "/{id}")
    public ResponseEntity<LeaveDto> getLeave(@PathVariable("id") Long id) {
        Optional<Leave> foundLeave = leaveService.findOneLeave(id);

        //If it finds an LeaveEntity, convert it to Dto
        return foundLeave.map(leave -> {
            LeaveDto leaveDto = leaveMapper.mapTo(leave);
            return new ResponseEntity<>(leaveDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Full update Leaves
    @PutMapping(path = "/{id}")
    public ResponseEntity<LeaveDto> fullUpdateLeave(
            @PathVariable("id") Long id,
            @RequestBody LeaveDto leaveDto
    ) {
        Leave leaveEntity = leaveMapper.mapFrom(leaveDto);  //Convert Dto to Entity
        Leave updatedLeaveEntity = leaveService.fullUpdateLeave(id, leaveEntity);   //Update Leave Entity
        LeaveDto updatedLeaveDto = leaveMapper.mapTo(updatedLeaveEntity);   //Convert Entity to Dto

        return new ResponseEntity<>(
                updatedLeaveDto,
                HttpStatus.OK
        );
    }

    //Delete Leaves
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable("id") Long id) {
        leaveService.deleteLeave(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
