package com.techelevator.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MessageDTO {

    @NotNull
    private String message;

    private int petId;


    public MessageDTO(@NotNull String message, int petId) {
        this.message = message;
        this.petId = petId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

}
