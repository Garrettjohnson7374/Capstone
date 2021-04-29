package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PetDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String species;
    private String breed;
    @NotNull
    @Positive
    private int weight;
    @NotNull
    @Positive
    //@Max(Long.valueOf(LocalDate.now().getYear())) "value must be constant"
    private int birthYear;
    @NotEmpty
    private String energeticRelaxed;
    @NotEmpty
    private String shyFriendly;
    @NotEmpty
    private String apatheticCurious;
    private String bio;
    private String pic;

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
}
