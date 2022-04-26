package ui.startingMenu.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.game.Game;
import ui.player.UIPlayers;

import java.awt.event.ActionEvent;

/**
 * Controller to gameMode.fxml file
 */
public class GameMenuController
{
    /**
     * Starts the classical mode game
     * @param event
     */
    //TODO start game in classical mode
    public void classicalMode(javafx.event.ActionEvent event)
    {

    }

    public void returnButton(javafx.event.ActionEvent event)
    {
        try{
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML files/playerMenu.fxml"));
            Scene scene = new Scene(loader.load(),800,600 );
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * Directs user to timeFormatMenu.fxml where the user can choose the time format in which they want to play the game
     * @param event
     */
    public void timeMode(javafx.event.ActionEvent event)
    {
        try{
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML files/timeFormatMenu.fxml"));
            Scene scene = new Scene(loader.load(),300,400 );
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
