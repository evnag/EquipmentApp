package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c " +
            "where lower(c.name) like lower(concat('%', :searchText, '%') ) ")
    List<Category> search(@Param("searchText") String filterText);
}
