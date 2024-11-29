package models;

import models.Player;

public class ComputerPlayer extends Player{

    private static int cpCount;
    
    public ComputerPlayer(){
        super("ComputerPlayer " + cpCount++);
    }
    public ComputerPlayer(String name) {
        super(name);
    }

    public void move(){
        // logic
    }
}