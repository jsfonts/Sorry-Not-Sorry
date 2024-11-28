package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Pawn;

public abstract class Player{
    private String name;
    private ArrayList<Pawn> pawns;
    private Color color;

    Player(String n, Color c){
        name = n;
        color = c;
        
        pawns = new ArrayList<Pawn>();
        for(int i = 0; i < 4; i++){
            pawns.add(new Pawn(c));
        }

        System.out.println("Player has been made");
    }

    public int pawnsLeft(){
        return pawns.size();
    }

    public Color getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public abstract void move();
}