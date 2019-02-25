import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {

        // port number and client number
        int port = 5555;
        int client = 1;

        // initial display text
        System.out.println("\nCurrency converter! (Lab-N3, Group 36)");
        System.out.println("Server initialized! Waiting for connection...");

        // new sockets
        ServerSocket serverSocket = new ServerSocket(port);

        // listen for new connections, always on
        boolean listener = true;

        // new converter thread and client number
        while (listener) {
            new Converter(serverSocket.accept(), client).start();
            client++;
        }
    }
}