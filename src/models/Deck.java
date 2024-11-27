import Card;
import java.util.ArrayList;
import java.util.Collectionse

public class Deck{
    private ArrayList<Card> cards;
    private int i;

    public Deck(){
        i = 0;

    }

    public Card drawCard(){
        if(i == cards.size()-1)
            reshuffle();

        return cards.get(i++);
    }

    private reshuffle(){
        Collections.shuffle(cards);
        i = 0;
    }
}