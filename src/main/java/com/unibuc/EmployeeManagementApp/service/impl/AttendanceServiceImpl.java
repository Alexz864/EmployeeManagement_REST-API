package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.repository.AttendanceRepository;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.service.AttendanceService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;


    //Inject Beans in constructor
    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository
            ) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    //Create Attendance
    @Override
    public Attendance createAttendance(Attendance attendanceEntity) {

        //Ensure Employee existence before assigning to the Attendance
        Employee employee = employeeRepository.findByEmail(attendanceEntity.getEmployee().getEmail())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(attendanceEntity.getEmployee().getEmail())
                );

        //Assign the resolved Employee to the Attendance
        attendanceEntity.setEmployee(employee);

        //Save the Attendance
        return attendanceRepository.save(attendanceEntity);
    }

    //Read all Attendances
    @Override
    public List<Attendance> findAllAttendances() {
        return attendanceRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    //Read one Employee
    @Override
    public Optional<Attendance> findOneAttendance(Long id) {
        return attendanceRepository.findById(id);
    }

    //Full update Attendance
    @Override
    public Attendance fullUpdateAttendance(Long id, Attendance attendanceEntity) {
        attendanceEntity.setId(id);

        return attendanceRepository.findById(id).map(existingAttendance -> {
            existingAttendance.setAttendanceDate(attendanceEntity.getAttendanceDate());
            existingAttendance.setPresent(attendanceEntity.getPresent());

            Employee existingEmployee = employeeRepository.findByEmail(
                    attendanceEntity.getEmployee().getEmail()
            ).orElseThrow(() -> new EmployeeNotFoundException("")
            );
            existingAttendance.setEmployee(existingEmployee);

            return attendanceRepository.save(existingAttendance);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }

    //Delete Attendance
    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
