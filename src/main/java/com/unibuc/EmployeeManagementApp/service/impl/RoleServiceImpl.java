package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.repository.RoleRepository;
import com.unibuc.EmployeeManagementApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    //Inject RoleRepository Bean in constructor
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //Create Role
    @Override
    public Role saveRole(Role roleEntity) {
        return roleRepository.save(roleEntity);
    }

    //Read all Roles
    @Override
    public List<Role> findAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    //Read one Role
    @Override
    public Optional<Role> findOneRole(Long id) {
        return roleRepository.findById(id);
    }

    //Check if Role exists
    @Override
    public boolean roleExists(Long id) {
        return roleRepository.existsById(id);
    }

    //Delete Role
    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
