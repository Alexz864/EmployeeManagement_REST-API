package com.unibuc.EmployeeManagementApp.repository;

import com.unibuc.EmployeeManagementApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository //Extends JpaRepository for Role, ID type Long
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}