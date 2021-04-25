/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengo;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.ImageView;

/**
 *
 * @author USER
 */
public class UnoDeck {

    private UnoCard[] cards;
    private int cardInDeck;
    Game game;
    
    //create deck
    public UnoDeck() {
        cards = new UnoCard[108];
    }
    
    public void reset() {
        //UnoCard.Color[] colors = UnoCard.Color.values();
        UnoCard initCard = new UnoCard();
        cardInDeck = 0; // initialize with no cards in deck
        
        for (int i = 0; i < initCard.colors.length - 1; i++) {
            UnoCard.Color color = initCard.colors[i];
            cards[cardInDeck++] = new UnoCard(color,initCard.getValue(i));
            for (int j = 1; j < 10; j++) {
                cards[cardInDeck++] = new UnoCard(color, initCard.getValue(i));
                cards[cardInDeck++] = new UnoCard(color, initCard.getValue(i));
            }
            UnoCard.Value[] values = new UnoCard.Value[] { UnoCard.Value.PlusTwo, UnoCard.Value.Reverse,
                    UnoCard.Value.Skip };
            for (UnoCard.Value value : values) {
                cards[cardInDeck++] = new UnoCard(color, value);
                cards[cardInDeck++] = new UnoCard(color, value);
            }
        }
        UnoCard.Value[] values = new UnoCard.Value[] { UnoCard.Value.Wild, UnoCard.Value.WildFour };
        for (UnoCard.Value value : values) {
            for (int i = 0; i < 4; i++) {
                cards[cardInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
            }
        }
    }

    // public void replaceDeck(ArrayList<UnoCard> cards) {
    //     this.cards = cards.toArray(new UnoCard(cards.size())); // ???
    // }

    public void replaceDeck(ArrayList<UnoCard> cards) {
        this.cards = cards.toArray(new UnoCard[cards.size()]);
        this.cardInDeck = this.cards.length;
    }

    public boolean isEmpty() {
        return cardInDeck == 0;
    }

    public void shuffle() {  //checked
        int n = cards.length;
        Random random = new Random();
        for (int i = 0; i < cards.length; i++) {
            int randomValue = i + random.nextInt(n - i);
            UnoCard randomCard = cards[randomValue];
            cards[randomValue] = cards[i];
            cards[i] = randomCard;
        }
    }

    // cards empty -> should edit it to play until can find some winner
    public UnoCard drawCard() throws IllegalArgumentException {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cann't draw a card since there are no cards in the deck");
        }
        return cards[--cardInDeck]; // minus card from deck
    }

    public ImageView drawCardImage() throws IllegalArgumentException {
        if (isEmpty()) {
            throw new IllegalArgumentException("Can't draw a card since the deck is empty");
        }
        return new ImageView(cards[--cardInDeck].toString() + ".png"); // get picture from deck
    }
    
    public UnoCard[] drawCard(int n){
        if(n<0){
            throw new IllegalArgumentException("Please draw positive cards but tried to draw "+ n +" cards.");
        } 
        if(n>cardInDeck){
            throw new IllegalArgumentException("Can't draw"+n+" cards since there are only "+cardInDeck+" cards");
        }
        UnoCard[] num = new UnoCard[n];
        for (int i = 0; i < n; i++) {
            num[i] = cards[--cardInDeck];
        }
        return num;
    }
}
