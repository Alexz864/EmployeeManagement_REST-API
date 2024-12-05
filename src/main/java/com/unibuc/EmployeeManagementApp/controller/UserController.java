package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.UserDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.User;
import com.unibuc.EmployeeManagementApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    private final Mapper<User, UserDto> userMapper;

    //Inject UserService & UserMapper to constructor
    public UserController(UserService userService, Mapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")   //Define create User endpoint
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User userEntity = userMapper.mapFrom(userDto);  //Convert Dto object to Entity object
        User savedUserEntity = userService.createUser(userEntity); //Create User Entity object
        UserDto savedUserDto = userMapper.mapTo(savedUserEntity); //Convert Entity object to Dto object
        return new ResponseEntity<>(
                savedUserDto,
                HttpStatus.CREATED
        );
    }
}
