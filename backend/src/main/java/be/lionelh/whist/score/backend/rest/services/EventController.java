package be.lionelh.whist.score.backend.rest.services;

import be.lionelh.whist.score.backend.data.DataService;
import be.lionelh.whist.score.backend.data.domain.Event;
import be.lionelh.whist.score.backend.data.domain.Contract;
import be.lionelh.whist.score.backend.rest.vo.DrawVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/events")
public class EventController {

    private DataService dataService;

    @Autowired
    private void setDataService(DataService inDataService) {
        this.dataService = inDataService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Event>> findAllSorted() {
        List<Event> l = this.dataService.findAllEventsSorted();

        return ResponseEntity.ok(l);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Event> create(@RequestBody Event inEvent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.dataService.createOrUpdateEvent(inEvent));
    }

    @GetMapping(path = "/{id}/details", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<DrawVO>> getDetails(@PathVariable("id") long inEventId) {
        return ResponseEntity.ok(this.dataService.getEventDetails(inEventId));
    }

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Event> findById(@PathVariable("id") Long inId) {
        return ResponseEntity.ok(this.dataService.findEventById(inId));
    }

    @PostMapping(path = "/{id}/draw", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<DrawVO>> addDraw(@PathVariable("id") Long inEventId, @RequestBody DrawVO inDraw) {
        this.dataService.addDraw(inEventId, inDraw);
        return this.getDetails(inEventId);
    }

    @PostMapping(path = "/{id}/close", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<DrawVO>> closeEvent(@PathVariable("id") Long inEventId) {
        this.dataService.closeEvent(inEventId);
        return this.getDetails(inEventId);
    }

    @GetMapping(path = "/{id}/contracts", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Contract>> findContractsForEvent(@PathVariable("id") Long inEventId) {
        List<Contract> cts = this.dataService.findContractsForEvent(inEventId);
        if (cts == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cts);
    }
}
