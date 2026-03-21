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

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Player>> findAllSorted() {
        List<Player> l = this.dataService.findAllPlayersSorted();

        return ResponseEntity.ok(l);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Player> create(@RequestBody Player inPlayer) {
        return new ResponseEntity<>(this.dataService.createOrUpdatePlayer(inPlayer), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Player> update(@RequestBody Player inPlayer, @PathVariable("id") long inPlayerId) {
        Player p = this.dataService.findPlayerById(inPlayerId);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }

        p.setName(inPlayer.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.dataService.createOrUpdatePlayer(p));
    }

    @GetMapping("/{name}/exists")
    public ResponseEntity<Boolean> isNameTaken(@PathVariable("name") String inName) {
        Boolean b = this.dataService.playerNameExists(inName);
        return ResponseEntity.ok(b);
    }
}
