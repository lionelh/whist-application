package be.lionelh.whist.score.backend.rest.services;

import be.lionelh.whist.score.backend.data.DataService;
import be.lionelh.whist.score.backend.rest.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/results")
public class ResultController {

    private DataService dataService;

    @Autowired
    private void setDataService(DataService inDataService) {
        this.dataService = inDataService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ResultVO>> findAllSorted() {
        List<ResultVO> l = this.dataService.findAllResultsSorted();

        return ResponseEntity.ok(l);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultVO> create(@RequestBody ResultVO inResult) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.dataService.createOrUpdateResult(inResult));
    }
}
