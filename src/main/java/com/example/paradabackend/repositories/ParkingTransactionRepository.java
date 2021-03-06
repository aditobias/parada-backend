package com.example.paradabackend.repositories;

import com.example.paradabackend.entities.ParkingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {
    Optional<ParkingTransaction> findById(Long id);

    List<ParkingTransaction> findAllByUsername(String username);

}
