package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Salary;

import java.util.List;
import java.util.Optional;

public interface SalaryService {

    //Create Salary
    Salary createSalary(Salary salaryEntity);

    //Read all Salaries
    List<Salary> findAllSalaries();

    //Read one Salary
    Optional<Salary> findOneSalary(Long id);

    //Salary full update
    Salary fullUpdateSalary(Long id, Salary salaryEntity);

    //Delete Salary
    void deleteSalary(Long id);

}
