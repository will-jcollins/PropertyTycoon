package model.board;

import model.actions.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * class representing a deck of potluck cards
 */
public class Deck {
    private LinkedList<Card> deck;

    /**
     * Builds a deck of cards using the information at the path supplied
     * @param path filepath to JSON file with card information
     */
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
        deck = new LinkedList<>();
        // Populate deck with Card objects using information from JSON
        for (int i = 0; i < objects.length(); i++) {
            JSONObject obj = objects.getJSONObject(i);
            Card card;
            card = new Card(obj.getString("text"),
                    new Action(obj.getString("action")));
            deck.add(card);
        }
        // Randomise order of cards
        Collections.shuffle(deck);
    }

    /**
     * Method returns the top card on the deck of cards and puts it at the bottom
     * @return top card from the deck
     */
    public Card draw() {
        // Return top card and place a copy to the bottom
        deck.offer(deck.peek());
        return deck.poll();
    }
}
