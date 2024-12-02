package models;

import java.awt.Color;
import models.Tile;

public class Pawn{
    //store the board space it is currently on 
    private Color color;
    private Tile location;

    public Pawn(Color c){
        color = c;
    }

    public Color getColor(){
        return color;
    }

    public Tile getLocation(){
        return location;
    }

    public void setLocation(Tile target){
        location = target;
        System.out.println(color + " Pawn is now at a" + location.getType());
    }
}