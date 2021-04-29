package com.techelevator.controller;

import com.techelevator.dao.AccountDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;
import com.techelevator.model.distanceMatrix.Location;
import com.techelevator.services.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class AccountController {

    private UserDAO userDAO;
    private AccountDAO accountDAO;
    private MapService mapService;

    public AccountController(UserDAO userDAO, AccountDAO accountDAO, MapService mapService){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.mapService = mapService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public void create(@Valid @RequestBody AccountDTO accountDTO, Principal principal) {
        //get user id
        int userId = userDAO.findIdByUsername(principal.getName());

        //get lat and lng
        Location location = mapService.getLocation(accountDTO.getAddress(), accountDTO.getCity(), accountDTO.getState());

        accountDAO.create(accountDTO, userId, location);
    }

    @RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int id) {
        return accountDAO.getAccount(id);
    }

    @RequestMapping(path = "/profile/{id}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable int id, @Valid @RequestBody AccountDTO accountDTO){
        //get lat and lng
        Location location = mapService.getLocation(accountDTO.getAddress(), accountDTO.getCity(), accountDTO.getState());

        accountDAO.updateAccount(id, accountDTO, location);
    }

    @RequestMapping(path = "/profile-list", method = RequestMethod.GET)
    public List<Account> getAllUsers() {
        return accountDAO.getAllUsers();
    }
}
