package ui.startingMenu.sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.game.Game;
import ui.UIGame;


/**
 * Controller to timeFormatMenu.fxml file
 */

// #FF4500

public class TimeFormatController
{

    @FXML private TextField hoursField;
    @FXML private TextField minutesField;
    public int hour;
    public int minute;


    /**
     * Starts the game in time mode game with the choosen time
     * @param event
     */
    public void startGame(ActionEvent event)
    {
        String hours = hoursField.getText();
        String minutes = minutesField.getText();
        if(!validMinutes(minutes) || !validHours(hours))
        {
//            Add error box popping out
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../FXML files/timeFormatException.fxml"));
                Scene scene = new Scene(loader.load(),350,208 );
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else{
            hour = Integer.valueOf(hours);
            minute = Integer.valueOf(minutes);

            // Create monopoly model from options selected in menu
            Game model = new Game(PlayerMenuController.getPlayers());
            UIGame root = new UIGame(model,hour * 60 * 60 + minute * 60);

            // Trigger game logic after UI has loaded
            Platform.runLater(() -> root.start());

            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            // Scene & Stage setup
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
        }


    }


    public void returnButton(ActionEvent event)
    {
        try{

            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML files/gameMode.fxml"));
            Scene scene = new Scene(loader.load(),389,402 );
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }





    /**
     * Checks if the input in TextField minutes is valid. IF it is a number and if it fits the time range of the game.
     * @param minutes
     * @return
     */
    public boolean validMinutes(String minutes)
    {
        boolean flag = minutes.chars().allMatch(Character::isDigit);
        boolean retVal = true;
        if(flag)
        {
            int min = Integer.valueOf(minutes);
            if(min < 0 || min > 59)
            {
                retVal = false;

            }
            return retVal;
        }
        else{
            retVal = false;
            return retVal;
        }
    }

    /**
     * Checks if the input in TextField hours is valid. IF it is a number and if it fits the time range of the game.
     * @param hours
     * @return
     */

    public boolean validHours(String hours)
    {
        boolean flag = hours.chars().allMatch(Character::isDigit);
        boolean retVal = true;
        if(flag)
        {
            int min = Integer.valueOf(hours);
            if(min < 0 || min > 23)
            {
                retVal = false;

            }
            return retVal;
        }
        else{
            retVal = false;
            return retVal;
        }
    }
}


