import java.io.*;
import java.net.*;

class Converter extends Thread {

    // private variables for socket and client number
    private Socket clientSocket;
    private int client;

    // converter constructor for new threads
    Converter(Socket clientSocket, int client) {
        this.clientSocket = clientSocket;
        this.client = client;
    }

    // new thread
    public void run() {

        try {
            // input and output streams
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // incoming text
            String inputLine;

            // connection established!
            System.out.println("Client " + client + " is now connected to server.");

            // communication loop
            while ((inputLine = in.readLine()) != null) {
                // input and output strings
                String input = inputLine.toUpperCase();
                String output;

                // client message
                System.out.println("Client " + client + " input: " + inputLine);

                // if exit message, send exit to client
                if (input.equals("EXIT")) {
                    out.println("exit");
                    break;
                }

                try {
                    // read csv file from url (Norges Bank API)
                    URL url = new URL("https://data.norges-bank.no/api/data/EXR?lastNObservations=1&Format=csv-:-semicolon-x");
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                    // variables
                    String[] splited = input.split(" ");
                    double inputValue = Double.parseDouble(splited[0]);
                    String inputFrom = splited[1];
                    String inputTo = splited[2];
                    String lines;
                    String[] line;

                    // read input text and do conversion
                    while (true) {

                        // read input text
                        lines = br.readLine();

                        // halt on invalid currency
                        if (lines == null) {
                            output = ("Could not find currency");
                            break;
                        }

                        // split the lines from the csv file and get date
                        line = lines.split(";");
                        String csvTo = line[2];
                        Double csvValue = Double.parseDouble(line[6]);
                        String csvRatio = line[9];
                        String csvDate = line[5];
                        String csvCurrent = line [10];

                        // ratio for currency calculation
                        double ratio;
                        if (csvRatio.equals("0")) {
                            ratio = 1;
                        } else {
                            ratio = 100;
                        }

                        // if same currency, just stop!
                        if (inputTo.equals(inputFrom)) {
                            output = (inputValue + " " + inputFrom + " = " + inputValue + " " + inputTo +
                                    " (Norges Bank " + csvDate + ")");
                            break;
                        }

                        // convert the correct currency
                        if (inputFrom.equals("NOK")) {
                            // calculates from NOK to selected value (live update)
                            if (csvCurrent.equals("C") && inputTo.equals(csvTo)) {
                                // currency equation
                                double value = ((inputValue * ratio) / csvValue);
                                // output numbers with two decimals
                                String resultTo = String.format("%.2f", value);
                                String resultFrom = String.format("%.2f", inputValue);
                                // write to output stream
                                output = (resultFrom + " " + inputFrom + " = " + resultTo + " " + inputTo +
                                        " (Norges Bank " + csvDate + ")");
                                break;
                            }
                        } else if (inputTo.equals("NOK")) {
                            // calculates from selected value to NOK (live update)
                            if (csvCurrent.equals("C") && inputFrom.equals(csvTo)) {
                                double value = ((csvValue * inputValue) / ratio);
                                String resultTo = String.format("%.2f", value);
                                String resultFrom = String.format("%.2f", inputValue);
                                output = (resultFrom + " " + inputFrom + " = " + resultTo + " " + inputTo +
                                        " (Norges Bank " + csvDate + ")");
                                break;
                            }
                        } else {
                            // first calculate the selected value to NOK then calculate the new currency from this
                            if (csvCurrent.equals("C") && inputFrom.equals(csvTo)) {
                                // currency to NOK
                                double value = ((csvValue * inputValue) / ratio);
                                BufferedReader br2 = new BufferedReader(new InputStreamReader(url.openStream()));
                                String lines2;
                                String[] line2;
                                // second loop to handle calculation from NOK to new currency
                                while ((lines2 = br2.readLine()) != null) {
                                    // read the csv a second time for the new currency
                                    line2 = lines2.split(";");
                                    String csvTo2 = line2[2];
                                    Double csvValue2 = Double.parseDouble(line2[6]);
                                    String csvRatio2 = line2[9];
                                    double ratio2;
                                    if (csvRatio2.equals("0")) {
                                        ratio2 = 1;
                                    } else {
                                        ratio2 = 100;
                                    }
                                    // NOK to new currency
                                    if (csvCurrent.equals("C") && inputTo.equals(csvTo2)) {
                                        double value2 = ((value * ratio2) / csvValue2);
                                        String resultTo = String.format("%.2f", value2);
                                        String resultFrom = String.format("%.2f", inputValue);
                                        output = (resultFrom + " " + inputFrom + " = " + resultTo + " " + csvTo2 +
                                                " (Norges Bank " + csvDate + ")");
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    // close reader
                    br.close();

                    // send output text to client and display text at server
                    out.println(output);
                    System.out.println("Client " + client + " output: " + output);

                } catch (Exception x) {
                    // send error message to client and display error message at server
                    out.println("error");
                    System.out.println("Client " + client + " error: " + x.toString());
                    break;
                }
            }

            // display disconnected text at server side
            System.out.println("Client " + client + " is now disconnected.");

        } catch (IOException e) {
            // display error text at server side
            System.out.println("Error: " + e.toString());
        }
    }
}