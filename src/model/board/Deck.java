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
    Card[] deck;
    public Deck(String fileName){
        String DATAPATH = fileName;
        StringBuilder sb = new StringBuilder();

        // Open file input stream and read characters into string builder
        try (FileInputStream fis = new FileInputStream(fileName)) {

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
        // Populate board with Tile objects using information from JSON
        for (int i = 0; i < objects.length(); i++) {
            JSONObject obj = objects.getJSONObject(i);

            String type = obj.getString("type");

            Card card;

            switch (type) {
                case "collect":
                    // Construct a collect card
                    card = new Card(obj.getString("text"),
                            obj.getInt("collect"),
                            ActCode.BANKPAY);
                    deck[i] = card;
                    break;

                case "pay":
                    // Construct a pay card
                    card = new Card(obj.getString("text"),
                            obj.getInt("pay"),
                            ActCode.PAYBANK);
                    deck[i] = card;
                    break;

                case "move":
                    // Construct a move card
                    card = new Card(obj.getString("text"),
                            obj.getInt("move"),
                            ActCode.MOVETO);
                    deck[i] = card;
                    break;
                case "moveNoGo":
                    // Construct a move card
                    card = new Card(obj.getString("text"),
                            obj.getInt("move"),
                            ActCode.MOVEN);
                    deck[i] = card;
                    break;

                case "jail":
                    // Construct a move card
                    card = new Card(obj.getString("text"),
                            obj.getInt("moveNoGo"),
                            ActCode.JAIL);
                    deck[i] = card;
                    break;

                case "collectFromAll":
                    // Construct a move card
                    card = new Card(obj.getString("text"),
                            obj.getInt("move"),
                            ActCode.COLLECTALL);
                    deck[i] = card;
                    break;
                default: throw new IllegalStateException("Invalid JSON data from \"" + DATAPATH + "\"");
            }

        }
    }


    public Card draw(){
        Random random = new Random();
        int pos = random.nextInt(deck.length);
        return deck[pos];
    }
}
