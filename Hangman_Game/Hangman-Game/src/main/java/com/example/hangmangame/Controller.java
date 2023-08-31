package com.example.hangmangame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;


import static com.example.hangmangame.LoadDictionary.*;


public class Controller {

    public static boolean Playing;

    @FXML
    private Label AvailableWordsLabel;

    @FXML
    private Label Dictionary;

    @FXML
    private ImageView ImageView;

    @FXML
    private Label Possible_Letters_Label;

    @FXML
    private HBox word_hbox;

    @FXML
    private Label TotalPoints;

    @FXML
    private Label Success_Rate;

    @FXML
    private void open_up_add_dictionary(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("add_dictionary.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            Stage stage = new Stage();
            stage.setTitle("Dictionary Settings");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening add_dictionary Scene");
        }
    }

    @FXML
    private void open_up_load_dictionary(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("load_dictionary.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            Stage stage = new Stage();
            stage.setTitle("Load Dictionary");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening Load Dictionary Scene");
        }
    }

    @FXML
    private void solution_dialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Solution");
        alert.setHeaderText(null);
        if(Playing) alert.setContentText("Solution is " + game_word);
        else alert.setContentText("You are not playing !");
        alert.showAndWait();
    }

    public List<Button> word_buttons = new ArrayList<>();
    public static String game_word;
    public int Number_of_Letters_left;
    public int lives;
    public List<Button> Invisible_Key_Buttons = new ArrayList<>();
    public boolean initialise = false;


    @FXML
    public void start() {

        if(!initialise){
            initialise_rounds();
        }
        lives = 6;
        SetImage(lives);
        game_word = get_random_word();

        Number_of_Letters_left = game_word.length();

        Playing = true;
        Successful_Attempts = 0.0F;
        Total_Attempts = 0.0F;
        Success_Rate.setText("0%");

        Available_Words();
        if (game_word.equals("empty")) {
            Dictionary.setText("Load Dictionary!");
            return;
        }
        //Reset
        Possible_letters.clear();
        //Set invisible keys to visible
        if (!Invisible_Key_Buttons.isEmpty()) {
            for (Button invisible_key_button : Invisible_Key_Buttons) {
                invisible_key_button.setVisible(true);
            }
        }

        Dictionary.setText(title);
        System.out.println(title);

        //create a list of buttons
        word_hbox.getChildren().removeAll(word_buttons);
        word_buttons.clear();
        for (int i = 0; i < game_word.length(); i++) {
            word_buttons.add(new Button("_"));
            //word_buttons.add(new Button(String.valueOf(game_word.charAt(i))));
            word_hbox.getChildren().add(word_buttons.get(i));
            word_buttons.get(i).setStyle("-fx-font-size: 2em; ");
            /* when clicked it should display the most common letters in the position of the letter in the Dictionary */
            word_buttons.get(i).setOnAction(actionEvent -> {
                //... do something in here.
            });
        }
        Same_length_stats();
    }

    public List<String> Possible_letters = new ArrayList<>();
    public List<Integer> Quantity_of_Letter = new ArrayList<>();
    public Vector<String> same_length_words = new Vector<>();
    public List<Element> elements = new ArrayList<Element>();


    public void Same_length_stats() {
        /* Find words with equal length with game_word */
        same_length_words.clear();
        elements.clear();
        for (String word : words) {
            if (word.length() == game_word.length() && !word.equals(game_word)) {
                same_length_words.add(word);
            }
        }

        // counter to keep track of how many
        int[][] CharCounter = new int[game_word.length()][26];
        /* set all counters to zero */
        // we have 26 counters for every letter of the game word
        for (int i = 0; i < game_word.length(); i++) {
            for (int z = 0; z < 26; z++) {
                CharCounter[i][z] = 0;
            }
        }
        // Now we count
        for (int i = 0; i < game_word.length(); i++) {
            for (String word : same_length_words) {
                int temp = (int) word.charAt(i) - 65; //Capital Letters
                CharCounter[i][temp]++;
            }
        }
        // add every counter to a List in order to keep track of them when we sort them

        for (int i = 0; i < game_word.length(); i++) { //i = position
            for (int z = 0; z < 26; z++) { //z = letter
                elements.add(new Element(i, z, CharCounter[i][z]));
                //System.out.println(CharCounter[i][z]);
            }
        }
        Collections.sort(elements);
        // reverse so we get the big numbers at front
        Collections.reverse(elements);

        for (int i = 0; i < game_word.length(); i++) {
            Possible_letters.add("");
            System.out.println("---------LOOP " + String.valueOf(i) + "----------" );
            System.out.println(i);
            for (Element element : elements) {
                if (element.position == i && element.quantity > 0) { //q>0 because we need P(q)>0
                    char letter = (char) (element.letter + 65);
                    System.out.println(letter + " quantity :" + String.valueOf(element.quantity));
                    Possible_letters.set(i , Possible_letters.get(i) + String.valueOf(letter) + " ");
                }
            }
            int java_is_dumb = i; //lamda expressions need final expressions
            word_buttons.get(i).setOnAction(e -> {Possible_Letters_Label.setText(Possible_letters.get(java_is_dumb));});
            System.out.println(Possible_letters.get(i));
        }
    }

    public void Available_Words() {
        AvailableWordsLabel.setText(String.valueOf(words.size()));
    }

    public float Successful_Attempts;
    public float Total_Attempts;

    public void Success_Rate(){
        if (Successful_Attempts == 0){
            Success_Rate.setText("0%");
            return;
        } else if (Successful_Attempts==Total_Attempts) {
            Success_Rate.setText("100%");
            return;
        }
        float Success_rate = 100*Successful_Attempts/Total_Attempts;;
        String Success_Rate_Text = String.valueOf(Success_rate);
        String Label = "";
        for (int i = 0; i < 4; i++) {
            Label += String.valueOf(Success_Rate_Text.charAt(i));
        }
        Success_Rate.setText(Label+" %");
    }

    public int Score = 0;

    public void Update_Score(int position){
        int total_same_length_words = same_length_words.size();
        int Element_letter = ((int) game_word.charAt(position)) - 65;
        //we have to convert it to the way we added it  26*i + z
        //int quantity_of_letter = elements.get(26*position + Element_letter).quantity;
        int quantity_of_letter = 0;
        for(Element element:elements){
            if(element.letter == Element_letter && element.position==position){
                quantity_of_letter = element.quantity;
            }
        }
        float percentage = (float)quantity_of_letter / (float) total_same_length_words;

        System.out.println("Total words, quantity of letter , percentage");
        System.out.println(total_same_length_words);
        System.out.println(quantity_of_letter);
        System.out.println(percentage);
        //System.out.println(Element_letter+65);
        if(percentage >= 0.6) {
            Score += 5;
        } else if(percentage >= 0.4){
            Score += 10;
        } else if(percentage >= 0.25){
            Score += 15;
        } else
            Score += 30;
    }

    @FXML
    public void Try_this_Letter(Event event) {
        if (!Playing) {
            return; //If we ain't playing do nothing
        }
        System.out.println(event.getTarget());
        Button btn = (Button) event.getSource(); //temp value
        String button_char = btn.getId();
        System.out.println(btn.getId()); //get id
        btn.setVisible(false);
        Invisible_Key_Buttons.add(btn);
        /* ChecK if the character exist in the game word */
        boolean success = false;
        Total_Attempts++;
        for (int i = 0; i < game_word.length(); i++) {
            if (button_char.equals(String.valueOf(game_word.charAt(i)))) {
                //reveal the letters that exist in the words
                success = true;
                Number_of_Letters_left--;
                word_buttons.get(i).setText(button_char);
                Update_Score(i); //we scored at position i
                TotalPoints.setText(String.valueOf(Score));
                if (Number_of_Letters_left < 1) {
                    System.out.println("Congratulations");
                    //win = true
                    Update_Rounds(true);
                    win_game(true);

                }
            }
        }
        if(!success){
            lives--;
            SetImage(lives);
            //game_over();
            if (lives < 1) {
                //win = false
                Playing = false;
                Update_Rounds(false);
                System.out.println("You lost :(");
                win_game(false);
            }
        } else Successful_Attempts++;
        Success_Rate();
     }

     public void win_game(boolean win){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         if(win) {
             alert.setTitle("Congratulations");
             alert.setHeaderText("You Won !");
             alert.setContentText("Ready for the next one ?");
             File myObj = new File("./images/win.png");
             Image image = new Image(myObj.getAbsolutePath());
             alert.setGraphic(new ImageView(image));
         } else {
             alert.setTitle("Tough luck");
             alert.setHeaderText("You Lost ...");
             alert.setContentText("Want to try again ?");
             File myObj = new File("./images/lost.png");
             Image image = new Image(myObj.getAbsolutePath());
             alert.setGraphic(new ImageView(image));
             }

         ButtonType buttonTypeNextWord = new ButtonType("Next Word", ButtonBar.ButtonData.CANCEL_CLOSE);
         alert.getButtonTypes().setAll(buttonTypeNextWord);
         Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == buttonTypeNextWord){
             start();
         }
     }

     public void SetImage(int lives) {
            File myObj = new File("./images/" + String.valueOf(lives) + ".jpg");
            Image image = new Image(myObj.getAbsolutePath());
            ImageView.setImage(image);
    }

    @FXML
    public void exit_app(){
        Platform.exit();
    }

    @FXML
    public void open_Dictionary_Details(ActionEvent event){
        /*Dictionary: Μέσω ενός popup παραθύρου θα παρουσιάζει το
        ποσοστό των λέξεων του ενεργού λεξικού με 6 γράμματα, 7 έως 9
        γράμματα και 10 ή περισσότερα γράμματα */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("dictionary_details.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            Stage stage = new Stage();
            stage.setTitle("Dictionary Details");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening Dictionary Details Scene");
        }
    }

    public static Vector<String> gamewords_5 = new Vector<>();
    public static Vector<String> results_5 = new Vector<>();
    public static Vector<String> Attempts_per_gameword = new Vector<>();

    public void initialise_rounds() {
        for (int i = 0; i < 5; i++) {
            gamewords_5.add("---");
            results_5.add("---");
            Attempts_per_gameword.add("---");
        }
        initialise = true;
    }

    public void Update_Rounds(Boolean win) {
        //gamewords
        gamewords_5.remove(0);
        gamewords_5.add(game_word);
        //results
        results_5.remove(0);
        if(win){
         results_5.add("Victory");
        }
        else {
            results_5.add("Defeat");
        }
        //attempts
        Attempts_per_gameword.remove(0);
        Attempts_per_gameword.add(String.valueOf((int) Total_Attempts));
    }

    @FXML
    public void Instructions(){
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

    @FXML
    public void Rounds(){
        /*Rounds: Μέσω ενός popup παραθύρου θα παρουσιάζει για τα 5
        τελευταία ολοκληρωμένα παιχνίδια τις παρακάτω πληροφορίες:
        επιλεγμένη λέξη, πλήθος προσπαθειών και νικητή (παίκτης ή
        υπολογιστής).
        */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("rounds.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            Stage stage = new Stage();
            stage.setTitle("Rounds Details");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening Rounds Scene");
        }
    }
}




