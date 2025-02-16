package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.PerformanceRepository;
import com.unibuc.EmployeeManagementApp.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    private final EmployeeRepository employeeRepository;

    //Inject PerformanceRepository Bean in constructor
    @Autowired
    public PerformanceServiceImpl(
            PerformanceRepository performanceRepository,
            EmployeeRepository employeeRepository
    ) {
        this.performanceRepository = performanceRepository;
        this.employeeRepository = employeeRepository;
    }

    //Create Performance
    @Override
    public Performance createPerformance(Performance performanceEntity) {
        //Ensure Employee existence before assigning to the Performance
        Employee employee = employeeRepository.findByEmail(performanceEntity.getEmployee().getEmail())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(performanceEntity.getEmployee().getEmail())
                );

        //Assign the resolved Employee to the Performance
        performanceEntity.setEmployee(employee);

        //Save the Performance
        return performanceRepository.save(performanceEntity);
    }

    //Read all Performances
    @Override
    public List<Performance> findAllPerformances() {
        return performanceRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    //Read one Performance
    @Override
    public Optional<Performance> findOnePerformance(Long id) {
        return performanceRepository.findById(id);
    }

    //Full update Performance
    @Override
    public Performance fullUpdatePerformance(Long id, Performance performanceEntity) {
        performanceEntity.setId(id);

        return performanceRepository.findById(id).map(existingPerformance -> {
            existingPerformance.setReviewDate(performanceEntity.getReviewDate());
            existingPerformance.setRating(performanceEntity.getRating());
            existingPerformance.setComments(performanceEntity.getComments());

            Employee existingEmployee = employeeRepository.findByEmail(
                    performanceEntity.getEmployee().getEmail()
            ).orElseThrow(() -> new EmployeeNotFoundException("")
            );
            existingPerformance.setEmployee(existingEmployee);

            return performanceRepository.save(existingPerformance);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }

    //Delete Performance
    @Override
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }
}
