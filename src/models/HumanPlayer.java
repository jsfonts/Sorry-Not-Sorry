package models;

import models.Player;
import models.Board;
import models.Pawn;
import models.Card;

public class HumanPlayer extends Player{
    public HumanPlayer(String n){
        super(n);
    }

    public void move(Card card){
        switch(card.getType()){
            case Card.CardType.ONE:
            break;
            case Card.CardType.TWO:
            break;
            case Card.CardType.THREE:
            break;
            case Card.CardType.FOUR:
            break;
            case Card.CardType.FIVE:
            break;
            case Card.CardType.SEVEN:
            break;
            case Card.CardType.EIGHT:
            break;
            case Card.CardType.TEN:
            break;
            case Card.CardType.ELEVEN:
            break;
            case Card.CardType.TWELVE:
            break;
            case Card.CardType.SORRY:
            break;
        }
    }

    private boolean moveOne(){
        boolean valid = true;

        

        return valid;
    }
}