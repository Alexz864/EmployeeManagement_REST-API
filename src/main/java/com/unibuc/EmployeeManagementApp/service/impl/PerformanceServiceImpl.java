package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.repository.PerformanceRepository;
import com.unibuc.EmployeeManagementApp.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Autowired  //Inject PerformanceRepository
    public PerformanceServiceImpl(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Override
    public Performance createPerformance(Performance performanceEntity) {
        return performanceRepository.save(performanceEntity);
    }
}
