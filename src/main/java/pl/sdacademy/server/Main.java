package pl.sdacademy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        int portNumber = 5555;
        System.out.println("Im listening port number: " + portNumber);

        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (true) {

            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                if (clientSocket.isConnected()) {
                    System.out.println("Nowy Klient " + clientSocket.getRemoteSocketAddress().toString());
                }
                PrintWriter out = null;

                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader in;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    String request, response;

                    while ((request = in.readLine()) != null) {
                        response = processRequest(request);
                        out.println(response);
                        if ("Done".equals(request)) {
                            System.out.println("Im done with YOU! HaHaHa HAHAHAHA haha ");
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static String processRequest(String request) {
        System.out.println("Server recive message from > " + request);
        return request;
    }
}
