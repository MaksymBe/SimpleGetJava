import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {
    private Connection connection;
    private Statement statement;
    private boolean connected = false;
    public DBController(String name, String pass) {
        System.out.print("Connecting to database:");
        while(!connected){
            try {
                Thread.sleep(500);
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                connection = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/internshipsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
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

    public ArrayList<Group> getAllGroups() throws Exception {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM groups");
        ArrayList<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            groups.add(createGroup(resultSet));
        }
        return groups;
    }

    public Group getGroupById(Integer id) throws Exception {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM groups WHERE id=" + id);
        resultSet.next();
        return createGroup(resultSet);
    }

    public Group delete(Integer id) throws Exception {
        Group group = getGroupById(id);
        statement.executeUpdate("DELETE FROM groups WHERE id=" + id);
        return group;
    }

    public Group update(Integer id, Group group) throws Exception {
        Group oldGroup = getGroupById(id);
        oldGroup.patch(group);
        group = oldGroup;
        statement.executeUpdate("UPDATE groups SET name=" + '\"' + group.getName() + '\"'
                + ", periodStart=" + '\"' + group.getPeriodStart() + '\"'
                + ", periodFinish=" + '\"' + group.getPeriodFinish() + '\"' + " WHERE id=" + id);
        return getGroupById(id);
    }

    public Group create(Group group) throws Exception {
        statement.executeUpdate("INSERT INTO groups(name, periodStart, periodFinish) VALUES(\""
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
