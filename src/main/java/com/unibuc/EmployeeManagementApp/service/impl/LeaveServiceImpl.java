package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.LeaveRepository;
import com.unibuc.EmployeeManagementApp.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;

    private final EmployeeRepository employeeRepository;

    //Inject LeaveRepository Bean in constructor
    @Autowired
    public LeaveServiceImpl(
            LeaveRepository leaveRepository,
            EmployeeRepository employeeRepository
    ) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    //Create Leave
    @Override
    public Leave createLeave(Leave leaveEntity) {

        //Ensure Employee existence before assigning to the Leave
        Employee employee = employeeRepository.findByEmail(leaveEntity.getEmployee().getEmail())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(leaveEntity.getEmployee().getEmail())
                );

        //Assign the resolved Employee to the Leave
        leaveEntity.setEmployee(employee);

        //Set status PENDING by default
        leaveEntity.setStatus(Leave.LeaveStatus.PENDING);

        //Save the Leave
        return leaveRepository.save(leaveEntity);
    }

    //Read all Leaves
    @Override
    public List<Leave> findAllLeaves() {
        return leaveRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    //Read one Leave
    @Override
    public Optional<Leave> findOneLeave(Long id) {
        return leaveRepository.findById(id);
    }

    //Full update Leave
    @Override
    public Leave fullUpdateLeave(Long id, Leave leaveEntity) {
        leaveEntity.setId(id);

        return leaveRepository.findById(id).map(existingLeave -> {
            existingLeave.setStartDate(leaveEntity.getStartDate());
            existingLeave.setEndDate(leaveEntity.getEndDate());
            existingLeave.setReason(leaveEntity.getReason());
            existingLeave.setStatus(leaveEntity.getStatus());

            Employee existingEmployee = employeeRepository.findByEmail(
                    leaveEntity.getEmployee().getEmail()
            ).orElseThrow(() -> new EmployeeNotFoundException("")
            );
            existingLeave.setEmployee(existingEmployee);

            return leaveRepository.save(existingLeave);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
