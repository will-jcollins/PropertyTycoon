package model.Player;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean askPlayer(String message) {
        Scanner scanner = new Scanner(System.in);
        String playerDecision = "";
        System.out.println(message);
        while(!playerDecision.equals("yes") && !playerDecision.equals("no")){
            playerDecision = scanner.nextLine();
        }
        return (playerDecision.equals("yes"));
    }
}
