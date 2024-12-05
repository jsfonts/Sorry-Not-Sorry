package models;

import java.awt.Color;
import models.Tile;
import models.Board;
import java.util.HashMap;

public class Pawn{
    //store the board space it is currently on 
    private Color color;
    private Tile location;
    private int [] startCoords;
    private boolean clicked;
    private static int reds = 0, blues = 0, greens = 0, yellows = 0;
    private int distanceTraveled;

    //START tile offsets
    private static final HashMap<Color, int[][]> startList = new HashMap<Color, int[][]>(){{
        put(Color.RED, new int[][]{{1,4}, {2,4}, {2,5}, {1,5}});
        put(Color.YELLOW, new int[][]{{14,11}, {14,10}, {13, 11}, {13,10}});
        put(Color.BLUE, new int[][]{{5,14}, {4,14}, {5,13}, {4,13}});
        put(Color.GREEN, new int[][]{{11,1}, {11, 2}, {10,1}, {10,2}});
    }};

    public Pawn(Color c){
        color = c;
        distanceTraveled = 0;
        location = Board.startingTile(color);
        clicked = false;
        
        if(color == Color.GREEN)
            startCoords = startList.get(color)[greens++];
        if(color == Color.BLUE)
            startCoords = startList.get(color)[blues++];
        if(color == Color.RED)
            startCoords = startList.get(color)[reds++];
        if(color == Color.YELLOW)
            startCoords = startList.get(color)[yellows++];
    }

    public static void reset(){
        greens = 0;
        blues = 0;
        reds = 0;
        yellows = 0;
    }
    public int getDistanceTraveled()
    {
        return distanceTraveled;
    }

    public Color getColor(){
        return color;
    }

    public Tile getTile(){
        return location;
    }

    public int [] getCoords(){
        if(location.getType() == Tile.TType.START)
            return startCoords;

        return location.getCoords();
    }

    public void setLocation(Tile target, int distance){
        location = target;
        location.setPawnAt(this);
        distanceTraveled += distance;
        System.out.println(color + " Pawn is now at " + location.getType());
    }

    public void resetToHome(Tile t){
        location = t;
        distanceTraveled = 0;
    }

    public void setClicked(){
        clicked = true;
    }

    public boolean isClicked(){
        return clicked;
    }
}