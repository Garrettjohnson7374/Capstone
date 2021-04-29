package com.techelevator.dao;

import com.techelevator.model.MessageDTO;
import com.techelevator.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageDAO {

    List<Message> getMessages ();

    List<Message> getMessageByUserId(int userId);

    long create(MessageDTO messageDTO, int userId, LocalDateTime date);
}
