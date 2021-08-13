package main.java.com.chat.controllers.simpleTabs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import main.java.com.chat.controllers.MainController;

import java.io.IOException;

public class AddPaneController {

    private MainController mainController;

    public void injectMainController(MainController controller){
        this.mainController = controller;
    }

    @FXML private BorderPane addPane;

    public void showPane(){
        addPane.getStyleClass().remove("hidden");
    }

    public void hidePane(){
        addPane.getStyleClass().add("hidden");
    }

    @FXML private void hidePane(ActionEvent actionEvent){
        hidePane();
    }

    @FXML private void addFriend(ActionEvent actionEvent){
        TextField textField = (TextField) ((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().get(1);
        mainController.checkUser(textField.getText());
    }

}
