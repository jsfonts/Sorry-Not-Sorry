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
        return type.toString();
    }
}