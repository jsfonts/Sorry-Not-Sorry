package models;

import models.Player;

public class ComputerPlayer extends Player{
    private static int cpCount;

    public ComputerPlayer(){
        super(String.format("ComputerPlayer %d" , cpCount++));
    }

    public void move(){
        // logic
    }
}