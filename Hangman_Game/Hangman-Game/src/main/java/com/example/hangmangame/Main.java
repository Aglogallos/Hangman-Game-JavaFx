package com.example.hangmangame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.example.hangmangame.Main.primaryStage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage = (Stage)FXMLLoader.load(this.getClass().getResource("main_application.fxml"));
        Scene mainScene = primaryStage.getScene();
        primaryStage.setTitle("Media Lab Hangman");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(e -> Platform.exit()); //close other windows when closing the app
        File myObj = new File("./images/icon.jpg");
        Image image = new Image(myObj.getAbsolutePath());
        primaryStage.getIcons().add(image);
        primaryStage.show();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("instructions.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Instructions");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening Instructions Scene");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}