package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.Unit;
import com.example.equipmentapp.backend.exception.UnitNotFoundException;
import com.example.equipmentapp.backend.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public List<Unit> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return unitRepository.findAll();
        } else {
            return unitRepository.search(filterText);
        }
    }

    public long count() {
        return unitRepository.count();
    }

    public void save(Unit unit) {
        if (unit == null) {
            return;
        }
        unitRepository.save(unit);
    }

    public void delete(Unit unit) {
        unitRepository.delete(unit);
    }

    public Unit getById(Long unitId) {
        return unitRepository.getUnitById(unitId).orElseThrow(() -> new UnitNotFoundException(unitId));
    }
}
