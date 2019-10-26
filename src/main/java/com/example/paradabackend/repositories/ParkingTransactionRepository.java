package com.example.paradabackend.repositories;

import com.example.paradabackend.entities.ParkingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {
}
