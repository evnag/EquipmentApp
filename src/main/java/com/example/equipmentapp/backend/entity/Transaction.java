package com.example.equipmentapp.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "transaction")
public class Transaction {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "action_name")
    private String action;
    @Column(name = "transaction_date")
    private LocalDate date;
    @Column(name = "asset")
    private String asset;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unitId;
    @ManyToOne
    @JoinColumn(name = "software_id")
    private Software softwareId;

    public Long getId() {
        return this.id;
    }

    public String getAction() {
        return this.action;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getAsset() {
        return this.asset;
    }

    public Unit getUnitId() {
        return this.unitId;
    }

    public Software getSoftwareId() {
        return this.softwareId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
    }

    public void setSoftwareId(Software softwareId) {
        this.softwareId = softwareId;
    }

    public String toString() {
        return "Transaction(id=" + this.getId() + ", action=" + this.getAction() + ", date=" + this.getDate() + ", asset=" + this.getAsset() + ", unitId=" + this.getUnitId() + ", softwareId=" + this.getSoftwareId() + ")";
    }
}
