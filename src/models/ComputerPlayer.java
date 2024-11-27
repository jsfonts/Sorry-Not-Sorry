package models;

import models.Player;
import java.awt.Color;

public class ComputerPlayer extends Player{
    private static int cpCount;

    public ComputerPlayer(Color c){
        super(String.format("ComputerPlayer %d" , cpCount++), c);
    }

    public void move(){
        // logic
    }
}