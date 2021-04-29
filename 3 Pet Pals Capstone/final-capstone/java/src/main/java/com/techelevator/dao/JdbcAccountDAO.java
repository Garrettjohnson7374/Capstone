package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;
import com.techelevator.model.distanceMatrix.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(AccountDTO accountDTO, int userId, Location location) {

        //create account
        String sql = "INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, " +
                "zip, bio, pic, lat, lng) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        jdbcTemplate.update(sql,  userId, accountDTO.getFirstName(), accountDTO.getLastName(), accountDTO.getEmail(),
                accountDTO.getPhone(), accountDTO.getAddress(), accountDTO.getCity(), accountDTO.getState(),
                accountDTO.getZip(), accountDTO.getBio(), accountDTO.getPic(), location.getLat(), location.getLng());



    }

    @Override
    public void updateAccount(int id, AccountDTO accountDTO, Location location) {
        //get lat and long


        String sql = "UPDATE accounts SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, " +
                "city = ?, state = ?, zip = ?, bio = ?, pic = ?, lat=?, lng=? WHERE user_id = ?";
        jdbcTemplate.update(sql, accountDTO.getFirstName(), accountDTO.getLastName(), accountDTO.getEmail(),
                accountDTO.getPhone(), accountDTO.getAddress(), accountDTO.getCity(), accountDTO.getState(),
                accountDTO.getZip(), accountDTO.getBio(), accountDTO.getPic(), location.getLat(), location.getLng(), id);

    }

    @Override
    public Account getAccount(int id) {
        Account account = new Account();

        String sql = "SELECT user_id, first_name, last_name, email, phone, address, city, state, " +
                "zip, bio, pic, lat, lng FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            account = mapRowToAccount(results);
        }

        return account;
    }

    @Override
    public List<Account> getAllUsers() {
        List<Account> allUsers = new ArrayList<>();

        String sql = "SELECT user_id, first_name, last_name, email, phone, address, city, state, zip, " +
                "bio, pic, lat, lng FROM accounts";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            allUsers.add(mapRowToAccount(results));
        }
        return allUsers;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setUserId(results.getLong("user_id"));
        account.setFirstName(results.getString("first_name"));
        account.setLastName(results.getString("last_name"));
        account.setEmail(results.getString("email"));
        account.setPhone(results.getString("phone"));
        account.setAddress(results.getString("address"));
        account.setCity(results.getString("city"));
        account.setState(results.getString("state"));
        account.setZip(results.getString("zip"));
        account.setBio(results.getString("bio"));
        account.setPic(results.getString("pic"));
        account.setLat(results.getString("lat"));
        account.setLng(results.getString("lng"));
        return account;
    }
}
