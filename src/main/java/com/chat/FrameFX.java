package main.java.com.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.chat.controllers.MainController;

import java.awt.*;

public class FrameFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/window/main.fxml"));
        primaryStage.setTitle("GG Chat");
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);

        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double x = winSize.getMaxX() - primaryStage.getWidth();
        double y = winSize.getMaxY() - primaryStage.getHeight();
        primaryStage.setX(x);
        primaryStage.setY(y);

        primaryStage.show();
    }
}