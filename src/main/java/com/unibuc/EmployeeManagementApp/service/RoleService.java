package com.unibuc.EmployeeManagementApp.service;

import com.unibuc.EmployeeManagementApp.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    //Create Role
    Role saveRole(Role roleEntity);

    //Read all Roles
    List<Role> findAllRoles();

    //Read one Role
    Optional<Role> findOneRole(Long id);

    //Check if Role exists
    boolean roleExists(Long id);

}
