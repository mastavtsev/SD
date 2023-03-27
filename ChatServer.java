package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class ChatServer {
    private final Set<ClientHandler> clients = new HashSet<>();


    public void execute() throws IOException {
        System.out.println("The chat server is running on port 9001");
        ServerSocket listener = new ServerSocket(9001);
        try {
            while (true) {
                ClientHandler newUser = new ClientHandler(listener.accept(), this);
                clients.add(newUser);
                newUser.start();
            }
        } finally {
            listener.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ChatServer chatServer = new ChatServer();
        chatServer.execute();
    }

    void broadcast(String message, ClientHandler currentUser) {
        for (ClientHandler client : clients) {
            if (client != currentUser) {
                client.sendMessage(message);
            }
        }
    }
}
