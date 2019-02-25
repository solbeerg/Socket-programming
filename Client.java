import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        // error message if no IP is given at startup
        if (args.length == 0) {
            System.err.println("No IP entered. You have to enter IP address to server!");
            System.err.println("Example: java Client 127.0.0.1 (This is the default localhost)");
            System.exit(0);
        }

        // IP address and port
        String host = args[0];
        int port = 5555;

        try {
            // new client socket
            Socket client = new Socket(host, port);
            System.out.println("\nConnecting to: " + args[0] + "\n");

            // input and output streams
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            // text from client to server
            String userInput;

            // welcome text
            System.out.println("Currency converter! (Lab-N3, Group 36)");
            System.out.println("Please enter currency to convert:\n");
            System.out.println("Example: Type '20 NOK USD' to convert 20 NOK to USD");
            System.out.println("Type EXIT to quit the converter\n");
            System.out.print("Client > ");

            // communication loop
            while ((userInput = stdIn.readLine()) != null && !userInput.isEmpty()) {
                // write keyboard input to the socket
                output.println(userInput);

                // read message from the socket
                String receivedText = input.readLine();

                // display text at client side, and handle exit and errors
                if (receivedText.equals("exit")) {
                    System.out.println("\nGame Over & Sayonara Sensei!\n");
                    break;
                } else if (receivedText.equals("error")) {
                    System.out.println("\nERROR!!! - Please reload...\n");
                    break;
                } else {
                    System.out.println("Server > " + receivedText);
                    System.out.print("Client > ");
                }
            }

            // close connection after use
            client.close();

        } catch (IOException e) {
            // display any errors
            System.err.println("Error: " + e.toString());
        }
    }
}
