package com.example.paradabackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class ParkingSpace {

    @Id
    private String id;
    private String parkingLotName;
    private Integer parkingLevel;
    private String parkingPosition;
    private Boolean isOccupied;


    public String getId() {
        return id;
    }

    public void setId(String id ) {
        this.id = id;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public Integer getParkingLevel() {
        return parkingLevel;
    }

    public void setParkingPosition(String parkingPosition) {
        this.parkingPosition = parkingPosition;
    }

    public String getParkingPosition() {
        return parkingPosition;
    }

    public void setParkingLevel(Integer parkingLevel) {
        this.parkingLevel = parkingLevel;
    }

    public Boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpace that = (ParkingSpace) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parkingLotName, that.parkingLotName) &&
                Objects.equals(parkingLevel, that.parkingLevel) &&
                Objects.equals(parkingPosition, that.parkingPosition) &&
                Objects.equals(isOccupied, that.isOccupied);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parkingLotName, parkingLevel, parkingPosition, isOccupied);
    }
}
