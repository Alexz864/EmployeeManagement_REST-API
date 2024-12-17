package com.unibuc.EmployeeManagementApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Create User model in the database
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    //Many Users can have the same Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //One User is identified as One Employee
    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference // Prevents circular reference during serialization
    private Employee employee;

}
