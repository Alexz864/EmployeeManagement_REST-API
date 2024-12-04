package com.unibuc.EmployeeManagementApp.repository;

import com.unibuc.EmployeeManagementApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Extends JpaRepository for Employee, ID type Long
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
