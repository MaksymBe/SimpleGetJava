package SpringWebApp.Core.Repositories;

import SpringWebApp.Core.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepositoryJPA extends JpaRepository<Group, Integer> {
}
