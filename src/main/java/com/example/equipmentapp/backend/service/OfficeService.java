package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.Office;
import com.example.equipmentapp.backend.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<Office> findAll() {
        return officeRepository.findAll();

    }

    public List<Office> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return officeRepository.findAll();
        } else {
            return officeRepository.search(filterText);
        }
    }

    public long count() {
        return officeRepository.count();
    }

    public void save(Office office) {
        if (office == null) {
            return;
        }
        officeRepository.save(office);
    }

    public void delete(Office office) {
        officeRepository.delete(office);
    }
}
