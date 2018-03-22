import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

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

    public void start() throws Exception{
        System.out.println("Listening on port " + serverSocket.getLocalPort() + "...");
        try {
            while (true) {
                Socket client = serverSocket.accept();
//                Request request = requestParser.parseRequest(new InputStreamReader(client.getInputStream()));

                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
