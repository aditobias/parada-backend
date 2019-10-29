package com.example.paradabackend.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class ParkingTransaction {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String parkingLotName;
    private Integer parkingLevel;
    private String parkingPosition;
    private Integer price;
    private String status;
    private Timestamp reserveTime;
    private Timestamp startTime;
    private Timestamp endTime;

    public ParkingTransaction(String username, String parkingLotName, String parkingPosition) {
        this.username = username;
        this.parkingLotName = parkingLotName;
        this.parkingPosition = parkingPosition;
    }

    public ParkingTransaction(){
    }

    public Timestamp getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Timestamp reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setParkingLevel(Integer parkingLevel) {
        this.parkingLevel = parkingLevel;
    }

    public String getParkingPosition() {
        return parkingPosition;
    }

    public void setParkingPosition(String parkingPosition) {
        this.parkingPosition = parkingPosition;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTransaction that = (ParkingTransaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(parkingLotName, that.parkingLotName) &&
                Objects.equals(parkingLevel, that.parkingLevel) &&
                Objects.equals(parkingPosition, that.parkingPosition) &&
                Objects.equals(price, that.price) &&
                Objects.equals(reserveTime, that.reserveTime) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, parkingLotName, parkingLevel, parkingPosition, price, reserveTime, startTime, endTime);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
