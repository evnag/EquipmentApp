package com.example.equipmentapp.backend.repository;

import com.example.equipmentapp.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.unitId=:unitId")
    List<Transaction> findAllByUnitId(@Param("unitId") Long unitId);

}
