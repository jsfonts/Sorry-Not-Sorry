package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Pawn;
import java.util.Arrays;
import controllers.GameController;

public abstract class Player{
    private static GameController controller;
    private String name;
    protected ArrayList<Pawn> pawns;
    private Color color;
    private static final ArrayList<Color> availableColors = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN));
    private static int nextColor = 0;

    Player(){}

    Player(String n, GameController c){
        name = n;
        controller = c;
        color = availableColors.get(nextColor++);
        
        if(nextColor == 4) 
            nextColor = 0;

        pawns = new ArrayList<Pawn>();
        for(int i = 0; i < 4; i++){
            pawns.add(new Pawn(color));
        }

        System.out.println("Player has been made & assigned " + color);
    }

    public int pawnsLeft(){
        return pawns.size();
    }

    public Color getColor(){
        return color;
    }

    public String getColorString(){
        if(color == Color.RED)
            return "Red";
        if(color == Color.BLUE)
            return "Blue";
        if(color == Color.GREEN)
            return "Green";
        else 
            return "Yellow";
    }

    public String getName(){
        return name;
    }

    public ArrayList<Pawn> getPawns(){
        return pawns;
    }

    public abstract void move(Card c);
}