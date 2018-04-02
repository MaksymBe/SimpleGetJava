package Framework;

import Framework.Dispatcher;
import Framework.Router;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket = null;
    private RequestParser requestParser = new RequestParser();
    private Router router;

    public Server(int port, int backlog, InetAddress address, Router router) {
        try {
            serverSocket = new ServerSocket(port, backlog, address);
            this.router = router;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int port, InetAddress address, Router router) {
        try {
            serverSocket = new ServerSocket(port, 5, address);
            this.router = router;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int port, Router router) {
        try {
            serverSocket = new ServerSocket(port);
            this.router = router;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Listening on port " + serverSocket.getLocalPort() + "...");
        try {
            while (true) {
                Socket client = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outputStream = new PrintWriter(client.getOutputStream());
                Request request = requestParser.parseRequest(reader);
                Dispatcher dispatcher = new Dispatcher(router);
                Response response = dispatcher.processRequest(request);
                response.sendResponse(outputStream);
                outputStream.close();
                reader.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
