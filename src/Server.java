/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket = null;
    private RequestParser requestParser = new RequestParser();

    public Server(int port, int backlog, InetAddress address){
        try{
            serverSocket = new ServerSocket(port, backlog, address);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int port, InetAddress address){
        try{
            serverSocket = new ServerSocket(port, 5, address);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        System.out.println("Listening on port " + serverSocket.getLocalPort() + "...");
        try {
            while (true) {
                Socket client = serverSocket.accept();
                Request request = requestParser.parseInputStream(client.getInputStream());

                PrintWriter out = new PrintWriter(client.getOutputStream());
                String line = in.readLine();
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
                }
                out.close();
                in.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
