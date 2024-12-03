package models;

import java.awt.Color;
import models.Tile;

public class Pawn{
    //store the board space it is currently on 
    private Color color;
    private Tile location;
    int [] coords;

    public Pawn(Color c){
        color = c;
        coords = new int[2];
    }

    public Color getColor(){
        return color;
    }

    public Tile getLocation(){
        return location;
    }
    
    public void setCoords(int x, int y){
        coords[0] = x;
        coords[1] = y;
    }

    public int[] getCoords(){
        return coords;
    }

    public void setLocation(Tile target){
        location = target;
        System.out.println(color + " Pawn is now at a" + location.getType());
    }
}