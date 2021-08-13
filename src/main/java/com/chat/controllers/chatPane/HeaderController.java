package main.java.com.chat.controllers.chatPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderController {

    private ChatController chatController;

    public void injectMainController(ChatController controller){
        this.chatController = controller;
    }

    @FXML private BorderPane header;

    @FXML
    public HBox contactPane;
    @FXML
    public ComboBox<String> moreContacts;

    @FXML
    private void initialize() {
        contactPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            reorderContactList();
        });
    }

    private Double defaultBoxSize = callDummyComboBox("...");
    private List<Button> friendsButtons = new ArrayList<>(Arrays.asList(
            createFriendTab("Friend0"),
            createFriendTab("Friend1"),
            createFriendTab("Friend2"),
            createFriendTab("Friend3"),
            createFriendTab("Friend4")
    ));
    private Button activeFriend = friendsButtons.get(0);
    private List<Double> friendsWidth = callDummyScene(friendsButtons);

    public void addFriend(String name){
        Button button = createFriendTab(name);
        friendsButtons.add(button);
        friendsWidth = callDummyScene(friendsButtons);
        activateContactTabFromButton(button);
    }

    private Button createFriendTab(String name) {
        Button button = new Button(name);
        button.getStyleClass().addAll("topButton", "friendButton");
        button.setOnAction(this::activateContactTabFromButton);
        return button;
    }

    private void reorderContactList() {
        Platform.runLater(() -> {
            layoutButtons(friendsWidth, defaultBoxSize, contactPane.getWidth());
        });
    }

    /* reorderContactList() sub-functions */

    private Double callDummyComboBox(String str){
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/css/style.css");

        ComboBox<String> newComboBox = new ComboBox<>();
        newComboBox.setValue(str);
        newComboBox.getStyleClass().addAll("topButton", "friendButton");
        root.getChildren().add(newComboBox);

        root.applyCss();
        root.layout();

        return newComboBox.getWidth();
    }
    private List<Double> callDummyScene(List<Button> children) {
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/css/style.css");
        List<Double> sizes = new ArrayList<>();

        for (Button child : children){
            Button button = new Button(child.getText());
            button.getStyleClass().addAll("topButton", "friendButton");
            root.getChildren().add(button);
        }
        root.applyCss();
        root.layout();
        for (int i=0; i<children.size(); i++){
            sizes.add( ((Button)root.getChildren().get(i)).getWidth());
        }
        return sizes;
    }
    private void layoutButtons(List<Double> buttonsSizes, Double comboBox, Double space) {
        contactPane.getChildren().clear();
        moreContacts.getSelectionModel().clearSelection();
        moreContacts.getItems().clear();
        double sum = 0.0;

        for (int i=0; i<buttonsSizes.size(); i++){
            sum += buttonsSizes.get(i);
            contactPane.getChildren().add(0, friendsButtons.get(i));

            if(sum > space) {
                space -= comboBox;

                while(sum > space && i >= 0) {
                    sum -= buttonsSizes.get(i);
                    contactPane.getChildren().remove(0);
                    i--;
                }
                int displayedCount = ++i;
                contactPane.getChildren().add(0, moreContacts);
                for (int j=i; j<buttonsSizes.size(); j++){
                    moreContacts.getItems().add(friendsButtons.get(j).getText());
                    if(friendsButtons.get(j).getStyle().contains("blue")) {
                        moreContacts.getSelectionModel().select(moreContacts.getItems().size()-1);
                    }
                }
                return;
            }
        }
    }

    /* END OF reorderContactList() sub-functions */



    @FXML
    private void activateContactTabFromButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        activateContactTabFromButton(button);
    }

    private void activateContactTabFromButton(Button button) {
        int newFirstIndex = 0;
        // reset all buttons
        for (int i=0; i<friendsButtons.size(); i++){
            Button child = friendsButtons.get(i);
            child.getStyleClass().remove("blue");
            if(child == button){
                newFirstIndex = i;
            }
        }

        setFirstOnFriendList(newFirstIndex);
        button.getStyleClass().add("blue");
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void activateContactTabFromComboBox(ActionEvent actionEvent) {
        ComboBox<String> comboBox = (ComboBox<String>) actionEvent.getSource();
        String val = comboBox.getValue();
        if(val != null){
            int newFirstIndex = 0;
            for (int i=0; i<friendsButtons.size(); i++){
                Button child = friendsButtons.get(i);
                child.getStyleClass().remove("blue");
                if( child.getText().equals( val )){
                    newFirstIndex = i;
                }
            }

            setFirstOnFriendList(newFirstIndex);
            friendsButtons.get(0).getStyleClass().add("blue");
        }
    }

    private void setFirstOnFriendList(int index) {
        Button button = friendsButtons.get(index);
        friendsButtons.remove(index);
        friendsButtons.add(0, button);
        reorderContactList();
    }

    @FXML private void openAddFriendTab(ActionEvent actionEvent) {
        chatController.mainController.addPaneController.showPane();
    }

    public String getActiveFriend(){
        return activeFriend.getText();
    }
}
