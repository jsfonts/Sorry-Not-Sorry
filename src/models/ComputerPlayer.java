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
        GameController controller = GameController.getInstance();
        System.out.println("Computer Player Moving. " + c.getType());

        if (c.getType() == Card.CardType.ONE)
        {
            int distancetraveled = 0;
            Pawn moveP = null;
            boolean pawnStart = false;
            for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.START && controller.isValidMove(p,1))
                {
                    moveP = p;
                    pawnStart = true;
                    break;
                }
            }
            if (!pawnStart)
            {
                for(Pawn p : getPawns()){
                    if (p.getDistanceTraveled() > distancetraveled && controller.isValidMove(p,1))
                    {
                         distancetraveled = p.getDistanceTraveled();
                         moveP = p;
                    }
                }
            }
            if (moveP != null)
            {
            controller.movePawn(moveP, 1);
            }
            else
                return;

        }
        else if(c.getType() == Card.CardType.TWO)
        {
            Pawn moveP = null;
            int distancetraveled = 0;
            boolean pawnStart = false;
            for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.START && controller.isValidMove(p,1))
                {
                    moveP = p;
                    pawnStart = true;
                    break;       
                }
            }
            if (!pawnStart)
            {
                for(Pawn p : getPawns()){
                    if (p.getDistanceTraveled() > distancetraveled && controller.isValidMove(p,2))
                    {
                         distancetraveled = p.getDistanceTraveled();
                         moveP = p;
                    }
                }
                controller.movePawn(moveP, 2);
            }

            if (moveP != null)
            {
                controller.movePawn(moveP, 1);
            }
            else
                return;
        }
        else if(c.getType() == Card.CardType.THREE)
        {
            Pawn moveP = null;
            int distancetraveled = 0;
            for(Pawn p : getPawns()){
                if (p.getDistanceTraveled() > distancetraveled && p.getTile().getType() != Tile.TType.START && controller.isValidMove(p,3))
                {
                     distancetraveled = p.getDistanceTraveled();
                     moveP = p;
                }
            }
            if (moveP != null)
            {
                controller.movePawn(moveP, 3);
            }
            else
                return;
        }
        else if(c.getType() == Card.CardType.FOUR)
        {
            Pawn moveP = null;
            boolean boolPawn = false;
            for(Pawn p : getPawns()){
                if(p.getTile().isFirstThree(color))    //if its one of the first 3 tiles
                {
                    moveP = p;
                    boolPawn = true;
                    break;
                    //pick pawn then break. move that pawn out of start. Set a bool to true
                
                }
            } 
            if (moveP != null)
            {
                controller.movePawn(moveP, -4);
            }
            else if (!boolPawn)
            {
                int distancetraveled = 0;
                for(Pawn p : getPawns()){
                    if (p.getDistanceTraveled() > distancetraveled && p.getTile().getType() != Tile.TType.START && controller.isValidMove(p,-4))
                    {
                         distancetraveled = p.getDistanceTraveled();
                         moveP = p;
                    }
                }
                if(moveP != null)
                    controller.movePawn(moveP, -4);
            }
            else
                return;

        }
        else if(c.getType() == Card.CardType.FIVE)
        {
            Pawn moveP = null;
            int distancetraveled = 0;
            for(Pawn p : getPawns()){
                if (p.getDistanceTraveled() > distancetraveled && controller.isValidMove(p,5) && p.getTile().getType() != Tile.TType.START)
                {
                     distancetraveled = p.getDistanceTraveled();
                     moveP = p;
                }
            }
            if (moveP != null)
            {
                controller.movePawn(moveP, 5);
            }
            else
                return;
        }
        else if (c.getType() == Card.CardType.SEVEN) //LATER IMPLEMENT SPLITTING LOGIC IF IT MAKES THEM END UP ON A SLIDE
        {
            Pawn moveP = null;     
            int distancetraveled = 0;
            for(Pawn p : getPawns()){
                if (p.getDistanceTraveled() > distancetraveled && p.getTile().getType() != Tile.TType.START && controller.isValidMove(p, 7))
                {
                     distancetraveled = p.getDistanceTraveled();
                     moveP = p;
                }
            }
            if (moveP != null)
            {
                controller.movePawn(moveP, 7);
            }
            else
                return;
        }
        else if(c.getType() == Card.CardType.EIGHT)
        {
            Pawn moveP = null;
            int distancetraveled = 0;
            for(Pawn p : getPawns()){
                if (p.getDistanceTraveled() > distancetraveled && p.getTile().getType() != Tile.TType.START && controller.isValidMove(p,8))
                {
                     distancetraveled = p.getDistanceTraveled();
                     moveP = p;
                }
            }
            if (moveP != null)
            {
                controller.movePawn(moveP, 8);
            }
            else
                return;
        }
        else if (c.getType() == Card.CardType.TEN)
        {
            int distancetraveled = 0;
            Pawn moveP = null;
            for(Pawn p : getPawns()){
                if (p.getDistanceTraveled() > distancetraveled && p.getTile().getType() != Tile.TType.START && controller.isValidMove(p,10))
                {
                    distancetraveled = p.getDistanceTraveled();
                    moveP = p;
                }
            }
            if (moveP != null)
            {
                controller.movePawn(moveP, 10);
            }
            else if (moveP == null)
            {
                distancetraveled = 0;
                for(Pawn p : getPawns())
                {
                    if (p.getDistanceTraveled() > distancetraveled && controller.isValidMove(p,-1))
                    {
                        distancetraveled = p.getDistanceTraveled();
                        moveP = p;
                    }
                }
                if (moveP != null)
                {
                    controller.movePawn(moveP, -1);
                }
            }
        }
        else if (c.getType() == Card.CardType.ELEVEN)
        {
            Pawn OppP  = null;
            Pawn moveP  = null;
            boolean swap = false;
            int distancetraveled = 0;
            for(Pawn p : getPawns()){
                if (p.getDistanceTraveled() > distancetraveled && p.getTile().getType() != Tile.TType.START && controller.isValidMove(p,11))
                {
                    distancetraveled = p.getDistanceTraveled();
                    moveP = p;
                }
            }
        
            int oppDistancetraveled = 0;
            for(Player pla : controller.getPlayers())   //have to add check that checks the distance of the opponents pawn from the originial pawns start place instead of the opponents start place.
            {
                for (Pawn p : pla.getPawns()){
                    if (p.getTile().getType() != Tile.TType.START && p.getColor() != color && p.getDistanceTraveled() > oppDistancetraveled && p.getDistanceTraveled() > distancetraveled + 11)
                    {
                        oppDistancetraveled = p.getDistanceTraveled();
                        OppP = p;
                        swap = true;
                    }
                }
            }
            if (moveP != null && OppP != null && swap == true) 
            {
                controller.swapPawns(moveP, OppP);
            }
            else if (OppP == null && moveP != null)
            {
                controller.movePawn(moveP, 11);
            }
            //if the switch would give more than eleven spaces forward use it else move 11 spaces forward. if cant move 11 spaces
            //and switch is bad skip turn
        }
        else if (c.getType() == Card.CardType.TWELVE)
        {
            Pawn moveP  = null;
            int distancetraveled = 0;
            for(Pawn p : getPawns()){
                if (p.getTile().getType() != Tile.TType.START && p.getDistanceTraveled() > distancetraveled && controller.isValidMove(p,12))
                {
                    distancetraveled = p.getDistanceTraveled();
                    moveP = p;
                }
            }
            if (moveP != null)
                controller.movePawn(moveP, 12);
        }
        else if(c.getType() == Card.CardType.SORRY)
        {   
            Pawn OppP  = null;
            int distancetraveled = 0;
            for(Player pla : controller.getPlayers())   
            {
                for (Pawn p : pla.getPawns()){
                    if (p.getColor() != color && p.getDistanceTraveled() > distancetraveled)
                    {
                        distancetraveled = p.getDistanceTraveled();
                        OppP = p;
                    }
                }
            }
            Pawn moveP = null;
            for(Pawn p : getPawns()){
                if(p.getTile().getType() == Tile.TType.START)
                {
                    moveP = p;
                    break;
                
                }
            }

            if (moveP != null && OppP != null)
            {
                controller.swapPawns(moveP, OppP);
            }
          //  add function for swaping
            // controller.movePawn(OppP, moveP);


        }
    }
}