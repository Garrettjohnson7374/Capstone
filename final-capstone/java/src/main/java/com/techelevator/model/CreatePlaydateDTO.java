package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Time;
import java.time.LocalDateTime;

public class CreatePlaydateDTO {

    @NotNull
    private Pet pet;
    @NotEmpty
    private String address;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String zip;
    @NotNull
    private LocalDateTime date;

//    public CreatePlaydateDTO() {}
//    public CreatePlaydateDTO(@NotNull long petId, @NotEmpty String address, @NotEmpty String city, @NotEmpty String state, @NotEmpty String zip, @NotNull TimeStamp date) {
//        this.petId = petId;
//        this.address = address;
//        this.city = city;
//        this.state = state;
//        this.zip = zip;
//        this.date = date;
//    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
