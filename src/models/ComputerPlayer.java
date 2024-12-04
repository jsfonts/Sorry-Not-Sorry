package models;

import controllers.GameController;
import models.Player;

public class ComputerPlayer extends Player{

    private static int cpCount;
    
    public ComputerPlayer(GameController controller){
        super("ComputerPlayer " + cpCount++, controller);
    }
    public ComputerPlayer(String name, GameController controller) {
        super(name, controller);
    }

    public void move(Card c){
        // logic
    }
}