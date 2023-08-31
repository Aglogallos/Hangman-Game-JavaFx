package com.example.hangmangame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class LoadDictionary {
    @FXML
    private Label bookName;

    @FXML
    private TextField input_add_dictionary;

    @FXML
    private Label Current_Dictionary_Code;

    @FXML
    private Label progressLabel;

    public static Vector<String> words = new Vector<>();

    public static String title;
    @FXML
    protected void LoadDictionary(ActionEvent event) throws Exception {
        this.progressLabel.setText("");
        String search_title = this.input_add_dictionary.getText();
        File file = new File("./medialab/" + search_title + ".txt");
        Scanner sc = new Scanner(file);
        title = sc.nextLine();
        System.out.println(title);
        while (sc.hasNextLine()) {
            words.add(sc.nextLine());
        }
        this.progressLabel.setText("Book Loaded - Press Start");
        this.bookName.setText(title);
        this.Current_Dictionary_Code.setText(search_title);

    }

    public static String get_random_word() {
        /* check if words is empty */
        if (words.isEmpty()){
            System.out.println("Load Dictionary");
            return "empty";
        }
        /* Get random Word */
        int index = (int) (Math.random() * words.size());
        String game_word = (String) words.get(index);
        System.out.println("Random Game word is :");
        System.out.println(game_word);
        words.remove(game_word);
        return game_word;
    }
}
