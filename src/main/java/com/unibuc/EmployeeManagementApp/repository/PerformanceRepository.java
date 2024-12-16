package com.unibuc.EmployeeManagementApp.repository;

import com.unibuc.EmployeeManagementApp.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Extends JpaRepository for Performance, ID type Long
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
