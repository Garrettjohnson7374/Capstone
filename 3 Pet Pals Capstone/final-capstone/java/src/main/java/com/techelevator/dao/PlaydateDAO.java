package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Pet;
import com.techelevator.model.Playdate;
import com.techelevator.model.UpdatePlaydateDTO;
import com.techelevator.services.MapService;

import java.util.List;

public interface PlaydateDAO {
    long create(CreatePlaydateDTO playdateDTO, MapService mapService);

    Playdate getPlaydate(int id);

    List<Playdate> getAllPlaydates(MapService mapService, int userId);

    long joinPlaydate(int playdateId, int petId);

    List<Playdate> getScheduledPlaydates(int petId);

    long cancelPlaydate(int playdateId);

    long updatePlaydate(int playdateId, UpdatePlaydateDTO playdateDTO, MapService mapService);
}
