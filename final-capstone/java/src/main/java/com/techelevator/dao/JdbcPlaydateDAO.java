package com.techelevator.dao;

import com.techelevator.model.*;
import com.techelevator.model.distanceMatrix.Location;
import com.techelevator.services.MapService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlaydateDAO implements PlaydateDAO {

    private JdbcTemplate jdbcTemplate;
    private PetDAO petDAO;
    private AttendeeDAO attendeeDAO;
    private AccountDAO accountDAO;
    private static final boolean IS_HOST = true;
    private static final boolean IS_NOT_HOST = false;

    public JdbcPlaydateDAO(JdbcTemplate jdbcTemplate, PetDAO petDAO, AttendeeDAO attendeeDAO, AccountDAO accountDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.petDAO = petDAO;
        this.attendeeDAO = attendeeDAO;
        this.accountDAO = accountDAO;
    }


    @Override
    public long create(CreatePlaydateDTO playdateDTO, MapService mapService) {

        //get lat and lng
        Location location = mapService.getLocation(playdateDTO.getAddress(), playdateDTO.getCity(), playdateDTO.getState());

        //create playdate
        String sql = "INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?) RETURNING playdate_id";
        Long playdateId = jdbcTemplate.queryForObject(sql, Long.class, playdateDTO.getPet().getPetId(), playdateDTO.getAddress(),
                playdateDTO.getCity(), playdateDTO.getState(), playdateDTO.getZip(), playdateDTO.getDate(), location.getLat(), location.getLng());

        //add to linker table
        String sqlPetPlaydate = "INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES(?,?,?)";
        jdbcTemplate.update(sqlPetPlaydate, playdateId, playdateDTO.getPet().getPetId(), IS_HOST);

        return playdateId;
    }

    @Override
    public Playdate getPlaydate(int id) {
        Playdate playdate = new Playdate();
        String sql = "SELECT playdate_id, pet_id, address, city, state, zip, date, lat, lng FROM playdates WHERE playdate_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            playdate = mapRowToPlaydate(results);
        }
        return playdate;
    }

    @Override
    public List<Playdate> getAllPlaydates(MapService mapService, int userId) {
        List<Playdate> playdates = new ArrayList<>();

        String sql = "SELECT playdate_id, pet_id, address, city, state, zip, date, lat, lng FROM playdates WHERE deleted_date IS NULL";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        String destinations = "";
        while(results.next()) {
            Playdate playdate = mapRowToPlaydate(results);
            playdates.add(playdate);
            destinations += playdate.getLat() + "," + playdate.getLng() + "|";
        }
        destinations = destinations.substring(0,destinations.length()-1);

        if (userId != 0) {
            //get location of current user
            Account currentAccount = accountDAO.getAccount(userId);
            String origin = currentAccount.getLat() + "," + currentAccount.getLng();

            //get distances from current user
            List<String> distances = mapService.getDistances(origin, destinations);
            for (int i = 0; i < distances.size(); i++) {
                playdates.get(i).setDistanceFromUser(distances.get(i));
            }
        }

        return playdates;
    }

    @Override
    public long joinPlaydate(int playdateId, int petId) {
        String sql = "INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES(?,?,?)";
        return jdbcTemplate.update(sql, playdateId, petId, IS_NOT_HOST);
    }

    @Override
    public List<Playdate> getScheduledPlaydates(int petId) {
        List<Playdate> playdates = new ArrayList<>();

        String sql = "SELECT p.playdate_id, p.pet_id, address, city, state, zip, date, lat, lng " +
        "FROM playdates p " +
        "JOIN playdates_pets pp ON p.playdate_id = pp.playdate_id " +
        "WHERE pp.pet_id = ? AND p.deleted_date IS NULL";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petId);
        while(results.next()) {
            playdates.add(mapRowToPlaydate(results));
        }

        return playdates;
    }

    @Override
    public long cancelPlaydate(int playdateId) {
        String sqlPlaydatesPets = "UPDATE playdates_pets SET deleted_date = ? WHERE playdate_id = ?";
        String sqlPlaydates = "UPDATE playdates SET deleted_date = ? WHERE playdate_id = ?";

        long playdatesPetsUpdated = jdbcTemplate.update(sqlPlaydatesPets, LocalDate.now(), playdateId);
        long playdatesUpdated = jdbcTemplate.update(sqlPlaydates, LocalDate.now(), playdateId);
        return playdatesPetsUpdated + playdatesUpdated;
    }

    @Override
    public long updatePlaydate(int playdateId, UpdatePlaydateDTO playdateDTO, MapService mapService) {
        //get lat and lng
        Location location = mapService.getLocation(playdateDTO.getAddress(), playdateDTO.getCity(), playdateDTO.getState());

        String sql = "UPDATE playdates SET pet_id = ?, address = ?, city = ?, state = ?, zip = ?, " +
                "date = ?, lat = ?, lng = ? WHERE playdate_id = ?";

        return jdbcTemplate.update(sql, playdateDTO.getPet().getPetId(), playdateDTO.getAddress(), playdateDTO.getCity(),
                playdateDTO.getState(), playdateDTO.getZip(), playdateDTO.getDate(), location.getLat(), location.getLng(),
                playdateId);
    }


    private Playdate mapRowToPlaydate(SqlRowSet results) {
        Playdate playdate = new Playdate();
        playdate.setPlaydateId(results.getLong("playdate_id"));
        int petId = results.getInt("pet_id");
        Pet pet = petDAO.getPet(petId);
        playdate.setPet(pet);
        playdate.setAttendeeList(attendeeDAO.getAttendees(results.getInt("playdate_id")));
        playdate.setAddress(results.getString("address"));
        playdate.setCity(results.getString("city"));
        playdate.setState(results.getString("state"));
        playdate.setZip(results.getString("zip"));
        playdate.setDate(results.getTimestamp("date").toLocalDateTime());
        playdate.setLat(results.getString("lat"));
        playdate.setLng(results.getString("lng"));
        Long ownerId = pet.getUserId();
        playdate.setOwner(accountDAO.getAccount(ownerId.intValue()));
        return playdate;
    }
}
