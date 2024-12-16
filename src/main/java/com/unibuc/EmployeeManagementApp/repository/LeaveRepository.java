package com.unibuc.EmployeeManagementApp.repository;

import com.unibuc.EmployeeManagementApp.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Extends JpaRepository for Leave, ID type Long
public interface LeaveRepository extends JpaRepository<Leave, Long> {
}
