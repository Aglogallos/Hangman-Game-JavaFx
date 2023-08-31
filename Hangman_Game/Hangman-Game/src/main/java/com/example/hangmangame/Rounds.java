package com.example.hangmangame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static com.example.hangmangame.Controller.gamewords_5;
import static com.example.hangmangame.Controller.results_5;
import static com.example.hangmangame.Controller.Attempts_per_gameword;
/*Rounds: Μέσω ενός popup παραθύρου θα παρουσιάζει για τα 5
τελευταία ολοκληρωμένα παιχνίδια τις παρακάτω πληροφορίες:
επιλεγμένη λέξη, πλήθος προσπαθειών και νικητή (παίκτης ή
υπολογιστής).
*/

public class Rounds{

    @FXML
    private Label Gameword_1;

    @FXML
    private Label Gameword_2;

    @FXML
    private Label Gameword_3;

    @FXML
    private Label Gameword_4;

    @FXML
    private Label Gameword_5;

    @FXML
    private Label Results_1;

    @FXML
    private Label Results_2;

    @FXML
    private Label Results_3;

    @FXML
    private Label Results_4;

    @FXML
    private Label Results_5;

    @FXML
    private Label Attempts_1;

    @FXML
    private Label Attempts_2;

    @FXML
    private Label Attempts_3;

    @FXML
    private Label Attempts_4;

    @FXML
    private Label Attempts_5;

    @FXML
    public void Round_Setup() {
        if(!Controller.Playing) {
            Gameword_1.setText("PLEASE");
            Gameword_5.setText("PLEASE");
            Results_1.setText("DICTIONARY");
            Results_5.setText("DICTIONARY");
            Attempts_1.setText("LOAD");
            Attempts_5.setText("LOAD");
            return;
        }
        //Call the method that we created in the controller
        Gameword_1.setText(gamewords_5.get(4));
        Gameword_2.setText(gamewords_5.get(3));
        Gameword_3.setText(gamewords_5.get(2));
        Gameword_4.setText(gamewords_5.get(1));
        Gameword_5.setText(gamewords_5.get(0));

        Results_1.setText(results_5.get(4));
        Results_2.setText(results_5.get(3));
        Results_3.setText(results_5.get(2));
        Results_4.setText(results_5.get(1));
        Results_5.setText(results_5.get(0));

        Attempts_1.setText(String.valueOf(Attempts_per_gameword.get(4)));
        Attempts_2.setText(String.valueOf(Attempts_per_gameword.get(3)));
        Attempts_3.setText(String.valueOf(Attempts_per_gameword.get(2)));
        Attempts_4.setText(String.valueOf(Attempts_per_gameword.get(1)));
        Attempts_5.setText(String.valueOf(Attempts_per_gameword.get(0)));
    }
}

