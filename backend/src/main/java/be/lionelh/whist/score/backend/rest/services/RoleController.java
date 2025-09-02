package be.lionelh.whist.score.backend.rest.services;

import be.lionelh.whist.score.backend.data.DataService;
import be.lionelh.whist.score.backend.data.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "/api/roles")
public class RoleController {

    private DataService dataService;

    @Autowired
    private void setDataService(DataService inDataService) {
        this.dataService = inDataService;
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.GET })
    public ResponseEntity<List<Role>> findAllSorted() {
        List<Role> l = this.dataService.findAllRolesSorted();

        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.POST })
    public ResponseEntity<Role> create(@RequestBody Role inRole) {
        return new ResponseEntity<>(this.dataService.createOrUpdateRole(inRole), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.PUT })
    public ResponseEntity<Role> update(@RequestBody Role inRole, @PathParam("id") long inRoleId) {
        Role r = this.dataService.findRoleById(inRoleId);
        if (r == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        r.setName(inRole.getName());

        return new ResponseEntity<>(this.dataService.createOrUpdateRole(r), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{name}/exists", method = { RequestMethod.GET })
    public ResponseEntity<Boolean> isNameTaken(@PathVariable("name") String inName) {
        Boolean b = this.dataService.roleNameExists(inName);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }
}
