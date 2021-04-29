package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;
import com.techelevator.model.distanceMatrix.Location;

import java.util.List;

public interface AccountDAO {
    void create(AccountDTO accountDTO, int userId, Location location);

    void updateAccount(int id, AccountDTO accountDTO, Location location);

    Account getAccount(int id);

    List<Account> getAllUsers();
}
