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
    private Card[] deck;

    public static void main(String[] args){
        Deck test = new Deck("C:\\Users\\User\\Documents\\GitHub\\propertytycoon\\assets\\jsons\\PotLuck.json");
        for (int i = 0; i < 10; i++) {
            System.out.println(test.draw().toString());
        }
        Deck test2 = new Deck("C:\\Users\\User\\Documents\\GitHub\\propertytycoon\\assets\\jsons\\Opportunity.json");
        for (int i = 0; i < 10; i++) {
            System.out.println(test.draw().toString());
        }
    }

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


            Card card;
            card = new Card(obj.getString("text"),
                    new Action(obj.getString("action")));
            deck[i] = card;

        }
    }


    public Card draw(){
        Random random = new Random();
        int pos = random.nextInt(deck.length);
        return deck[pos];
    }
}
