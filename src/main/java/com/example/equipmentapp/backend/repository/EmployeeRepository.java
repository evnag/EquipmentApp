package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e " +
            "where lower(e.lastName) like lower(concat('%', :searchText, '%') ) ")
    List<Employee> search(@Param("searchText") String searchText);
}
