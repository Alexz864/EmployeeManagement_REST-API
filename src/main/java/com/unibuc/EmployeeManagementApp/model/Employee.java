package com.unibuc.EmployeeManagementApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

//Create Employee model in the database
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as an entity
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

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String designation;

    //Many employees can have the same Role
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    //One employee has One Salary
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference   //Marks the parent side of the relationship
    private Salary salary;

    //One employee can have Many Attendance records
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference   //Marks the parent side of the relationship
    private List<Attendance> attendances;

    //One employee can have Many Leave records
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference   //Marks the parent side of the relationship
    private List<Leave> leaves;

    //One employee can have Many Performance records
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference   //Marks the parent side of the relationship
    private List<Performance> performances;

    //One Employee can be linked to a User
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference   //Marks the parent side of the relationship
    private User user;
}