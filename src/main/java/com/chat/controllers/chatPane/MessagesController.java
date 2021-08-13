package main.java.com.chat.controllers.chatPane;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MessagesController {

    private ChatController chatController;

    public void injectMainController(ChatController controller){
        this.chatController = controller;
    }

    @FXML private AnchorPane messages;

    public void addMessage(String username, String message){
        addMessage(username, message, 0);
    }
    public void addMessage(String username, String message, int id){

    }

}
