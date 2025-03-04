package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Attendance;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    //Create Attendance
    Attendance createAttendance(Attendance attendanceEntity);

    //Read all Attendances
    List<Attendance> findAllAttendances();

    //Read one Attendance
    Optional<Attendance> findOneAttendance(Long id);

    //Attendance full update
    Attendance fullUpdateAttendance(Long id, Attendance attendanceEntity);

    //Delete Attendance
    void deleteAttendance(Long id);

}
