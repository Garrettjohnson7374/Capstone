package com.techelevator.controller;

import com.techelevator.dao.PetDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Attendee;
import com.techelevator.model.Pet;
import com.techelevator.model.PetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PetController {

    private PetDAO petDAO;
    private UserDAO userDAO;

    public PetController(PetDAO petDAO, UserDAO userDAO) {
        this.petDAO = petDAO;
        this.userDAO = userDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/pets/register", method = RequestMethod.POST)
    public long register(@Valid @RequestBody PetDTO petDTO, Principal principal) {
        int userId = userDAO.findIdByUsername(principal.getName());
        return petDAO.create(petDTO, userId);
    }

    @RequestMapping(path = "/pets/{id}", method = RequestMethod.GET)
    public Pet getPet(@PathVariable int id) {
       return petDAO.getPet(id);
    }

    @RequestMapping(path = "/pets/{id}", method = RequestMethod.PUT)
    public void updatePet(@PathVariable int id, @Valid @RequestBody PetDTO petDTO) {
        petDAO.updatePet(id, petDTO);
    }

    @RequestMapping(path = "/pets", method = RequestMethod.GET)
    public List<Pet> getPetsByUserId(Principal principal) {
        int userId = userDAO.findIdByUsername(principal.getName());
        return petDAO.getPetsByUserId(userId);
    }

}
