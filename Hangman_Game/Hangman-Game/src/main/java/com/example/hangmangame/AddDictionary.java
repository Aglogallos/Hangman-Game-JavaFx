package com.example.hangmangame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDictionary {

    @FXML
    private Label bookName;

    @FXML
    private TextField input_add_dictionary;

    @FXML
    private Label Current_Dictionary_Code;

    @FXML
    private Label progressLabel;

    @FXML
    protected void searchDictionary(ActionEvent event) {
        this.progressLabel.setText("");
        String search_title = this.input_add_dictionary.getText();
        if (!search_title.isEmpty()) {
            this.progressLabel.setText("Make request ...");

            try {
                URL obj = new URL(this.formatSearchRequest(search_title));
                HttpURLConnection con = (HttpURLConnection)obj.openConnection();
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                this.progressLabel.setText("Handle response ...");
                this.updateUI(response.toString() , search_title);
            } catch (Exception var9) {
                System.out.println(var9.toString());
                this.progressLabel.setText(var9.toString());
            }

        } else {
            this.progressLabel.setText("Invalid search input ...");
        }
    }

    private String formatSearchRequest(String str) {
        String url = "https://openlibrary.org/books/" + str + ".json";
        return url.replace(" ", "+");
    }

    private void updateUI(String response , String search_title) {
        System.out.println(response);
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(response);
            JSONObject response_object = (JSONObject)obj;

            String description = response_object.get("description").toString();
            String title = response_object.get("title").toString();

            if (this.Dictionary(description , search_title , title)) {
                this.progressLabel.setText("Book found ...");
                this.bookName.setText((String) response_object.get("title"));
                this.Current_Dictionary_Code.setText((String) response_object.get("key"));
            }
        } catch (ParseException var6) {
            System.out.println(var6.toString());
            this.progressLabel.setText(var6.toString());
        }

    }
    private boolean Dictionary(String description , String search_title , String title) {
        File tempFile = new File("./medialab/" + search_title + ".txt");
        if(tempFile.exists()) {
            System.out.println("This Dictionary already exists");
            this.progressLabel.setText("This Dictionary already exists");
            /*Load the existing dictionary in the game */
            return false;
        }
        /* Regex wins */
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m1 = p.matcher(description);
        Vector<String> words = new Vector<>();
        System.out.println("Words from string \"" + description + "\" : ");
        int count_9 = 0; /* count the words that contain 9 words or more */

        while (m1.find()) {
            if (m1.group().length() > 5) {
                if (!words.contains(m1.group())) { /* include each word once */
                    words.add(m1.group().toUpperCase());
                    if(m1.group().length() > 8) count_9++;
                }
            }
        }

        if(words.size() < 20) {
            System.out.println("Undersize Exception");
            this.progressLabel.setText("Book Description is too small");
            return false;
        }
        else if(words.size()/count_9 > 5) { /*we want >=20% of the words to be 9 letters or more */
            System.out.println("Unbalanced Exception");
            this.progressLabel.setText("Book Description contains small words");
            return false;
        }
        else {
            /* Put this dictionary in a text file */
            try {
                File myObj = new File("./medialab/" + search_title + ".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                    try {
                        FileWriter myWriter = new FileWriter("./medialab/" + search_title + ".txt");
                        myWriter.write(title + "\n");
                        for (String word : words) {
                            myWriter.write(word + "\n");
                            System.out.println(word);
                        }
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred while writing.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred in the creation process.");
                e.printStackTrace();
            }
        }
    return true;
    }
}

