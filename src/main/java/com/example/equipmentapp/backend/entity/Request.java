package com.example.equipmentapp.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "requests")
public class Request {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "request_date")
    private LocalDate date;
    @Column(name = "description")
    private String description;
    @Column(name = "completed", columnDefinition = "boolean default false")
    private Boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
