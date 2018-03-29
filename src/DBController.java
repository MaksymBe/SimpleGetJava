import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {
    private Connection connection;
    private Statement statement;

    public DBController(String name, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306",
                            name,
                            pass);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Group> getAllGroups() throws Exception {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM internshipsystem.groups");
        ArrayList<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            groups.add(createGroup(resultSet));
        }
        return groups;
    }

    public Group getGroupById(Integer id) throws Exception {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM internshipsystem.groups WHERE id=" + id);
        resultSet.next();
        return createGroup(resultSet);
    }

    public Group delete(Integer id) throws Exception {
        Group group = getGroupById(id);
        statement.executeUpdate("DELETE FROM internshipsystem.groups WHERE id=" + id);
        return group;
    }

    public Group update(Integer id, Group group) throws Exception {
        Group oldGroup = getGroupById(id);
        oldGroup.patch(group);
        group = oldGroup;
        statement.executeUpdate("UPDATE internshipsystem.groups SET name=" + '\"' + group.getName() + '\"'
                + ", periodStart=" + '\"' + group.getPeriodStart() + '\"'
                + ", periodFinish=" + '\"' + group.getPeriodFinish() + '\"' + " WHERE id=" + id);
        return getGroupById(id);
    }

    public Group create(Group group) throws Exception {
        statement.executeUpdate("INSERT INTO internshipsystem.groups(name, periodStart, periodFinish) VALUES(\""
                + group.getName() + "\", \""
                + group.getPeriodStart() + "\", \""
                + group.getPeriodFinish() + "\")");
        statement.getGeneratedKeys();

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);

        return getGroupById(id);
    }

    private Group createGroup(ResultSet resultSet) throws Exception {
        Group group = new Group();
        group.setId(resultSet.getInt(1));
        group.setName(resultSet.getString("name"));
        group.setPeriodStart(resultSet.getString("periodStart"));
        group.setPeriodFinish(resultSet.getString("periodFinish"));
        return group;
    }
}
