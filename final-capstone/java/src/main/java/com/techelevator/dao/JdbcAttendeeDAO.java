package com.techelevator.dao;

import com.techelevator.model.Attendee;
import com.techelevator.model.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAttendeeDAO implements AttendeeDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcAttendeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Attendee> getAttendees(int playdateId) {
        List<Attendee> attendees = new ArrayList<>();

        String sql = "SELECT p.pet_id, pp.playdate_id, name, user_id, species, breed, weight, birth_year, pp.is_host, " +
                "energetic_relaxed, shy_friendly, apathetic_curious, bio, pic " +
                "FROM pets p " +
                "JOIN playdates_pets pp ON pp.pet_id = p.pet_id " +
                "WHERE pp.playdate_id = ? AND pp.is_host = false";

//        AND pp.is_host = false
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playdateId);
        while(results.next()) {
            attendees.add(mapRowToAttendee(results));
        }

        return attendees;
    }

    private Attendee mapRowToAttendee(SqlRowSet results) {
        Attendee attendee = new Attendee();
        attendee.setPetId(results.getLong("pet_id"));
        attendee.setPlaydateId(results.getLong("playdate_id"));
        attendee.setName(results.getString("name"));
        attendee.setUserId(results.getLong("user_id"));
        attendee.setSpecies(results.getString("species"));
        attendee.setBreed(results.getString("breed"));
        attendee.setWeight(results.getInt("weight"));
        attendee.setBirthYear(results.getInt("birth_year"));
        attendee.setHost(results.getBoolean("is_host"));
        attendee.setEnergeticRelaxed(results.getString("energetic_relaxed"));
        attendee.setShyFriendly(results.getString("shy_friendly"));
        attendee.setApatheticCurious(results.getString("apathetic_curious"));
        attendee.setBio(results.getString("bio"));
        attendee.setPic(results.getString("pic"));
        return attendee;
    }
}
