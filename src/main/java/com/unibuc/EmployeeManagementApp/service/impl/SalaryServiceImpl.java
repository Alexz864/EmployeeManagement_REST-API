package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.SalaryRepository;
import com.unibuc.EmployeeManagementApp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;

    private final EmployeeRepository employeeRepository;

    //Inject Beans in constructor
    @Autowired
    public SalaryServiceImpl(
            SalaryRepository salaryRepository,
            EmployeeRepository employeeRepository
    ) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    //Create Salary
    @Override
    public Salary createSalary(Salary salaryEntity) {

        //Ensure Employee existence before assigning to the Attendance
        Employee employee = employeeRepository.findByEmail(salaryEntity.getEmployee().getEmail())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(salaryEntity.getEmployee().getEmail())
                );

        //Assign the resolved Employee to the Salary
        salaryEntity.setEmployee(employee);

        //Save the Salary
        return salaryRepository.save(salaryEntity);
    }

    //Read all Salaries
    @Override
    public List<Salary> findAllSalaries() {
        return salaryRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    //Read one Salary
    @Override
    public Optional<Salary> findOneSalary(Long id) {
        return salaryRepository.findById(id);
    }

    //Full update Salary
    @Override
    public Salary fullUpdateSalary(Long id, Salary salaryEntity) {
        salaryEntity.setId(id);

        return salaryRepository.findById(id).map(existingSalary -> {
            existingSalary.setLastPaidDate(salaryEntity.getLastPaidDate());
            existingSalary.setAmount(salaryEntity.getAmount());

            Employee existingEmployee = employeeRepository.findByEmail(
                    salaryEntity.getEmployee().getEmail()
            ).orElseThrow(() -> new EmployeeNotFoundException("")
            );
            existingSalary.setEmployee(existingEmployee);

            return salaryRepository.save(existingSalary);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }

    //Delete Salary
    @Override
    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}
