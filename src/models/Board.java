package models;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.GameController;

import java.awt.Color;
import models.Tile;
import models.Player;
import models.Pawn;

public class Board{
    private boolean isPaused = false;
    private ArrayList<Player> players;
    private HashMap<Color, Tile> startingTiles;
    private final Color [] colors = {Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE};
    private GameController controller; 

    public Board(){
        startingTiles = new HashMap<Color, Tile>();
        players = new ArrayList<Player>();
        setup();
        print();
    }

    public Board(ArrayList<Player> players) {
        this.players = players;
        startingTiles = new HashMap<Color, Tile>();
        setup();
        print();
    }

    public void setPlayerNames(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void restartGame() {
       
    }

    public void startNewGame() {
 
    }

    public boolean movePawn(Pawn piece, int spaces){     //returns false if its an invalid move
        boolean valid = true;
        Tile destination = piece.getLocation();
        Color pC = piece.getColor();

        if(spaces < 0){             //move backwards
            for(int i = spaces; i < 0; i++)
                destination = destination.prev();
        }
        else{                       //move forwards
            for(int i = spaces; i > 0; i--){
                if(isEndZoneEntrance(destination.fork(), piece))
                    destination = destination.fork();
                else 
                    destination = destination.next();
                //account for slides
            }
        }

        if(destination.next().getType() == Tile.TType.SLIDE_START)   //if you landed on a slide
            destination = endOfSlide(destination);

        if(destination.pawnAt() != null){    //if another pawn is already there 
            Pawn pawnFound = destination.pawnAt();
            if(pC == pawnFound.getColor())         //if its their own pawn
                valid = false;
            else{                   //if its opponents pawn, bump it back
                pawnFound.setLocation(startingTiles.get(pawnFound.getColor()));
            }
        }


        //if move is invalid, pawn stays where it is.
        //otherwise, update location on and do moving animation
        if(valid){
            piece.setLocation(destination);
        }

        if(destination.getType() == Tile.TType.HOME){
           //remove pawn from player's inventory

        }
        //check if all their pawns are gone

        return valid;
    }

    public boolean movePawn(Pawn p1, int spaces1, Pawn p2, int spaces2){
        if(!movePawn(p1, spaces1))
            return false;
        
        if(!movePawn(p2, spaces2))
            return false;

        return true;
    }

    private void setup(){
        Tile greenStart = new Tile(Tile.TType.START, null, null, Color.GREEN);
        greenStart.setCoords(11,1);
        Tile yellowStart = new Tile(Tile.TType.START, null, null, Color.YELLOW);
        yellowStart.setCoords(14, 11);
        Tile redStart = new Tile(Tile.TType.START, null, null, Color.RED);
        redStart.setCoords(1, 4);
        Tile blueStart = new Tile(Tile.TType.START, null, null, Color.BLUE);
        blueStart.setCoords(4, 14);

        startingTiles.put(Color.YELLOW, yellowStart);
        startingTiles.put(Color.GREEN, greenStart);
        startingTiles.put(Color.RED, redStart);
        startingTiles.put(Color.BLUE, blueStart);

        //slide in front of yellow start
        Tile original = new Tile(Tile.TType.SLIDE_START, null, null);
        Tile current = original;
        current.setCoords(15, 14);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15, 13);
        
        Tile.makefork(current, Color.YELLOW);
        
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15, 12);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null));
        current = current.next();
        current.setCoords(15, 11);
        yellowStart.setNext(current);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords();
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords();
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords();
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords();

        /*
        for(int i = 0; i < 4; i++){     //for each side (and color)
            current = makefirstSlide(current, startingTiles.get(colors[i]));
            
            //startingTiles.get(i).setNext(current);   //this is what the start connects to
            
            for(int j = 0; j < 4; j++){
                current.setNext(new Tile(Tile.TType.NORMAL, current, null));
                current = current.next();
            }

            current.setNext(new Tile(Tile.TType.SLIDE_START, current, null));
            current = makeSecondSlide(current);

            for(int j = 0; j < 2; j++){
                current.setNext(new Tile(Tile.TType.NORMAL, current, null));
                current = current.next();
            }

            if(i < 3){      //for the first 3 sides
                current.setNext(new Tile(Tile.TType.SLIDE_START, current, null));
                current = current.next();
            }
            else            
                current.setNext(original);      //connect it back on the last pass
        }
        System.out.println("There have been " + Tile.count + " tiles made.");
 */
    }

    private Tile makefirstSlide(Tile begin, Tile START){    //this is the one that passes through the endzone and start
        Tile current = begin;
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        
        Tile.makefork(current, START.getColor());
        
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null));
        current = current.next();
        START.setNext(current);

        return current;
    }

    private Tile makeSecondSlide(Tile begin){  //returns slide_end tile
        Tile current = begin;
        
        for(int i = 0; i < 3; i++){
            current.setNext(new Tile(Tile.TType.NORMAL, current, null));
            current = current.next();
        }

        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null));
        current = current.next();

        return current;   
    }

    private boolean isEndZoneEntrance(Tile fork, Pawn piece){
        boolean valid = false;

        //if the fork is the Pawn's color and its the endzone's entrance
        if(fork.getColor() == piece.getColor() && fork.getType() == Tile.TType.ENDZONE_FIRST)
            valid = true;
        
        return valid;
    }

    public Tile endOfSlide(Tile current){
        if(current.getType() != Tile.TType.SLIDE_START)
            return current;
        
        while(current.getType() != Tile.TType.SLIDE_END)
            current = current.next();

        return current;
    }

    public void print(){
        Tile original = startingTiles.get(Color.YELLOW).next();
        Tile current = original.next();
        int i = 1;

        while(current != original && i < 100){
            current = current.next();

            System.out.println(i++);
        }
        System.out.println(i);
    }
}