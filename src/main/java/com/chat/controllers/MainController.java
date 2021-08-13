package main.java.com.chat.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import main.java.com.chat.controllers.chatPane.ChatController;
import main.java.com.chat.controllers.simpleTabs.AddPaneController;

public class MainController {

    @FXML public ChatController chatController;
    @FXML public AddPaneController addPaneController;

    @FXML private BorderPane chat;
    @FXML private BorderPane addPane;

    @FXML private void initialize() {
        chatController.injectMainController(this);
        addPaneController.injectMainController(this);
    }

    // server contact

    public void register(String name, String password){

    }
    public void login(String name, String password) {

    }
    public void checkUser(String name){
        //TODO
        //check
        chatController.headerController.addFriend(name);
    }
    public void sendMessage(String username, String message){
        chatController.messagesController.addMessage(username, message);
    }
    public void checkForNews(){

    }

}
