package com.unibuc.EmployeeManagementApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

//Create Attendance model in the database
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as an entity
@Builder
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Many Attendances can be assigned to One Employee
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @JsonBackReference // Prevents circular reference during serialization
    private Employee employee;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private Boolean present;
}