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
    private static HashMap<Color, Tile> startingTiles;
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
        //print();
    }

    public void setPlayerNames(ArrayList<Player> players) {
        this.players = players;
    }

    public static Tile startingTile(Color c){
        return startingTiles.get(c);
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

        System.out.println("Pawn has been move attempted");
        boolean valid = true;
        Tile destination = piece.getTile();
        Color pC = piece.getColor();
        int distance = 0;

        if(spaces < 0){             //move backwards
            for(int i = spaces; i < 0; i++){
                destination = destination.prev();
                distance--;
            }
        }
        else{         
            int i = spaces;              //move forwards
            for(;i > 0; i--){
                if(isEndZoneEntrance(destination.fork(), piece)){
                    destination = destination.fork();
                    distance++;
                }
                else if(destination.getType() == Tile.TType.SLIDE_START && destination.getColor() != piece.getColor()){
                    destination = endOfSlide(destination);
                }
                else{
                    destination = destination.next();
                    distance++;
                }
            
            }

            if(destination.getType() == Tile.TType.HOME && i > 0){
                //travel to HOME must be exact
                return false;
            }
        }

        if(destination.pawnAt() != null){    //if another pawn is already there 
            Pawn pawnFound = destination.pawnAt();
            if(pC == pawnFound.getColor())         //if its their own pawn
                valid = false;
            else{                   //if its opponents pawn, bump it back
                pawnFound.resetToHome(startingTiles.get(pawnFound.getColor()));
            }
        }

        //if move is invalid, pawn stays where it is.
        //otherwise, update location on and do moving animation
        if(valid){
            piece.setLocation(destination, distance);
            //destination.setPawnAt(piece);
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

    public boolean isValidMove(Pawn piece, int spaces){
        boolean valid = true;

        Tile original = piece.getTile();

        valid = movePawn(piece, spaces);

        if(!valid)
            System.out.println("It was an invalid move");

        piece.setLocation(original, 0);

        return valid;
    }

    public void swapPawns(Pawn p1, Pawn p2){
        Tile p1Location = p1.getTile();
        p1.setLocation(p2.getTile(), 0);
        p2.setLocation(p1Location, 0);
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

        //SOUTH SIDE/////////////
        //slide in front of yellow start
        Tile original = new Tile(Tile.TType.SLIDE_START, null, null, Color.YELLOW);
        Tile current = original;
        current.setCoords(15, 14);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15, 13);
        
        Tile.makefork(current, Color.YELLOW);
        
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15, 12);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15, 11);
        startingTiles.get(Color.YELLOW).setNext(current);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15, 10);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15, 9);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15, 8);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15, 7);

        //yellow slide
        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15,6);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15, 5);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15, 4);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15, 3);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.YELLOW));
        current = current.next();
        current.setCoords(15,2);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15,1);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15,0);

        //WEST SIDE////////////////

        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(14, 0);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(13,0);
        
        Tile.makefork(current, Color.GREEN);
        
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(12, 0);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(11, 0);
        startingTiles.get(Color.GREEN).setNext(current);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(10, 0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(9, 0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(8, 0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(7, 0);

        //GREEN slide
        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(6,0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(5, 0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(4, 0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.GREEN));
        current = current.next();
        current.setCoords(3, 0);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.GREEN));
        current.setCoords(2,0);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(1,0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0,0);

        //NORTH SIDE/////////////

        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.RED));
        current = current.next();
        current.setCoords(0, 1);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.RED));
        current = current.next();
        current.setCoords(0,2);
        
        Tile.makefork(current, Color.RED);
        
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.RED));
        current = current.next();
        current.setCoords(0, 3);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.RED));
        current = current.next();
        current.setCoords(0, 4);
        startingTiles.get(Color.RED).setNext(current);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0, 5);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0, 6);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0, 7);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0, 8);

        //RED slide
        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.RED));
        current = current.next();
        current.setCoords(0,9);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.RED));
        current = current.next();
        current.setCoords(0, 10);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.RED));
        current = current.next();
        current.setCoords(0, 11);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.RED));
        current = current.next();
        current.setCoords(0, 12);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.RED));
        current.setCoords(0,13);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0,14);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(0,15);

        //EAST SIDE//////////////

        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(1, 15);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(2,15);
        
        Tile.makefork(current, Color.BLUE);
        
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(3, 15);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(4, 15);
        startingTiles.get(Color.BLUE).setNext(current);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(5, 15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(6, 15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(7, 15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(8, 15);

        //BLUE slide
        current.setNext(new Tile(Tile.TType.SLIDE_START, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(9,15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(10, 15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(11, 15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null, Color.BLUE));
        current = current.next();
        current.setCoords(12, 15);
        current.setNext(new Tile(Tile.TType.SLIDE_END, current, null, Color.BLUE));
        current.setCoords(13,15);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(14,15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15,15);

        //connect
        current.setNext(original);
        System.out.println("There have been " + Tile.count + " tiles made.");

    }

    private boolean isEndZoneEntrance(Tile fork, Pawn piece){
        if(fork == null)
            return false;
        
        boolean valid = false;

        //if the fork is the Pawn's color and its the endzone's entrance
        if(fork.getColor() == piece.getColor() && fork.getType() == Tile.TType.ENDZONE_FIRST)
            valid = true;
        
        return valid;
    }

    public Tile endOfSlide(Tile current){
        if(current.getType() != Tile.TType.SLIDE_START)
            return current;
        
        int distance = 0;

        while(current.getType() != Tile.TType.SLIDE_END){
            current = current.next();
            distance++;
            if(current.pawnAt() != null)
                current.pawnAt().setLocation(startingTiles.get(current.pawnAt().getColor()), distance);
        }

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