package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.repository.AttendanceRepository;
import com.unibuc.EmployeeManagementApp.service.AttendanceService;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    //Inject AttendanceRepository Bean in constructor
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Attendance createAttendance(Attendance attendanceEntity) {
        return attendanceRepository.save(attendanceEntity);
    }
}
