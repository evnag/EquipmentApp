package com.example.equipmentapp.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "software")
public class Software {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "software_name")
    private String name;
    @Column(name = "install_date")
    private LocalDate date;

    @OneToMany(mappedBy = "softwareId", fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}
