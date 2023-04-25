package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    @Query("select a from Authority a where a.username =:username")
    Authority getByUserName(@Param("username") String username);
}
