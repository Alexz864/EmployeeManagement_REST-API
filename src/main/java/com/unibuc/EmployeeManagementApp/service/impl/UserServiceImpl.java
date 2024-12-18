package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.User;
import com.unibuc.EmployeeManagementApp.repository.UserRepository;
import com.unibuc.EmployeeManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //Inject UserRepository Bean in constructor
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Create User
    @Override
    public User createUser(User userEntity) {
        return userRepository.save(userEntity);
    }
}
