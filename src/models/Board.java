package models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

import controllers.GameController;

import java.awt.Color;
import models.Tile;
import models.Player;
import models.Pawn;
import java.io.Serializable;

public class Board implements Serializable{
    private boolean isPaused = false;
    private static HashMap<Color, Tile> startingTiles;
    private final Color [] colors = {Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE};
    private GameController controller; 

    public Board(GameController c){
        controller = c;
        startingTiles = new HashMap<Color, Tile>();
        setup();
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
        ArrayList<Player> OriginalplayersList = new ArrayList<Player>();
        OriginalplayersList = Player.getOriginalPlayers();
        ArrayDeque<Player> playerCopy = new ArrayDeque<Player>();
        playerCopy = controller.players;
        controller.players = new ArrayDeque<Player>();
        for (int i = 0; i < OriginalplayersList.size(); i++) {
            Player player = OriginalplayersList.get(i);
            for(Player p : playerCopy){
                if(p.getName().equals(player.getName()))
                    controller.players.addLast(p);
            }
        }
        for (Player p : controller.players)  
        {
            for (Pawn pawn : p.getPawns())
            {
                if (p.getColor() == Color.YELLOW)
                {
                    Tile start = startingTile(Color.YELLOW);
                    pawn.resetToHome(start);
                }
                else if(p.getColor() == Color.GREEN)
                {
                    Tile start = startingTile(Color.GREEN);
                    pawn.resetToHome(start);
                }
                else if(p.getColor() == Color.BLUE)
                {
                    Tile start = startingTile(Color.BLUE);
                    pawn.resetToHome(start);
                }
                else if (p.getColor() == Color.RED)
                {
                    Tile start = startingTile(Color.RED);
                    pawn.resetToHome(start);
                }
            }
        } 
    }


    public boolean movePawn(Pawn pawn, int spaces){
        System.out.println("Pawn move has just been called");
        return movePawn(pawn, spaces, true);
    }

    public boolean movePawn(Pawn piece, int spaces, boolean change){     //returns false if its an invalid move

        boolean valid = true;
        Tile destination = piece.getTile();
        Tile original = destination;
        Color pC = piece.getColor();
        int distance = 0;

        if(spaces < 0 && destination.getType() == Tile.TType.START){
            valid = false;
        }
        else if(spaces < 0){             //move backwards
            for(int i = spaces; i < 0; i++){
                destination = destination.prev();
                distance--;
            }
        }
        else{         
            int i;              //move forwards
            for(i = spaces; i > 0; i--){
                if(isEndZoneEntrance(destination.fork(), piece)){
                    destination = destination.fork();
                    distance++;
                }
                else if(destination.getType() == Tile.TType.HOME){
                    break;
                }
                else{
                    destination = destination.next();
                    distance++;
                }
            
            }
            
            if(destination.getType() == Tile.TType.HOME && i > 0){
                //travel to HOME must be exact
                valid = false;
            }

        }
        
        if(destination.getType() == Tile.TType.SLIDE_START && destination.getColor() != piece.getColor()){
            destination = endOfSlide(destination, change);
        }

        //if move is invalid, pawn stays where it is.
        
        if(destination.pawnAt() != null){    //if another pawn is already there 
            if(destination.pawnAt().getColor() == pC){
                valid = false;
                System.out.println("your own pawn is already there");
            }
            else if(change && valid){                           //if its opponents pawn, bump it 
                Pawn pawnFound = destination.pawnAt();
                pawnFound.resetToHome(startingTiles.get(pawnFound.getColor()));
                int [] coord = startingTiles.get(pawnFound.getColor()).getCoords();
                System.out.println("Home of pawn is " + startingTiles.get(pawnFound.getColor()) + " at " + coord[0] + ", " + coord[1]);
            }
        }

        if(valid && change){      //in case a pawn was on the destination square
            System.out.println("Move was valid. Moving pawn there now");
            piece.setLocation(destination, distance);
            destination.setPawnAt(piece);

            if(destination.getType() == Tile.TType.HOME){
                controller.pawnReachedHome(piece);   //removes it from the players inventory
            }

            original.setPawnAt(null);
        }
            

        return valid;
    }

    public boolean isValidMove(Pawn piece, int spaces){
        boolean valid = true;

        valid = movePawn(piece, spaces, false);

        if(!valid)
            System.out.println("It was an invalid move");

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
        startingTiles.get(Color.YELLOW).setNext(current); current.setSecondType(Tile.TType.FIRST);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next(); current.setSecondType(Tile.TType.SECOND);
        current.setCoords(15, 10);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next(); current.setSecondType(Tile.TType.THIRD);
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
        startingTiles.get(Color.GREEN).setNext(current); current.setSecondType(Tile.TType.FIRST);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null)); current.setSecondType(Tile.TType.SECOND);
        current = current.next();
        current.setCoords(10, 0);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null)); current.setSecondType(Tile.TType.THIRD);
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
        current = current.next();
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
        startingTiles.get(Color.RED).setNext(current); current.setSecondType(Tile.TType.FIRST);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null)); current.setSecondType(Tile.TType.SECOND);
        current = current.next();
        current.setCoords(0, 5);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null)); current.setSecondType(Tile.TType.THIRD);
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
        current = current.next();
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
        startingTiles.get(Color.BLUE).setNext(current); current.setSecondType(Tile.TType.FIRST);

        //4 normal tiles
        current.setNext(new Tile(Tile.TType.NORMAL, current, null)); current.setSecondType(Tile.TType.SECOND);
        current = current.next();
        current.setCoords(5, 15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null)); current.setSecondType(Tile.TType.THIRD);
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
        current = current.next();
        current.setCoords(13,15);

        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(14,15);
        current.setNext(new Tile(Tile.TType.NORMAL, current, null));
        current = current.next();
        current.setCoords(15,15);

        //connect
        current.setNext(original);

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

    public Tile endOfSlide(Tile current, boolean change){
        if(current.getType() != Tile.TType.SLIDE_START)
            return current;
        
        int distance = 0;

        while(current.getType() != Tile.TType.SLIDE_END){
            current = current.next();
            distance++;
            if(current.pawnAt() != null && change){
                current.pawnAt().setLocation(startingTiles.get(current.pawnAt().getColor()), distance);
                current.setPawnAt(null);
            }
        }

        return current;
    }
}   //end of Board class