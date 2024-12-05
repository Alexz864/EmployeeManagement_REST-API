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

    //Inject RoleService & RoleMapper to constructor
    public RoleController(RoleService roleService, Mapper<Role, RoleDto> roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping(path = "/roles")   //Define create Role endpoint
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role roleEntity =  roleMapper.mapFrom(roleDto);    //Convert Dto object to Entity object
        Role savedRoleEntity = roleService.createRole(roleEntity);  //Create Role Entity object
        RoleDto savedRoleDto = roleMapper.mapTo(savedRoleEntity);   //Convert Entity object to Dto object
        return new ResponseEntity<>(
                savedRoleDto,
                HttpStatus.CREATED
        );
    }

}
