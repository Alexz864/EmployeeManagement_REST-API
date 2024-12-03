package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.repository.RoleRepository;
import com.unibuc.EmployeeManagementApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired  //inject RoleRepository
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role roleEntity) {
        return roleRepository.save(roleEntity);
    }

//    public List<Role> getAllRoles() {
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public Optional<Role> getRoleById(Long id) {
//        return roleRepository.findById(id);
//    }
//
//    @Override
//    public Role getRoleByRoleName(String roleName) {
//        return roleRepository.findByRoleName(roleName);
//    }
//
//    @Override
//    public Role updateRole(Long id, Role role) {
//        Optional<Role> existingRole = roleRepository.findById(id);
//        if(existingRole.isPresent()) {
//            Role updatedRole = existingRole.get();
//            updatedRole.setRoleName(role.getRoleName());
//            updatedRole.setEmployees(role.getEmployees());
//            updatedRole.setUsers(role.getUsers());
//            return roleRepository.save(updatedRole);
//        } else {
//            throw new RuntimeException("Role not found with ID: " + id);
//        }
//    }
//
//    @Override
//    public void deleteRole(Long id) {
//        roleRepository.deleteById(id);
//    }
}
