package models;

import models.Card;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;
    private int cardsUsed;

    public Deck(){      //45 cards
        cardsUsed = 0;
        cards = new ArrayList<Card>(45);

        //5 ones and 4 of every other card
        for(Card.CardType type : Card.CardType.values()){
            for(int i = 0; i < 4; i++)
                cards.add(cardsUsed++, new Card(type));
        }

        cards.add(cardsUsed++, new Card(Card.CardType.ONE));

        reshuffle();
    }

    public Card drawCard(){
        if(cardsUsed == cards.size()-1)
            reshuffle();

        return cards.get(cardsUsed++);
    }

    public void reshuffle(){
        Collections.shuffle(cards);
        cardsUsed = 0;
    }

    public void printAll(){
        for(Card card : cards)
            System.out.println(card);
    }
}
