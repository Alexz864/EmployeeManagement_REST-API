package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements Mapper<Role, RoleDto> {

    private final ModelMapper modelMapper;  //Inject ModelMapper

    //Constructor
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public RoleDto mapTo(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    //Convert Dto object to Entity object
    @Override
    public Role mapFrom(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
}
