package com.example.paradabackend.repositories;

import com.example.paradabackend.entities.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, String> {
    List<ParkingSpace> findAllByParkingLotName(String parkingLotName);

    ParkingSpace findByParkingLotNameAndParkingPosition(String parkingLotName, String parkingPosition);
}
