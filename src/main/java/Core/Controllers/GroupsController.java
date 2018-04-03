package Core.Controllers;


import Core.Group;
import Core.Repositories.GroupsRepositoryJDBCTemplate;
import Core.Repositories.GroupsRepositoryJPA;
import Core.Repositories.GroupsRepositoryJPAImpl;
import Core.Repositories.IRepository;
import Framework.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/groups")
public class GroupsController {

    //private SQLRepositoryForGroups repositoryForGroups = new SQLRepositoryForGroups("max", "qwerty");
    /*@Autowired
    GroupsRepositoryJDBCTemplate repositoryForGroups;*/


    @Autowired
    GroupsRepositoryJPAImpl repositoryForGroups;

    @GetMapping("")
    public ResponseEntity<Group[]> getAllGroups() {
        try {
            return ResponseEntity.ok().body(repositoryForGroups.getAll());
        } catch (RepositoryException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable(value = "id") Integer groupId) {
        try {
            Group group = repositoryForGroups.getById(groupId);
            if(group.getId()==null) return ResponseEntity.notFound().build();
            else
            return ResponseEntity.ok().body(group);
        } catch (RepositoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group newGroup) {
        try {
            return ResponseEntity.status(204).body(repositoryForGroups.create(newGroup));
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable(value = "id") Integer groupId) {
        try {
            repositoryForGroups.delete(groupId);
            return ResponseEntity.noContent().build();
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable(value = "id") Integer groupId, @RequestBody Group newGroup) {
        try {
            return ResponseEntity.status(201).body(repositoryForGroups.update(groupId, newGroup));
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
