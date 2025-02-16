package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Performance;

import java.util.List;
import java.util.Optional;

public interface PerformanceService {

    //Create Performance
    Performance createPerformance(Performance performanceEntity);

    //Read all Performances
    List<Performance> findAllPerformances();

    //Read one Performance
    Optional<Performance> findOnePerformance(Long id);

    //Performance full update
    Performance fullUpdatePerformance(Long id, Performance performanceEntity);

    //Delete Performance
    void deletePerformance(Long id);
}
