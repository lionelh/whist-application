package be.lionelh.whist.score.backend.rest.services;

import be.lionelh.whist.score.backend.data.DataService;
import be.lionelh.whist.score.backend.data.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/players")
public class PlayerController {

    private DataService dataService;

    @Autowired
    private void setDataService(DataService inDataService) {
        this.dataService = inDataService;
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.GET })
    public ResponseEntity<List<Player>> findAllSorted() {
        List<Player> l = this.dataService.findAllPlayersSorted();

        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.POST })
    public ResponseEntity<Player> create(@RequestBody Player inPlayer) {
        return new ResponseEntity<>(this.dataService.createOrUpdatePlayer(inPlayer), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.PUT })
    public ResponseEntity<Player> update(@RequestBody Player inPlayer, @PathVariable("id") long inPlayerId) {
        Player p = this.dataService.findPlayerById(inPlayerId);
        if (p == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        p.setName(inPlayer.getName());

        return new ResponseEntity<>(this.dataService.createOrUpdatePlayer(p), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{name}/exists", method = { RequestMethod.GET })
    public ResponseEntity<Boolean> isNameTaken(@PathVariable("name") String inName) {
        Boolean b = this.dataService.playerNameExists(inName);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }
}
