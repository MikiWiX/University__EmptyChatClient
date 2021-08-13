package main.java.com.chat.controllers.chatPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.java.com.chat.controllers.MainController;

public class ChatController {

    protected MainController mainController;

    public void injectMainController(MainController controller){
        this.mainController = controller;
    }

    @FXML public HeaderController headerController;
    @FXML public MessagesController messagesController;

    @FXML private void initialize() {
        headerController.injectMainController(this);
        messagesController.injectMainController(this);
    }

    @FXML private AnchorPane messages;
    @FXML private BorderPane header;
    @FXML private BorderPane chat;

    @FXML private void sendMessage(ActionEvent actionEvent){
        TextField textField = (TextField) ((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().get(1);
        String friendName = headerController.getActiveFriend();
        mainController.sendMessage(friendName, textField.getText());
    }
}
