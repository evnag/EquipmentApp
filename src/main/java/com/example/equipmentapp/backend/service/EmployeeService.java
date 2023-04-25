package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.Employee;
import com.example.equipmentapp.backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return employeeRepository.findAll();
        } else {
            return employeeRepository.search(filterText);
        }
    }

    public long count() {
        return employeeRepository.count();
    }

    public void save(Employee employee) {
        if (employee == null) {
            return;
        }
        employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
