package models;

public class Card{
    public enum CardType{
        ONE{},
        TWO{},
        THREE{},
        FOUR{},
        FIVE{},
        SEVEN{},
        EIGHT{},
        TEN{},
        ELEVEN{},
        TWELVE{},
        SORRY{};
    }

    boolean isUsed;

    private CardType type;

    public Card(CardType ct){
        type = ct;
        isUsed = false;
    }

    public void useCard(){
        isUsed = true;
        //this.play(); this is the function to overload for the CardTypes
    }

    private CardType type(){
        return type;
    }

    public String toString(){
        return this.type.toString();
    }

    public String getInstructions() {
        switch (type) {
            case ONE:
                return "Move a pawn from Start or move a pawn one space forward.";
            case TWO:
                return "Move a pawn from Start or move a pawn two spaces forward. Draw Again";
            case THREE:
                return "Move a pawn three spaces forward.";
            case FOUR:
                return "Move a pawn four spaces backward.";
            case FIVE:
                return "Move a pawn five spaces forward.";
            case SEVEN:
                return "Move a pawn seven spaces forward, or split the seven spaces between two pawns (e.g., four for one pawn, three for another).";
            case EIGHT:
                return "Move a pawn eight spaces forward.";
            case TEN:
                return "Move a pawn ten spaces forward or one space backward. If a pawn cannot move 10 forward, it must move back one space.";
            case ELEVEN:
                return "Move eleven spaces forward, or switch the places of one of your pawns with an opponent’s pawn (not in Safety Zone).";
            case TWELVE:
                return "Move a pawn twelve spaces forward.";
            case SORRY:
                return "Move a pawn from Start to a square occupied by an opponent’s pawn, sending that pawn back to Start. (Can’t be used on a pawn in a Safety Zone or at Home base.)";
            default:
                return "No instructions available.";
        }
    }
}