package com.techelevator.controller;

import com.techelevator.dao.MessageDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Attendee;
import com.techelevator.model.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.techelevator.model.Message;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class MessageController {

    private MessageDAO messageDAO;
    private UserDAO userDAO;

    public MessageController(MessageDAO messageDAO, UserDAO userDAO) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/messages", method = RequestMethod.POST)
    public long register(@Valid @RequestBody MessageDTO messageDTO, Principal principal) {
        int userId = userDAO.findIdByUsername(principal.getName());
        LocalDateTime postedDate = LocalDateTime.now();
        return messageDAO.create(messageDTO, userId, postedDate);
    }

    @RequestMapping(path = "/messages/{id}", method = RequestMethod.GET)
    public List<Message> getMessageByUserId(@PathVariable int id) {
        //int userId = userDAO.findIdByUsername(id.getName());
        return messageDAO.getMessageByUserId(id);
    }

    @RequestMapping(path = "/messages", method = RequestMethod.GET)
    public List<Message> getMessages() {
        return messageDAO.getMessages();
    }
}
