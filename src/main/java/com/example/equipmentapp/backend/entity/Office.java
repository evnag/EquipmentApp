package com.example.equipmentapp.backend.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "office")
public class Office {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "office_number")
    private String officeNumber;
    @Column(name = "building_number")
    private String buildingNumber;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "officeId", fetch = FetchType.EAGER)
    private List<Unit> units;

    public String getFullData() {
        return this.buildingNumber + "-" + this.officeNumber;
    }
}
