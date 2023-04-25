package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query("select o from Office o " +
            "where lower(o.description) like lower(concat('%', :searchText, '%') ) ")
    List<Office> search(@Param("searchText") String searchText);
}
