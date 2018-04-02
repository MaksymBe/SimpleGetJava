package Core.Repositories;

import Core.Group;
import Core.IRepository;
import Framework.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class GroupsRepository implements IRepository<Group> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Group> rowMapper = new BeanPropertyRowMapper<>(Group.class);

    public GroupsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Group[] getAll() throws RepositoryException {
        String sql = "SELECT * FROM groups";
        return this.jdbcTemplate.query(sql, rowMapper).toArray(new Group[]{});
    }

    public Group getById(Integer id) throws RepositoryException {
        String sql = "SELECT * FROM groups WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Group create(Group newGroup) throws RepositoryException {
        String sql = "INSERT INTO groups (name, periodStart, periodFinish) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, newGroup.getName());
                    ps.setString(2, newGroup.getPeriodStart());
                    ps.setString(3, newGroup.getPeriodFinish());
                    return ps;
                }, keyHolder);
        Number key = keyHolder.getKey();
        return getById(key.intValue());
    }

    public Group delete(Integer id) throws RepositoryException {
        Group group = getById(id);
        String sql = "DELETE FROM groups WHERE id=?";
        jdbcTemplate.update(sql, id);
        return group;
    }

    public Group update(Integer id, Group updatedGroup) throws RepositoryException {
        String sql = "UPDATE groups SET name=?, periodStart=?, periodFinish=? WHERE id=?";
        jdbcTemplate.update(sql, updatedGroup.getName(), updatedGroup.getPeriodStart(), updatedGroup.getPeriodFinish(), id);
        return getById(id);
    }
}