package be.lionelh.whist.score.backend.rest.services;

import be.lionelh.whist.score.backend.data.DataService;
import be.lionelh.whist.score.backend.data.domain.Contract;
import be.lionelh.whist.score.backend.rest.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contracts")
public class ContractController {

    private DataService dataService;

    @Autowired
    private void setDataService(DataService inDataService) {
        this.dataService = inDataService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Contract>> findAllSorted() {
        List<Contract> l = this.dataService.findAllContractsSorted();

        return ResponseEntity.ok(l);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Contract> create(@RequestBody Contract inContract) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.dataService.createOrUpdateContract(inContract));
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Contract> update(@RequestBody Contract inContract, @PathVariable("id") long inContractId) {
        Contract c = this.dataService.findContractById(inContractId);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }

        c.setName(inContract.getName());
        c.setRoles(inContract.getRoles());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.dataService.createOrUpdateContract(c));
    }

    @GetMapping("/{name}/{numberOfPlayers}/exists")
    public ResponseEntity<Boolean> isNameAndNumberOfPlayersTaken(@PathVariable("name") String inName,
            @PathVariable("numberOfPlayers") short inNumberOfPlayers) {
        Boolean b = this.dataService.existsContractByNameAndNumberOfPlayers(inName, inNumberOfPlayers);
        return ResponseEntity.ok(b);
    }

    @GetMapping(path = "/numberofplayers/{nop}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Contract>> findByNumberOfPlayers(@PathVariable("nop") Short inNumberOfPlayers) {
        return ResponseEntity.ok(this.dataService.findContractsByNumberOfPlayers(inNumberOfPlayers));
    }

    @GetMapping(path = "/{id}/results", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ResultVO>> findResultsByContractId(@PathVariable("id") Long inContractId) {
        return ResponseEntity.ok(this.dataService.findResultsByContractId(inContractId));
    }
}
