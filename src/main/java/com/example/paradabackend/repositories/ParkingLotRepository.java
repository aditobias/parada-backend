package com.example.paradabackend.repositories;

import com.example.paradabackend.entities.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {
    ParkingLot findByParkingLotName(String parkingLotName);
}
