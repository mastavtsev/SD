package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Getter
@Setter
public class ClientHandler extends Thread {
    private Socket socket;
    private ChatServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            int countSameName = 0;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get the client's name

            clientName = in.readLine();
            if (clientName == null) {
                return;
            }

            synchronized (server.getClients()) {
                boolean nameTaken = false;
                for (ClientHandler client : server.getClients()) {
                    if (client != this && client.getClientName().equals(clientName)) {
                        nameTaken = true;
                        countSameName++;
                        break;
                    }
                }

                if (nameTaken) {
                    clientName += "_" + countSameName;
                }
            }

            // Add the client to the chat
            synchronized (server.getClients()) {
                server.getClients().add(this);
            }

            server.broadcast("Welcome to the chat, " + clientName, this);

            String message;
            do {
                message = in.readLine();
                server.broadcast(clientName + " : " + message, this);

            } while (!message.equals("exit"));

            socket.close();
            server.broadcast(clientName + " has quit", this);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }
}
