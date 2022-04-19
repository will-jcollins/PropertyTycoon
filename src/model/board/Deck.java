package model.board;

import model.actions.ActCode;
import model.actions.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private static final Random random = new Random();
    private Card[] deck;

    public Deck(String path) {
        StringBuilder sb = new StringBuilder();

        // Open file input stream and read characters into string builder
        try (FileInputStream fis = new FileInputStream(path)) {

            int content;
            while ((content = fis.read()) != -1) {
                sb.append((char) content);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray objects = new JSONArray(sb.toString());
        deck = new Card[objects.length()];
        // Populate deck with Card objects using information from JSON
        for (int i = 0; i < objects.length(); i++) {
            JSONObject obj = objects.getJSONObject(i);

            Card card;
            card = new Card(obj.getString("text"),
                    new Action(obj.getString("action")));
            deck[i] = card;
        }
    }


    public Card draw(){
        int pos = random.nextInt(deck.length);
        return deck[pos];
    }
}
