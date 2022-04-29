package ui.startingMenu.sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class which starts the Main menu and the game
 */
public class MenuStart extends Application {



    @FXML public Label startLabel;
    /**
     * reading the first fxml file.
     * @param primaryStage
     * @throws Exception
     */


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML files/mainMenu.fxml"));
        primaryStage.setTitle("Start");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
