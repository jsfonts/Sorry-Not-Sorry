package models;

import controllers.GameController;
import models.Player;
import models.Card.CardType;

public class ComputerPlayer extends Player{

    private static int cpCount;
    
    public ComputerPlayer(GameController controller){
        super("ComputerPlayer " + cpCount++, controller);
    }
    public ComputerPlayer(String name, GameController controller) {
        super(name, controller);
    }

    public void move(Card c){
        if (c.getType() == Card.CardType.ONE)
        {
            boolean pawnStart = false;
            for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.START)
                {
                    //pick pawn then break. move that pawn out of start. Set a bool to true
                
                }
            }
            if (!pawnStart)
            {
                //move the pawn closest to home if thats valid
            }
        }
        else if(c.getType() == Card.CardType.TWO)
        {
            boolean pawnStart = false;
            for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.START)
                {
                    //pick pawn then break. move that pawn out of start. Set a bool to true
                
                }
            }
            if (!pawnStart)
            {
                //move the pawn closest to home if thats valid
            }
        }
        else if(c.getType() == Card.CardType.THREE)
        {
         /* for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.)
                {
                    
                }
            } */
        }
        else if(c.getType() == Card.CardType.FOUR)
        {
          /*  for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.)
                {
                    //pick pawn then break. move that pawn out of start. Set a bool to true
                
                }
            } */
        }
        else if(c.getType() == Card.CardType.FIVE)
        {
            //move the pawn closest to home if thats valid
        }
        else if (c.getType() == Card.CardType.SEVEN)
        {
            //if the pawn can end up on a slide of another persons color split it so it will do so
            //else move the pawn closest to home 
        }
        else if(c.getType() == Card.CardType.EIGHT)
        {
            //move the pawn closest to home
        }
        else if (c.getType() == Card.CardType.TEN)
        {
            //move the pawn closest to home 10 spaces

        }
        else if (c.getType() == Card.CardType.ELEVEN)
        {
            //if the switch would give more than eleven spaces forward use it else move 11 spaces forward. if cant move 11 spaces
            //and switch is bad skip turn
        }
        else if (c.getType() == Card.CardType.TWELVE)
        {
            //move 12 spaces forward on the one closest to home
        }
        else if(c.getType() == Card.CardType.SORRY)
        {
            //switch with an opponent closest to their own home
        }
    }
}