package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Pawn;
import java.util.Arrays;

public abstract class Player{
    private String name;
    protected ArrayList<Pawn> pawns;
    private Color color;
    private static final ArrayList<Color> availableColors = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN));
    private static int nextColor = 0;

    Player(){}

    Player(String n){
        name = n;
        
        color = availableColors.get(nextColor++);
        if(nextColor == 4) nextColor = 0;

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
            return "Red";
        else 
            return "Red";
    }

    public String getName(){
        return name;
    }

    public ArrayList<Pawn> getPawns(){
        return pawns;
    }

    public abstract void move(Card c);
}