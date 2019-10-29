package com.example.paradabackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Driver {
    @Id
    private String username;

    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String emailVerificationStatus;
    private String profilePicture;
    private String driverType;

    public Driver() {
    }

    public Driver(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(String emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(username, driver.username) &&
                Objects.equals(password, driver.password) &&
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(lastName, driver.lastName) &&
                Objects.equals(email, driver.email) &&
                Objects.equals(mobileNumber, driver.mobileNumber) &&
                Objects.equals(emailVerificationStatus, driver.emailVerificationStatus) &&
                Objects.equals(profilePicture, driver.profilePicture) &&
                Objects.equals(driverType, driver.driverType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstName, lastName, email, mobileNumber, emailVerificationStatus, profilePicture, driverType);
    }
}
