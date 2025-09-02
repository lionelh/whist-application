package be.lionelh.whist.score.backend.rest.services;

import be.lionelh.whist.score.backend.data.DataService;
import be.lionelh.whist.score.backend.data.domain.Contract;
import be.lionelh.whist.score.backend.rest.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "/api/contracts")
public class ContractController {

    private DataService dataService;

    @Autowired
    private void setDataService(DataService inDataService) {
        this.dataService = inDataService;
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.GET })
    public ResponseEntity<List<Contract>> findAllSorted() {
        List<Contract> l = this.dataService.findAllContractsSorted();

        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.POST })
    public ResponseEntity<Contract> create(@RequestBody Contract inContract) {
        return new ResponseEntity<>(this.dataService.createOrUpdateContract(inContract), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.PUT })
    public ResponseEntity<Contract> update(@RequestBody Contract inContract, @PathParam("id") long inContractId) {
        Contract c = this.dataService.findContractById(inContractId);
        if (c == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        c.setName(inContract.getName());
        c.setRoles(inContract.getRoles());

        return new ResponseEntity<>(this.dataService.createOrUpdateContract(c), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{name}/{numberOfPlayers}/exists", method = { RequestMethod.GET })
    public ResponseEntity<Boolean> isNameAndNumberOfPlayersTaken(@PathVariable("name") String inName,
            @PathVariable("numberOfPlayers") short inNumberOfPlayers) {
        Boolean b = this.dataService.existsContractByNameAndNumberOfPlayers(inName, inNumberOfPlayers);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @RequestMapping(path = "/numberofplayers/{nop}", produces = { MediaType.APPLICATION_JSON_VALUE }, method = {
            RequestMethod.GET })
    public ResponseEntity<List<Contract>> findByNumberOfPlayers(@PathVariable("nop") Short inNumberOfPlayers) {
        return new ResponseEntity<>(this.dataService.findContractsByNumberOfPlayers(inNumberOfPlayers), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/results", produces = { MediaType.APPLICATION_JSON_VALUE }, method = {
            RequestMethod.GET })
    public ResponseEntity<List<ResultVO>> findResultsByContractId(@PathVariable("id") Long inContractId) {
        return new ResponseEntity<>(this.dataService.findResultsByContractId(inContractId), HttpStatus.OK);
    }
}
