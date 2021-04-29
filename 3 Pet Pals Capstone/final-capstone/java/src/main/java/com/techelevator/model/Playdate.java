package com.techelevator.model;

import java.time.LocalDateTime;
import java.util.List;

public class Playdate {

    private long playdateId;

    private Pet pet;

    private List<Attendee> attendeeList;

    private String address;

    private String city;

    private String state;

    private String zip;

    private LocalDateTime date;

    private String lat;

    private String lng;

    private String distanceFromUser;

    private Account owner;

    public Playdate(){}
    public Playdate(long playdateId, Pet pet, List<Attendee> attendeeList, String address, String city, String state, String zip, LocalDateTime date, String lat, String lng, String distanceFromUser, Account owner) {
        this.playdateId = playdateId;
        this.pet = pet;
        this.attendeeList = attendeeList;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.distanceFromUser = distanceFromUser;
        this.owner = owner;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Attendee> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(List<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
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

    public long getPlaydateId() {
        return playdateId;
    }

    public void setPlaydateId(long playdateId) {
        this.playdateId = playdateId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(String distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}


