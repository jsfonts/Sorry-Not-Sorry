package models;

import java.util.ArrayList;
import java.awt.Color;
import models.Pawn;
import java.util.Arrays;
import controllers.GameController;

public abstract class Player{
    protected static GameController controller;
    private String name;
    protected ArrayList<Pawn> pawns;
    protected Color color;
    private static final ArrayList<Color> availableColors = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN));
    private static int nextColor = 0;

    Player(){}

    Player(String n, GameController c){
        name = n;
        controller = c;
        color = availableColors.get(nextColor++);
        
        if(nextColor == 4) 
            nextColor = 0;

        pawns = new ArrayList<Pawn>();
        for(int i = 0; i < 4; i++){
            pawns.add(new Pawn(color));
        }

        System.out.println("Player has been made & assigned " + color);
    }

    public int pawnsLeft(){
        return pawns.size();
    }

    public Color getColor(){
        return color;
    }

    public String getColorString(){
        if(color == Color.RED)
            return "Red";
        if(color == Color.BLUE)
            return "Blue";
        if(color == Color.GREEN)
            return "Green";
        else 
            return "Yellow";
    }

    public String getName(){
        return name;
    }

    public ArrayList<Pawn> getPawns(){
        return pawns;
    }

    public abstract void move(Card c);

    public boolean hasValidMoves(Card selectedCard){
        Tile tile;
        Card.CardType type = selectedCard.getType();
        ArrayList<Pawn> pawnsThatCanMove = new ArrayList<Pawn>();
        ArrayList<Pawn> opponentPawns = new ArrayList<Pawn>();
        for(Pawn p : controller.getPawns()){
            if(p.getColor() != color)
                opponentPawns.add(p);
        }

        for(Pawn p : getPawns()){
            tile = p.getTile();
            if(type == Card.CardType.ONE){
                if(controller.isValidMove(p, 1))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.TWO){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, 2))
                    pawnsThatCanMove.add(p);
                if(tile.getType() == Tile.TType.START)
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.THREE){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, 3))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.FOUR){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, -4))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.FIVE){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, 5))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.EIGHT){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, 8))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.TEN){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, 10))
                    pawnsThatCanMove.add(p);
                if(controller.isValidMove(p, -1))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.ELEVEN){

            }
            else if(type == Card.CardType.TWELVE){
                if(tile.getType() != Tile.TType.START && controller.isValidMove(p, 12))
                    pawnsThatCanMove.add(p);
            }
            else if(type == Card.CardType.SORRY){
                boolean validOp = false;
                for(Pawn op : opponentPawns){
                    if(op.getTile().getType() != Tile.TType.HOME && op.getTile().getType() != Tile.TType.START && op.getTile().getType() != Tile.TType.ENDZONE)
                        validOp = true;
                }

                if(tile.getType() == Tile.TType.START && validOp)
                    pawnsThatCanMove.add(p);
            }
        }
        
        controller.setValidPawns(pawnsThatCanMove);
 
        return pawnsThatCanMove.size() > 0;
    }

    public void removePawn(Pawn p){
        pawns.remove(p);
    }
}