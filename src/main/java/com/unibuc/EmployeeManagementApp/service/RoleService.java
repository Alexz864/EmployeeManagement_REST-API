package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role saveRole(Role roleEntity);

    List<Role> findAllRoles();

    Optional<Role> findOneRole(Long id);

    boolean isExists(Long id);
//
//    Role getRoleByRoleName(String roleName);
//
//    Role updateRole(Long id, Role role);
//
//    void deleteRole(Long id);
}
