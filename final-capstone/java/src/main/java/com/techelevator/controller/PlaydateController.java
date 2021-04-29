package com.techelevator.controller;

import com.techelevator.dao.PlaydateDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Pet;
import com.techelevator.model.Playdate;
import com.techelevator.model.UpdatePlaydateDTO;
import com.techelevator.services.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PlaydateController {

    private PlaydateDAO playdateDAO;
    private UserDAO userDAO;
    private MapService mapService;

    public PlaydateController(PlaydateDAO playdateDAO, UserDAO userDAO, MapService mapService) {
        this.playdateDAO = playdateDAO;
        this.userDAO = userDAO;
        this.mapService = mapService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/playdates", method = RequestMethod.POST)
    public long create(@Valid @RequestBody CreatePlaydateDTO playdateDTO) {
        return playdateDAO.create(playdateDTO, mapService);
    }

    @RequestMapping(path = "/playdates/{id}", method = RequestMethod.GET)
    public Playdate getPlaydate(@PathVariable int id) {
        return playdateDAO.getPlaydate(id);
    }

    @RequestMapping(path = "/playdates", method = RequestMethod.GET)
    public List<Playdate> getAllPlaydates(Principal principal) {
        int userId = 0;
        if (principal != null) {
            userId = userDAO.findIdByUsername(principal.getName());
        }
        return playdateDAO.getAllPlaydates(mapService, userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "{petId}/join/playdate/{playdateId}", method = RequestMethod.POST)
    public long addPetToPlaydate(@PathVariable int petId, @PathVariable int playdateId) {
        return playdateDAO.joinPlaydate(petId, playdateId);
    }

    @RequestMapping(path = "{petId}/schedule/playdates", method = RequestMethod.GET)
    public List<Playdate> getScheduledPlaydates(@PathVariable int petId) {
        return playdateDAO.getScheduledPlaydates(petId);
    }

    @RequestMapping(path = "/playdates/{playdateId}/update", method = RequestMethod.PUT)
    public long updatePlaydate(@PathVariable int playdateId, @Valid @RequestBody UpdatePlaydateDTO playdateDTO) {
        return playdateDAO.updatePlaydate(playdateId, playdateDTO, mapService);
    }

    @RequestMapping(path = "/cancel/playdate/{playdateId}", method = RequestMethod.DELETE)
    public long cancelPlaydate(@PathVariable int playdateId) {
        return playdateDAO.cancelPlaydate(playdateId);
    }

}