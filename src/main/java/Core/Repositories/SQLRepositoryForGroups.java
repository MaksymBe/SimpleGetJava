package Core.Repositories;

import Core.Group;
import Framework.RepositoryException;

import java.sql.*;
import java.util.ArrayList;

public class SQLRepositoryForGroups implements IRepository<Group> {
    private Connection connection;
    private Statement statement;

    public SQLRepositoryForGroups(String name, String pass) {
        System.out.print("Connecting to database:");
        boolean connected = false;
        while (!connected) {
            try {
                Thread.sleep(500);
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                connection = DriverManager
                        .getConnection("jdbc:mysql://172.17.0.2/internshipsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
                                name,
                                pass);
                statement = connection.createStatement();
                connected = true;
            } catch (Exception e) {
                System.out.print('.');
            }
        }
        System.out.println('\n');
    }

    public Group[] getAll() throws RepositoryException {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM groups");
            ArrayList<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                groups.add(createGroup(resultSet));
            }
            return groups.toArray(new Group[]{});
        } catch (SQLException e) {
            throw new RepositoryException();
        }
    }

    public Group getById(Integer id) throws RepositoryException {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM groups WHERE id=" + id);
            resultSet.next();
            return createGroup(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException();
        }
    }

    public Group delete(Integer id) throws RepositoryException {
        try {
            Group group = getById(id);
            statement.executeUpdate("DELETE FROM groups WHERE id=" + id);
            return group;
        } catch (SQLException e) {
            throw new RepositoryException();
        }

    }

    public Group update(Integer id, Group group) throws RepositoryException {
        try {
            Group oldGroup = getById(id);
            oldGroup.patch(group);
            group = oldGroup;
            statement.executeUpdate("UPDATE groups SET name=" + '\"' + group.getName() + '\"'
                    + ", periodStart=" + '\"' + group.getPeriodStart() + '\"'
                    + ", periodFinish=" + '\"' + group.getPeriodFinish() + '\"' + " WHERE id=" + id);
            return getById(id);
        } catch (Exception e) {
            throw new RepositoryException();
        }

    }

    public Group create(Group group) throws RepositoryException {
        try {
            statement.executeUpdate("INSERT INTO groups(name, periodStart, periodFinish) VALUES(\""
                    + group.getName() + "\", \""
                    + group.getPeriodStart() + "\", \""
                    + group.getPeriodFinish() + "\")", 1);
            statement.getGeneratedKeys();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);

            return getById(id);
        } catch (Exception e) {
            throw new RepositoryException();
        }

    }

    private Group createGroup(ResultSet resultSet) throws RepositoryException {
        try {
            Group group = new Group();
            group.setId(resultSet.getInt(1));
            group.setName(resultSet.getString("name"));
            group.setPeriodStart(resultSet.getDate("periodStart"));
            group.setPeriodFinish(resultSet.getDate("periodFinish"));
            return group;
        } catch (SQLException e) {
            throw new RepositoryException();
        }

    }
}
