package ui.startingMenu.sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Player.Player;
import model.game.Game;
import ui.UIGame;

import javax.xml.soap.Text;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Class to control controls in playerMenu.fxml file
 * @author Piotr Klukowski
 */


public class PlayerMenuController implements Initializable
{
    /**
     * List of player choice boxes where each player can choose whether they can be human player, AI player or don't participate in a game
     */
    @FXML public ChoiceBox choicePlayer1;
    @FXML public ChoiceBox choicePlayer2;
    @FXML public ChoiceBox choicePlayer3;
    @FXML public ChoiceBox choicePlayer4;
    @FXML public ChoiceBox choicePlayer5;
    @FXML public ChoiceBox choicePlayer6;

    /**
     * List of textfields
     */

    @FXML public TextField playerName1;
    @FXML public TextField playerName2;
    @FXML public TextField playerName3;
    @FXML public TextField playerName4;
    @FXML public TextField playerName5;
    @FXML public TextField playerName6;


    @FXML public CheckBox playerBox1;
    @FXML public CheckBox playerBox2;
    @FXML public CheckBox playerBox3;
    @FXML public CheckBox playerBox4;
    @FXML public CheckBox playerBox5;
    @FXML public CheckBox playerBox6;

    @FXML public Label player1;
    @FXML public Label player2;
    @FXML public Label player3;
    @FXML public Label player4;
    @FXML public Label player5;
    @FXML public Label player6;

    @FXML public Button startGameButton;


    /**
     * ArrayLists of values from choiceBoxes and textFields
     */

    public ArrayList<String> playerTypes = new ArrayList<>();
    public ArrayList<String> playerNames = new ArrayList<>();
    public ArrayList<ChoiceBox> choiceBoxes = new ArrayList<>();
    public ArrayList<TextField> textFields = new ArrayList<>();
    public ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    public ArrayList<Label> playersLabels = new ArrayList<>();

    private Button backButton;





    /**
     * Function from Initializable interface, used to put input into choiceBoxes
     * and creating ArrayLists
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        choicePlayer1.getItems().addAll("Human player","AI player","No player");
        choicePlayer1.setValue("No player");

        choicePlayer2.getItems().addAll("Human player","AI player","No player");
        choicePlayer2.setValue("No player");

        choicePlayer3.getItems().addAll("Human player","AI player","No player");
        choicePlayer3.setValue("No player");

        choicePlayer4.getItems().addAll("Human player","AI player","No player");
        choicePlayer4.setValue("No player");

        choicePlayer5.getItems().addAll("Human player","AI player","No player");
        choicePlayer5.setValue("No player");

        choicePlayer6.getItems().addAll("Human player","AI player","No player");
        choicePlayer6.setValue("No player");

        choiceBoxes.add(choicePlayer1);
        choiceBoxes.add(choicePlayer2);
        choiceBoxes.add(choicePlayer3);
        choiceBoxes.add(choicePlayer4);
        choiceBoxes.add(choicePlayer5);
        choiceBoxes.add(choicePlayer6);





        textFields.add(playerName1);
        textFields.add(playerName2);
        textFields.add(playerName3);
        textFields.add(playerName4);
        textFields.add(playerName5);
        textFields.add(playerName6);



        checkBoxes.add(playerBox1);
        checkBoxes.add(playerBox2);
        checkBoxes.add(playerBox3);
        checkBoxes.add(playerBox4);
        checkBoxes.add(playerBox5);
        checkBoxes.add(playerBox6);

        playersLabels.add(player1);
        playersLabels.add(player2);
        playersLabels.add(player3);
        playersLabels.add(player4);
        playersLabels.add(player5);
        playersLabels.add(player6);




    }

    /**
     * Start the game button and its functions. It creates list with user names and a list with player types(human player, AI player)
     * In order to proceed with the game eash choiceBox with the word ready next to it has to be checked. If its not an approopriate message will be shown.
     * @param event
     */
    public void startMenuButton(ActionEvent event)
    {

        boolean validInput = true;
        int checkedBoxes = 0; // Number of checkboxes that are ticked

        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected() ) {
                checkedBoxes += 1;
            }
        }

        for (int i = 0; i < checkedBoxes; i++) {
            if (choiceBoxes.get(i).equals("No player")) {
                validInput = false;

            }

            if (textFields.get(i).equals("")) {
                validInput = false;
            }
        }

        if (checkedBoxes < 2) {
            validInput = false;
        }

        System.out.println(checkedBoxes);
        System.out.println(validInput);


        if (validInput)
        {
            try {

                ArrayList<Player> players = new ArrayList<>();





                for (int i = 0; i < checkedBoxes; i++) {
                    players.add(new Player(i,textFields.get(i).getText(),choiceBoxes.get(i).getValue().toString().equals("AI player")));
                }



                ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

                // Create monopoly model from options selected in menu
                Game model = new Game(players);
                UIGame root = new UIGame(model);

                // Trigger game logic after UI has loaded
                Platform.runLater(() -> root.start());

                // Scene & Stage setup
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                stage.show();

//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("../FXML files/gameMode.fxml"));
//                Scene scene = new Scene(loader.load(),389,402 );
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.show();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else{
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../FXML files/StartException.fxml"));
                Scene scene = new Scene(loader.load(), 350,208);
                Stage stage = new Stage();
                stage.setTitle("ERROR");
                stage.setScene(scene);
                stage.show();
            }catch(Exception ex)
            {
                ex.printStackTrace();

            }
        }
    }

    /**
     * return butto implementation. Allows the user to go back to main game menu
     * @param event
     */

    public void returnButton(ActionEvent event)
    {
        try{

            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML files/mainMenu.fxml"));
            Scene scene = new Scene(loader.load(),600,400);
            Stage stage = new Stage();
            stage.setTitle("Main menu");
            stage.setScene(scene);
            stage.show();



        }catch(Exception exception)
        {
            exception.printStackTrace();
        }



    }
}
