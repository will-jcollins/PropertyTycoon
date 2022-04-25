package ui.startingMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller class to mainMenu.fxml file
 */
public class Controller {

    /**
     * Allows user to go to player menu. Also closes the main menu window.
     * @param event
     */
    public void mainMenuButton(ActionEvent event)
    {
        try{
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXML files/playerMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),800,600);
            Stage stage = new Stage();
            stage.setTitle("Player menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * it opens rules of the game
     * @param event
     */

    //TODO add rule window and write couple of rules into it
    public void rulesMenuButton(ActionEvent event)
    {


    }

    /**
     * Opens settings menu
     * @param event
     */

    //TODO settings window as soon as we discuss which settings are needed
    public void settingsMenuButton(ActionEvent event)
    {

    }



}
