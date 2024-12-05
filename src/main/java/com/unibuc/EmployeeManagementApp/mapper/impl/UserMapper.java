package com.unibuc.EmployeeManagementApp.mapper.impl;

import com.unibuc.EmployeeManagementApp.dto.UserDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final ModelMapper modelMapper;

    //Inject ModelMapper in constructor
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Convert Entity object to Dto object
    @Override
    public UserDto mapTo(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    //Convert Dto object to Entity object
    @Override
    public User mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
