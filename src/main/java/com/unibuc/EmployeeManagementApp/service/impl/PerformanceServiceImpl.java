package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.repository.PerformanceRepository;
import com.unibuc.EmployeeManagementApp.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    //Inject PerformanceRepository Bean in constructor
    @Autowired
    public PerformanceServiceImpl(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    //Create Performance
    @Override
    public Performance createPerformance(Performance performanceEntity) {
        return performanceRepository.save(performanceEntity);
    }
}
