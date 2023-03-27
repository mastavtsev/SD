package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private BufferedReader reader;
    private final Socket socket;
    private final Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        Console console = System.console();

        String userName = console.readLine("\nEnter your name: ");
        int countSameName = 0;
        boolean nameTaken = false;

        writer.println(userName);
        client.setUserName(userName);

        try {
            String text;

            do {
                text = console.readLine("[" + userName + "]: ");
                writer.println(text);

            } while (!text.equals("exit"));

            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
