package com.techelevator.dao;

import com.techelevator.model.Attendee;

import java.util.List;

public interface AttendeeDAO {

    List<Attendee> getAttendees(int playdateId);
}
