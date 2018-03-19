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
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        Group[] groups = new Group[]{};
        try {
            File src = new File("db.json");
            JsonNode obj = mapper.readTree(src);
            JsonNode groupsNode = obj.get("groups");
            groups = mapper.treeToValue(groupsNode ,Group[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("Listening on port 3000...");
            while (true) {
                Socket client = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());
                String line = in.readLine();

                out.print("HTTP/1.1 200 \r\n");
                out.print("Content-Type: application/json; charset=utf-8\r\n");
                out.print("Connection: close\r\n");
                out.print("\r\n");
                out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groups));
                out.close();
                in.close();
                client.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Usage: java HttpMirror <port>");
        }
    }

}
