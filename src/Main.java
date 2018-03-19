import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            File src = new File("db.json");
            JsonNode obj = mapper.readTree(src);
            JsonNode groupsNode = obj.get("groups");
            //ArrayList<Group> groups = new ArrayList<>();
            String groupsJSON = groupsNode.toString();
            System.out.println(groupsJSON);
            Group[] groups = mapper.readValue(groupsJSON ,Group[].class);
            System.out.println(groups.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        try {
            getGroup();
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("Listening on port 3000 ...");
            while (true) {
                Socket client = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());

                out.print("HTTP/1.1 200 \r\n");
                out.print("Content-Type: text/plain\r\n");
                out.print("Connection: close\r\n");
                out.print("\r\n");
                out.print("Some useful info");

                out.close();
                in.close();
                client.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Usage: java HttpMirror <port>");
        }*/
    }

    /*static void getGroup() {
        ObjectMapper mapper = new ObjectMapper();
        Group obj;
        try {
            String dbString = "{'name': 'inCamp Winter S8', 'periodStart': '2018-03-15','periodFinish': '2018-05-28','id': 3}";
            obj = mapper.readValue(dbString, Group.class);
            System.out.println(obj.toString());

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}
