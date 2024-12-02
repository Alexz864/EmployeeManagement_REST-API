//Create User model in the database
package com.unibuc.EmployeeManagementApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    //Many Users can have the same Role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    //One User is identified as One Employee
    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}