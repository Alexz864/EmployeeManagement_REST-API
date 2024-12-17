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

@RestController
@RequestMapping("/roles")   //Roles endpoint
public class RoleController {

    private final RoleService roleService;

    private final Mapper<Role, RoleDto> roleMapper;

    //Inject RoleService & RoleMapper Beans to constructor
    public RoleController(RoleService roleService, Mapper<Role, RoleDto> roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping    //Create
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role roleEntity =  roleMapper.mapFrom(roleDto);    //Convert Dto object to Entity object
        Role savedRoleEntity = roleService.saveRole(roleEntity);  //Create Role Entity object
        RoleDto savedRoleDto = roleMapper.mapTo(savedRoleEntity);   //Convert Entity object to Dto object
        return new ResponseEntity<>(
                savedRoleDto,
                HttpStatus.CREATED
        );
    }

    @GetMapping     //Read all
    public List<RoleDto> listRoles() {
        List<Role> roles = roleService.findAllRoles();
        return roles.stream()
                .map(roleMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")    //Read one
    public ResponseEntity<RoleDto> getRole(@PathVariable("id") Long id) {   //Reference the id passed in the URL
        Optional<Role> foundRole = roleService.findOneRole(id);
        return foundRole.map(role -> {  //If it finds a RoleEntity, convert it to RoleDto
            RoleDto roleDto = roleMapper.mapTo(role);
            return new ResponseEntity<>(roleDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "{id}")      //Full update
    public ResponseEntity<RoleDto> fullUpdateRole(
            @PathVariable("id") Long id,
            @RequestBody RoleDto roleDto) {
         if(!roleService.isExists(id)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         roleDto.setId(id);
         Role roleEntity = roleMapper.mapFrom(roleDto);
         Role savedRoleEntity = roleService.saveRole(roleEntity);
         RoleDto savedRoleDto = roleMapper.mapTo(savedRoleEntity);
         return new ResponseEntity<>(
                 savedRoleDto,
                 HttpStatus.OK
         );
    }
}
