package main.java.com.chat;

import javafx.application.Application;
import main.java.com.chat.server.Server;

public class ChatClient {

    private static Server server;

    private ChatClient() {};

    public static void main (String[] args) {

//        try{
//            server = new Server("192.168.0.18", 5600);
//
//            System.out.println("connected...");
//            String reply = server.request("qwertyuiopasdfghjklzxcvbnm");
//
//            System.out.println(reply);
//
//        } catch (IOException e) {
//            System.out.println("Error connecting to server");
//        }

        Application.launch(FrameFX.class, args);

    }
}
