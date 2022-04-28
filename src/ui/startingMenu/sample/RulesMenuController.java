package ui.startingMenu.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RulesMenuController
{


    public void returnButton(ActionEvent event) {
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
