package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "where lower(u.username) like lower(concat('%', :searchText, '%') ) ")
    List<User> search(@Param("searchText") String filterText);

    @Query("select u from User u where u.username=:username")
    User findByUsername(@Param("username") String username);
}
