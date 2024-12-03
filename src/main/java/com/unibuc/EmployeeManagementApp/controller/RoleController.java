package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private final RoleService roleService;

    private final Mapper<Role, RoleDto> roleMapper;

    //constructor
    public RoleController(RoleService roleService, Mapper<Role, RoleDto> roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping(path = "/roles")   //Define the endpoint
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto role) {
        Role roleEntity =  roleMapper.mapFrom(role);    //convert Dto object to Entity
        Role savedRoleEntity = roleService.createRole(roleEntity);  //create and save object Entity
        return new ResponseEntity<>(
                roleMapper.mapTo(savedRoleEntity),
                HttpStatus.CREATED
        );   //convert from Entity to Dto and return
    }

}
