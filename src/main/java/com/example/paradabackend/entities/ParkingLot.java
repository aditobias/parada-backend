package com.example.paradabackend.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String parkingLotName;
    private String location;

    @Min(value = 0)
    private int capacity;
    @Min(value = 0)
    private int availableSpaces;
    @Min(value = 0)
    private int flatRate;
    @Min(value = 0)
    private int maxSpacePerLevel;

    private Integer ratePerHour;
    private Integer succeedingHourRate;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<ParkingSpace> parkingSpaceList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public void setAvailableSpaces(int availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public int getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(int flatRate) {
        this.flatRate = flatRate;
    }

    public int getMaxSpacePerLevel() {
        return maxSpacePerLevel;
    }

    public void setMaxSpacePerLevel(int maxSpacePerLevel) {
        this.maxSpacePerLevel = maxSpacePerLevel;
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

    public void setSucceedingHourRate(Integer succeedingHourRate) {
        this.succeedingHourRate = succeedingHourRate;
    }

    public List<ParkingSpace> getParkingSpaceList() {
        return parkingSpaceList;
    }

    public void setParkingSpaceList(List<ParkingSpace> parkingSpaceList) {
        this.parkingSpaceList = parkingSpaceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return id == that.id &&
                capacity == that.capacity &&
                availableSpaces == that.availableSpaces &&
                flatRate == that.flatRate &&
                maxSpacePerLevel == that.maxSpacePerLevel &&
                Objects.equals(parkingLotName, that.parkingLotName) &&
                Objects.equals(location, that.location) &&
                Objects.equals(ratePerHour, that.ratePerHour) &&
                Objects.equals(succeedingHourRate, that.succeedingHourRate) &&
                Objects.equals(parkingSpaceList, that.parkingSpaceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parkingLotName, location, capacity, availableSpaces, flatRate, maxSpacePerLevel, ratePerHour, succeedingHourRate, parkingSpaceList);
    }
}
