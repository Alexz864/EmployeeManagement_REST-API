package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.repository.SalaryRepository;
import com.unibuc.EmployeeManagementApp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;

    @Autowired  //Inject SalaryRepository
    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override   //Create Salary
    public Salary createSalary(Salary salaryEntity) {
        return salaryRepository.save(salaryEntity);
    }
}
