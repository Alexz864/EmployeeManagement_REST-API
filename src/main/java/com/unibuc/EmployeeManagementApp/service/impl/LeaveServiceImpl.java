package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.repository.LeaveRepository;
import com.unibuc.EmployeeManagementApp.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;

    //Inject LeaveRepository Bean in constructor
    @Autowired
    public LeaveServiceImpl(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    //Create Leave
    @Override
    public Leave createLeave(Leave leaveEntity) {
        return leaveRepository.save(leaveEntity);
    }
}
