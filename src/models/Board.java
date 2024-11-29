package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Tile;

public class Board{
    private boolean isPaused = false;
    private ArrayList<Player> players;
    private Tile greenStart;
    private Tile redStart;
    private Tile blueStart;
    private Tile yellowStart;


    public Board(){
        //initialize all the tiles
    }

    public Board(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<String> getPlayerNames() {
        ArrayList<String> names = new ArrayList<String>();

        for(Player p : players)
            names.add(p.getName());

        return names;
    }

    public void setPlayerNames(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void restartGame() {
       
    }

    public void startNewGame() {
 
    }

    public void quitGame() {
    
    }

}