package com.example.hangmangame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Vector;

import static com.example.hangmangame.LoadDictionary.words;


public class DictionaryDetails {

    @FXML
    private Label Label_6;

    @FXML
    private Label Label_7to9;

    @FXML
    private Label Label_m10;

    @FXML
    private Button Show_Details;

    @FXML
    private Label Dictionary;


    @FXML
    public void show_Dictionary_Details(){
        if(words.isEmpty()){
            Dictionary.setText("Load Dictionary ! ");
            return;
        }
        Dictionary.setText(LoadDictionary.title);
        words_len_6.clear();
        words_len_7to9.clear();
        words_len_m10.clear();
        for (String word : words) {
            if (word.length() >= 10) {
                words_len_m10.add(word);
            }
            else if (word.length() >= 7) {
                words_len_7to9.add(word);
            }
            else {
                words_len_6.add(word);
            }
        }
        float p_6 = (float) words_len_6.size() / (float) words.size();
        p_6 /= Math.pow(10, (int) Math.log10(p_6));
        p_6 = ((int) (p_6 * 1000) / 10.0f); // <-- performs one digit floor
        Label_6.setText(String.valueOf(p_6) + " %");

        float p_7to9 = (float) words_len_7to9.size() / (float) words.size();
        p_7to9 /= Math.pow(10, (int) Math.log10(p_7to9));
        p_7to9 = ((int) (p_7to9 * 1000)) / 10.0f; // <-- performs one digit floor
        Label_7to9.setText(String.valueOf(p_7to9) + " %");

        float p_m10 = (float) words_len_m10.size() / (float) words.size();
        p_m10 /= Math.pow(10, (int) Math.log10(p_m10));
        p_m10 = ((int) (p_m10 * 1000)) / 10.0f; // <-- performs one digit floor
        Label_m10.setText(String.valueOf(p_m10) + " %");
    }

    public Vector<String> words_len_6 = new Vector<>();
    public Vector<String> words_len_7to9 = new Vector<>();
    public Vector<String> words_len_m10 = new Vector<>();
}
