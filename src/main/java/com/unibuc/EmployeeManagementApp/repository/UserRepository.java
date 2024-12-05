package com.unibuc.EmployeeManagementApp.repository;

import com.unibuc.EmployeeManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Extends JpaRepository for Employee, ID type Long
public interface UserRepository extends JpaRepository<User, Long> {
}
