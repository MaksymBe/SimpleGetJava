import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        Integer port;
        String filePath;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            port = 3000;
        }
        try {
            filePath = args[0];
        } catch (Exception e) {
            filePath = "../db.json";
        }
        GroupsHandler groupsHandler = new GroupsHandler(filePath);
        Router router = new Router();
        router.addHandler("GET", "/groups/", groupsHandler::getAllGroups);
        router.addHandler("GET", "/groups/:id", groupsHandler::getGroupById);
        Server server = new Server(port, router);
        server.start();
        /*ObjectMapper mapper = new ObjectMapper();
        System.out.println(port);
        Group[] groups = new Group[]{};
        try {
            File filePath = new File(args[0]);
            JsonNode jsonInTree = mapper.readTree(filePath);
            JsonNode groupsNode = jsonInTree.get("groups");
            groups = mapper.treeToValue(groupsNode, Group[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Listening on port " + port + "...");
            while (true) {
                Socket client = ss.accept();
                RequestParser parser = new RequestParser();
                Request req = parser.parseRequest(new InputStreamReader(client.getInputStream()));
                System.out.println(req.getBody());
                //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                PrintWriter out = new PrintWriter(client.getOutputStream());

//                String collect = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining());


*//*                BufferedInputStream inputS = new BufferedInputStream(client.getInputStream());
                byte[] buffer = new byte[1024];    //If you handle larger data use a bigger buffer size
                int read;
                while((read = inputS.read(buffer)) != -1) {
                    System.out.println(read);
                    // Your code to handle the data
                }

                System.out.println(new String(buffer));*//*



                *//*String line = in.readLine();
                System.out.println(line);
                String[] wordsInRequest = line.split(" ");
                if (checkRequest(wordsInRequest[0]) && (isGroups(wordsInRequest[1]) || isGroupsById(wordsInRequest[1]))) {

                    if (isGroups(wordsInRequest[1])) {
                        printOkResponse(out, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groups));

                    } else if (isGroupsById(wordsInRequest[1])) {
                        String[] arrayToGetNumber = wordsInRequest[1].split("/");
                        Integer groupId = Integer.parseInt(arrayToGetNumber[arrayToGetNumber.length - 1]);
                        Boolean groupExists = false;
                        for (Group group : groups) {
                            if (group.getId().equals(groupId)) {
                                printOkResponse(out, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(group));
                                groupExists = true;
                                break;
                            }
                        }
                        if (!groupExists) {
                            print404Response(out);
                        }
                    }
                } else {
                    print404Response(out);
                }*//*
//                out.close();
                //in.close();
                client.close();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Usage: java HttpMirror <port>");
        }*/
    }

    private static void printOkResponse(PrintWriter out, String data) {
        out.print("HTTP/1.1 200 OK \r\n");
        out.print("Content-Type: application/json; charset=utf-8\r\n");
        out.print("Access-Control-Allow-Origin: *\r\n");
        out.print("\r\n");
        out.print(data);
    }



    private static Boolean checkRequest(String s) {
        return s.equals("GET");
    }

    private static Boolean isGroups(String s) {
        return s.matches("^/groups(/)*$");
    }

    private static Boolean isGroupsById(String s) {
        return s.matches("^/groups/\\d+$");
    }

}
