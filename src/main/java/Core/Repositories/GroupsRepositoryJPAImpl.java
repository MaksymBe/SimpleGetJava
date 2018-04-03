package Core.Repositories;


import Core.Group;
import Framework.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsRepositoryJPAImpl implements IRepository<Group> {

    @Autowired
    GroupsRepositoryJPA repository;

    public GroupsRepositoryJPAImpl() {
    }

    public Group[] getAll() throws RepositoryException {
        try{
           return repository.findAll().toArray(new Group[]{});
        } catch (Exception e){
            throw new RepositoryException();
        }
    }

    public Group getById(Integer id) throws RepositoryException {
        try{
            Group group = repository.getOne(id);
            return group;
        } catch (org.springframework.dao.EmptyResultDataAccessException e){
            throw new RepositoryException();
        }
    }

    public Group create(Group newGroup) throws RepositoryException {
        try{
            return repository.save(newGroup);
        } catch (Exception e){
            throw new RepositoryException();
        }
    }

    public Group delete(Integer id) throws RepositoryException {
        try{
            Group group = getById(id);
            repository.deleteById(id);
            return group;
        } catch (Exception e){
            throw new RepositoryException();
        }
    }

    public Group update(Integer id, Group updatedGroup) throws RepositoryException {
        try{
            Group group = getById(id);
            group.patch(updatedGroup);
            repository.save(group);
            return group;
        } catch (Exception e){
            throw new RepositoryException();
        }
    }
}
