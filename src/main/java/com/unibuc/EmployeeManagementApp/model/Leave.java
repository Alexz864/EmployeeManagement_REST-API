package com.unibuc.EmployeeManagementApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

//Create Leave model in the database
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as an entity
@Builder
@Table(name = "leaves")
@SuppressWarnings("unused")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String reason;

    //Many Leave records can be assigned to an Employee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference // Prevents circular reference during serialization
    private Employee employee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    public enum LeaveStatus {
        PENDING,    // The leave request is awaiting approval
        APPROVED,   // The leave request has been approved
        REJECTED    // The leave request has been rejected
    }
}