package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Pet {

    private long petId;
    private String name;
    private long userId;
    private String species;
    private String breed;
    private int weight;
    private int birthYear;
    private String energeticRelaxed;
    private String shyFriendly;
    private String apatheticCurious;
    private String bio;
    private String pic;

    public Pet() {
    }

    public Pet(long petId, String name, long userId, String species, String breed, int weight, int birthYear, String energeticRelaxed, String shyFriendly, String apatheticCurious, String bio, String pic) {
        this.petId = petId;
        this.name = name;
        this.userId = userId;
        this.species = species;
        this.breed = breed;
        this.weight = weight;
        this.birthYear = birthYear;
        this.energeticRelaxed = energeticRelaxed;
        this.shyFriendly = shyFriendly;
        this.apatheticCurious = apatheticCurious;
        this.bio = bio;
        this.pic = pic;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getEnergeticRelaxed() {
        return energeticRelaxed;
    }

    public void setEnergeticRelaxed(String energeticRelaxed) {
        this.energeticRelaxed = energeticRelaxed;
    }

    public String getShyFriendly() {
        return shyFriendly;
    }

    public void setShyFriendly(String shyFriendly) {
        this.shyFriendly = shyFriendly;
    }

    public String getApatheticCurious() {
        return apatheticCurious;
    }

    public void setApatheticCurious(String apatheticCurious) {
        this.apatheticCurious = apatheticCurious;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}


