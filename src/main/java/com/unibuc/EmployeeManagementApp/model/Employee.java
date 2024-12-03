//Create Employee model in the database
package com.unibuc.EmployeeManagementApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as entity
@Builder
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true ,nullable = false)
    private String email;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String department;

    //Many employees can have the same Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //One employee has One Salary
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Salary salary;

    //One employee can have Many Attendance records
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    //One employee can have Many Leave records
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Leave> leaves;

    //One employee can have Many Performance records
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Performance> performances;

    //One Employee can be linked to a User
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private User user;
}