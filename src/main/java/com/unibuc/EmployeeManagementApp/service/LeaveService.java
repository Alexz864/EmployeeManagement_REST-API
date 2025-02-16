package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Leave;

import java.util.List;
import java.util.Optional;

public interface LeaveService {

    //Create Leave
    Leave createLeave(Leave leaveEntity);

    //Read all Leaves
    List<Leave> findAllLeaves();

    //Read one Leave
    Optional<Leave> findOneLeave(Long id);

    //Leave full update
    Leave fullUpdateLeave(Long id, Leave leaveEntity);

    //Delete Leave
    void deleteLeave(Long id);
}
