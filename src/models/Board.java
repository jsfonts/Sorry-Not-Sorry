package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Tile;
import models.Player;
import models.Pawn;

public class Board{
    private boolean isPaused = false;
    private ArrayList<Player> players;
    private Tile greenStart;
    private Tile redStart;
    private Tile blueStart;
    private Tile yellowStart;

    public Board(){
        setup();
        print();
    }

    public Board(ArrayList<Player> players) {
        this.players = players;
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

    public boolean move(Pawn piece, int spaces){     //returns false if its an invalid move
        boolean valid = true;

        //actually move it 

        return valid;
    }

    public boolean move(Pawn p1, int spaces1, Pawn p2, int spaces2){
        if(!move(p1, spaces1))
            return false;
        
        if(!move(p2, spaces2))
            return false;

        return true;
    }

    private void setup(){
        greenStart = new Tile(Tile.TType.START, null, null, Color.GREEN);
        yellowStart = new Tile(Tile.TType.START, null, null, Color.YELLOW);
        redStart = new Tile(Tile.TType.START, null, null, Color.RED);
        blueStart = new Tile(Tile.TType.START, null, null, Color.BLUE);

        ArrayList<Tile> starts = new ArrayList<Tile>();
        starts.add(yellowStart);
        starts.add(greenStart);
        starts.add(redStart);
        starts.add(blueStart);

        Tile original = new Tile(Tile.TType.SLIDE_START, null, null);
        Tile current = original;

        for(int i = 0; i < 4; i++){     //for each side
            current = makefirstSlide(current, starts.get(i));
            
            starts.get(i).setNext(current);   //this is what the start connects to
            
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

            if(i < 3){
                current.setNext(new Tile(Tile.TType.SLIDE_START, current, null));
                current = current.next();
            }
            else 
                current.setNext(original);      //connect it back on the last pass
        }
        System.out.println("There have been " + Tile.count + " tiles made.");

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

    public void print(){
        Tile original = yellowStart.next();
        Tile current = original.next();
        int i = 1;

        while(current != original && i < 100){
            current = current.next();

            System.out.println(i++);
        }
    }
}