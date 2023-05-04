package com.example.equipmentapp.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "unit")
public class Unit {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model")
    private String model;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "inventory_number")
    private String inventoryNumber;
    @Column(name = "income_date")
    private LocalDate incomeDate;
    @Column(name = "outcome_date")
    private LocalDate outcomeDate;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"units"})
    private Category categoryId;
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office officeId;
    @OneToMany(mappedBy = "unitId")
    private List<Transaction> transactions;

}
