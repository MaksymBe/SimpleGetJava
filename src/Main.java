import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        Integer port = 3000;
        ObjectMapper mapper = new ObjectMapper();
        Group[] groups = new Group[]{};
        try {
            File src = new File(args[0]);
            JsonNode obj = mapper.readTree(src);
            JsonNode groupsNode = obj.get("groups");
            groups = mapper.treeToValue(groupsNode, Group[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Listening on port " + port + "...");
            while (true) {
                Socket client = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());
                String line = in.readLine();
                String origin = null;
                for(int i = 0; i < 3; i++){
                    origin = in.readLine();
                }
                String[] wordsInRequest = line.split(" ");
                if (checkRequest(wordsInRequest[0])) {
                    out.print("HTTP/1.1 200 OK \r\n");
                    out.print("Content-Type: application/json; charset=utf-8\r\n");
                    out.print("Access-Control-Allow-Origin: *\r\n"); /* + origin.split(" ")[1] + "\r\n")*/
                    //out.print("Connection: close\r\n");
                    out.print("\r\n");
                    if (isGroups(wordsInRequest[1])) {
                        out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groups));
                    } else if (isGroupsById(wordsInRequest[1])) {
                        String[] arrayToGetNumber = wordsInRequest[1].split("/");
                        Integer groupId = Integer.parseInt(arrayToGetNumber[arrayToGetNumber.length-1]);
                        Boolean groupExists = false;
                        for (Group group : groups) {
                            if(group.getId().equals(groupId))
                            {
                                out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(group));
                                groupExists = true;
                                break;
                            }
                        }
                        if(!groupExists){
                            out.print("No such group");
                        }
                    }
                }


/*                X-Powered-By: Express
                Access-Control-Allow-Origin: http://localhost:9000
                Vary: Origin, Accept-Encoding
                Access-Control-Allow-Credentials: true
                Cache-Control: no-cache
                Pragma: no-cache
                Expires: -1
                X-Content-Type-Options: nosniff
                Content-Type: application/json; charset=utf-8
                Content-Length: 232
                ETag: W/"e8-2vURZ9ef862VdukLIVy1O0S0sHE"
                Date: Tue, 20 Mar 2018 08:20:03 GMT
                Connection: keep-alive*/

                else {
                    out.print("HTTP/1.1 404 \r\n");
                    out.print("Content-Type: application/json; charset=utf-8\r\n");
                    out.print("Connection: close\r\n");
                    out.print("Not found");
                }
                out.close();
                in.close();
                client.close();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Usage: java HttpMirror <port>");
        }
    }

    private static Boolean checkRequest(String s) {
        return s.equals("GET");
    }

    private static Boolean isGroups(String s) {
        return s.equals("/groups")||s.equals("/groups/");
    }

    private static Boolean isGroupsById(String s) {
        return s.matches("^/groups/\\d+$");
    }

}
