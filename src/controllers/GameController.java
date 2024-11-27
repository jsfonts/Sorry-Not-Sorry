package controllers;

import java.util.Deque;
import models.Player;
import models.Deck;

public class GameController{
    private static GameController instance;
    private Deque<Player> players;
    private Deck deck;

    public GameController(){
        System.out.println("gamecontroller has been made");
    }

    public static GameController getInstance(){
        if(instance == null)
            instance = new GameController();
        
        return instance;
    }

    public void run(){
        //this runs the game until someone wins 
    }

    //------------- helper functions --------------------

}