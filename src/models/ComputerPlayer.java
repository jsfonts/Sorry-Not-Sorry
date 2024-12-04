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

        }
        else if(c.getType() == Card.CardType.TWO)
        {

        }
        else if(c.getType() == Card.CardType.THREE)
        {

        }
        else if(c.getType() == Card.CardType.FOUR)
        {

        }
        else if(c.getType() == Card.CardType.FIVE)
        {

        }
        else if (c.getType() == Card.CardType.SEVEN)
        {

        }
        else if(c.getType() == Card.CardType.EIGHT)
        {

        }
        else if (c.getType() == Card.CardType.TEN)
        {

        }
        else if (c.getType() == Card.CardType.ELEVEN)
        {

        }
        else if (c.getType() == Card.CardType.TWELVE)
        {

        }
        else if(c.getType() == Card.CardType.SORRY)
        {
            
        }
    }
}