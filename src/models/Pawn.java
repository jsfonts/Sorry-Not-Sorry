package models;

import java.awt.Color;

public class Pawn{
    //store the board space it is currently on 
    Color color;

    public Pawn(Color c){
        color = c;
    }

    public Color getColor(){
        return color;
    }
}