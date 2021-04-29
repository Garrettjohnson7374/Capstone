package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.MessageDTO;
import com.techelevator.model.Message;

import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMessageDAO implements MessageDAO  {

    private JdbcTemplate jdbcTemplate;
    private PetDAO petDAO;
    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public JdbcMessageDAO(JdbcTemplate jdbcTemplate, PetDAO petDAO, AccountDAO accountDAO, UserDAO userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.petDAO = petDAO;
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messageList = new ArrayList<>();

        String sql = "SELECT * FROM messages";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                messageList.add(mapRowToMessage(results));
            }

            return messageList;
        }

    @Override
    public List<Message> getMessageByUserId(int userId) {
        List<Message> messageList = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            messageList.add(mapRowToMessage(results));
        }
        return messageList;
    }

    @Override
    public long create(MessageDTO messageDTO, int userId, LocalDateTime postedDate) {

        String sql = "INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (?,?,?,?) RETURNING message_id";
        Long messageId = jdbcTemplate.queryForObject(sql, Long.class, userId, messageDTO.getMessage(), postedDate, messageDTO.getPetId());

        return messageId;
    }

    private Message mapRowToMessage(SqlRowSet results) {
        Message message = new Message();
        message.setMessageId(results.getLong("message_id"));
        Long userId = results.getLong("user_id");
        message.setUserId(userId);
        message.setMessage(results.getString("message"));
        message.setPostedDate(results.getTimestamp("posted_date").toLocalDateTime());
        int petId = results.getInt("pet_id");
        message.setPet(petDAO.getPet(petId));
        User user = userDAO.getUserById(userId);
        message.setName(user.getUsername());
        Account account = accountDAO.getAccount(userId.intValue());
        message.setPic(account.getPic());
        return message;
    }


}
