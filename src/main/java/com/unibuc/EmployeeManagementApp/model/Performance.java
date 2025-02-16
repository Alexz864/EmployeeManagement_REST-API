package com.unibuc.EmployeeManagementApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

//Create Performance model in the database
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define the class as an entity
@Builder
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate reviewDate;

    @Column(nullable = false)
    private Integer rating; //1 to 5 scale

    @Column(length = 1000)
    private String comments;

    //Many Performance records can be assigned to an Employee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
