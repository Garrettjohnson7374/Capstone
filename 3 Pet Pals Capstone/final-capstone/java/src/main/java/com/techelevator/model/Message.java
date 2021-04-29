package com.techelevator.model;

import java.time.LocalDateTime;

public class Message {

    private long messageId;
    private long userId;
    private String message;
    private LocalDateTime postedDate;
    private Pet pet;
    private String name;
    private String pic;

    public Message() {}

    public Message(long messageId, long userId, String message, LocalDateTime postedDate, long petId, Pet pet, String name, String pic) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
        this.postedDate = postedDate;
        this.pet = pet;
        this.name = name;
        this.pic = pic;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
