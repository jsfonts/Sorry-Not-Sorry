package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Pawn;
import java.util.Arrays;

public abstract class Player{
    private String name;
    private ArrayList<Pawn> pawns;
    private Color color;
    private static ArrayList<Color> availableColors = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN));

    Player(String n){
        name = n;

        if(!availableColors.isEmpty()){
            color = availableColors.get(0);
            availableColors.remove(0);
        }
        else 
            System.out.println("\n-----No more colors available to assign to players-----\n");
        
        pawns = new ArrayList<Pawn>();
        for(int i = 0; i < 4; i++){
            pawns.add(new Pawn(color));
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