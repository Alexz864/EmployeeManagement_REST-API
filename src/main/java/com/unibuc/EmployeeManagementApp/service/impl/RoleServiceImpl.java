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

    @Autowired  //Inject RoleRepository
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override   //Create Role
    public Role saveRole(Role roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findOneRole(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return roleRepository.existsById(id);
    }
}
