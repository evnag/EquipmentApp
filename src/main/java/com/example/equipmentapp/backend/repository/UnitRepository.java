package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query("select u from Unit u " +
            "where lower(u.model) like lower(concat('%', :searchText, '%') ) ")
    List<Unit> search(@Param("searchText") String searchText);
}
