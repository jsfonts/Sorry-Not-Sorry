package controllers;

import java.util.List;
import java.util.ArrayDeque;
import models.Player;
import models.Deck;
import views.GameBoard;
import java.awt.Color;

public class GameController{
    private static GameController instance;
    private ArrayDeque<Player> players;
    private Deck deck;
    public static GameBoard gameboard;
    private List<Color> colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

    public GameController(){
        players = new ArrayDeque<Player>();
        gameboard = new GameBoard();
    }

    public static GameController getInstance(){
        if(instance == null)
            instance = new GameController();
        
        return instance;
    }

    public void start(List<String> personNames){
        int i = 0;

        for(String name: personNames){
            players.addLast(new Player(personNames, colors[i++]));
        }
        
        gameBoard = new GameBoard(playerNames); 
        gameBoard.setVisible(true);
    }

    public void nextTurn(){
        //play one persons turn
        //pop them from the queue, do their moves and then put thenm back at the end
    }

}