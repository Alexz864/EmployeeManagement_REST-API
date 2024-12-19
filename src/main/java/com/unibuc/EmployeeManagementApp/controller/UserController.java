package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.UserDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.User;
import com.unibuc.EmployeeManagementApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@SuppressWarnings("unused")
public class UserController {

    private final UserService userService;

    private final Mapper<User, UserDto> userMapper;

    //Inject UserService & UserMapper Beans in constructor
    public UserController(UserService userService, Mapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    //Create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User userEntity = userMapper.mapFrom(userDto);  //Convert Dto to Entity
        User savedUserEntity = userService.createUser(userEntity); //Create User Entity
        UserDto savedUserDto = userMapper.mapTo(savedUserEntity); //Convert Entity to Dto
        
        return new ResponseEntity<>(
                savedUserDto,
                HttpStatus.CREATED
        );
    }
}
