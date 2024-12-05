package models;
import java.io.Serializable;

public class Card implements Serializable{
    public enum CardType{
        ONE, TWO, THREE, FOUR, FIVE, SEVEN, EIGHT, TEN, ELEVEN, TWELVE, SORRY;
    }

    boolean isUsed;

    private CardType type;

    public Card(CardType ct){
        type = ct;
        isUsed = false;
    }

    public void useCard(){
        isUsed = true;
    }

    public CardType getType(){
        return type;
    }

    public String toString(){
        return this.type.toString();
    }

    public String getImage() {
        switch (type) {
            case ONE:
                return "../resources/One.png";
            case TWO:
                return "../resources/Two.png";
            case THREE:
                return "../resources/Three.png";
            case FOUR:
                return "../resources/Four.png";
            case FIVE:
                return "../resources/Five.png";
            case SEVEN:
                return "../resources/Seven.png";
            case EIGHT:
                return "../resources/Eight.png";
            case TEN:
                return "../resources/Ten.png";
            case ELEVEN:
                return "../resources/Eleven.png";
            case TWELVE:
                return "../resources/Twelve.png";
            case SORRY:
                return "../resources/Sorry.png";
            default:
                return "No instructions available.";
        }
    }
}