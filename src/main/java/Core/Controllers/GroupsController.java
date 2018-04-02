package Core.Controllers;


import Core.Group;
import Core.Repositories.GroupsRepository;
import Core.SQLRepositoryForGroups;
import Framework.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class GroupsController {

    //private SQLRepositoryForGroups repositoryForGroups = new SQLRepositoryForGroups("max", "qwerty");
    @Autowired
    GroupsRepository repositoryForGroups;

    @GetMapping("/groups")
    public ResponseEntity<Group[]> getAllGroups() {
        try {
            return ResponseEntity.ok().body(repositoryForGroups.getAll());
        } catch (RepositoryException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable(value = "id") Integer groupId) {
        try {
            return ResponseEntity.ok().body(repositoryForGroups.getById(groupId));
        } catch (RepositoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group newGroup) {
        try {
            return ResponseEntity.status(204).body(repositoryForGroups.create(newGroup));
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable(value = "id") Integer groupId) {
        try {
            repositoryForGroups.delete(groupId);
            return ResponseEntity.noContent().build();
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/groups/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable(value = "id") Integer groupId, @RequestBody Group newGroup) {
        try {
            return ResponseEntity.status(201).body(repositoryForGroups.update(groupId, newGroup));
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
