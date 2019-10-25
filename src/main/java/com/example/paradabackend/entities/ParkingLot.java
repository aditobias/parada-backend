package com.example.paradabackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String parkingLotName;
    private String location;
    private Integer capacity;
    private Integer flatRate;
    private Integer ratePerHour;
    private Integer succeedingHourRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(Integer flatRate) {
        this.flatRate = flatRate;
    }

    public Integer getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(Integer ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public Integer getSucceedingHourRate() {
        return succeedingHourRate;
    }

    public void setSucceedingHoursRate(Integer succeedingHourRate) {
        this.succeedingHourRate = succeedingHourRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parkingLotName, that.parkingLotName) &&
                Objects.equals(location, that.location) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(flatRate, that.flatRate) &&
                Objects.equals(ratePerHour, that.ratePerHour) &&
                Objects.equals(succeedingHourRate, that.succeedingHourRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parkingLotName, location, capacity, flatRate, ratePerHour, succeedingHourRate);
    }
}
