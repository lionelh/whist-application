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

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Role>> findAllSorted() {
        List<Role> l = this.dataService.findAllRolesSorted();

        return ResponseEntity.ok(l);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Role> create(@RequestBody Role inRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.dataService.createOrUpdateRole(inRole));
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Role> update(@RequestBody Role inRole, @PathVariable("id") long inRoleId) {
        Role r = this.dataService.findRoleById(inRoleId);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }

        r.setName(inRole.getName());

        return new ResponseEntity<>(this.dataService.createOrUpdateRole(r), HttpStatus.CREATED);
    }

    @GetMapping("/{name}/exists")
    public ResponseEntity<Boolean> isNameTaken(@PathVariable("name") String inName) {
        Boolean b = this.dataService.roleNameExists(inName);
        return ResponseEntity.ok(b);
    }
}
