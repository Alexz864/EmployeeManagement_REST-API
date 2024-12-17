package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(Role roleEntity);

    List<Role> findAllRoles();

    Optional<Role> findOneRole(Long id);
//
//    Role getRoleByRoleName(String roleName);
//
//    Role updateRole(Long id, Role role);
//
//    void deleteRole(Long id);
}
