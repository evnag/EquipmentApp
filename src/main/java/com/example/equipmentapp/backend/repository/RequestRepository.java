package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findRequestByCompletedIsFalse();

    List<Request> findRequestByCompletedIsTrue();
}
