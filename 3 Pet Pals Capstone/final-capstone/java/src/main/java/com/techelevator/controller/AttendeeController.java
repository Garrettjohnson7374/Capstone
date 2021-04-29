package com.techelevator.controller;

import com.techelevator.dao.AttendeeDAO;
import com.techelevator.model.Attendee;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AttendeeController {

    private AttendeeDAO attendeeDAO;

    public AttendeeController(AttendeeDAO attendeeDAO) {
        this.attendeeDAO = attendeeDAO;
    }
    @RequestMapping(path = "/schedule/playdates/{playdateId}/attendees", method = RequestMethod.GET)
    public List<Attendee> getAttendees(@PathVariable int playdateId) {
        return attendeeDAO.getAttendees(playdateId);
    }
}
