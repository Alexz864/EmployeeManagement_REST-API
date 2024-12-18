package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Roles endpoint
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    private final Mapper<Role, RoleDto> roleMapper;

    //Inject RoleService & RoleMapper Beans in constructor
    public RoleController(RoleService roleService, Mapper<Role, RoleDto> roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    //Create Role
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role roleEntity =  roleMapper.mapFrom(roleDto);    //Convert Dto to Entity
        Role savedRoleEntity = roleService.saveRole(roleEntity);  //Create Role Entity
        RoleDto savedRoleDto = roleMapper.mapTo(savedRoleEntity);   //Convert Entity to Dto
        return new ResponseEntity<>(
                savedRoleDto,
                HttpStatus.CREATED
        );
    }

    //Read all Roles
    @GetMapping
    public List<RoleDto> listRoles() {
        List<Role> roles = roleService.findAllRoles();
        return roles.stream()
                .map(roleMapper::mapTo)
                .collect(Collectors.toList());
    }

    //Read one Role
    @GetMapping(path = "{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("id") Long id) {   //Reference the id passed in the URL
        Optional<Role> foundRole = roleService.findOneRole(id);

        //If it finds a RoleEntity, convert it to Dto
        return foundRole.map(role -> {
            RoleDto roleDto = roleMapper.mapTo(role);
            return new ResponseEntity<>(roleDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Full update Role
    @PutMapping(path = "{id}")
    public ResponseEntity<RoleDto> fullUpdateRole(
            @PathVariable("id") Long id,
            @RequestBody RoleDto roleDto) {
         //Check if the Role exists
         if(!roleService.roleExists(id)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         roleDto.setId(id);
         Role roleEntity = roleMapper.mapFrom(roleDto); //Convert Dto to Entity
         Role savedRoleEntity = roleService.saveRole(roleEntity);   //Update Role Entity
         RoleDto savedRoleDto = roleMapper.mapTo(savedRoleEntity);  //Convert Entity to Dto
        
         return new ResponseEntity<>(
                 savedRoleDto,
                 HttpStatus.OK
         );
    }
}
