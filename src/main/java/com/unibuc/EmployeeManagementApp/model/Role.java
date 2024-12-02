//Create Role model in the database
package com.unibuc.EmployeeManagementApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;

    //One Role can have Many Employees
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Employee> employees;

    //One Role can have Many Users
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;
}