/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengo;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author USER
 */
public class UnoDeck {

    private UnoCard[] cards;
    private int cardInDeck;
    private String[] playerID;
    Game game;
    
    //create deck
    public UnoDeck() {
        cards = new UnoCard[108];
        cardInDeck = cards.length;
    }
    
    public void reset() {
        //UnoCard.Color[] colors = UnoCard.Color.values();
        UnoCard initCard = new UnoCard();
        cardInDeck = 0; // initialize with no cards in deck
        int count=0;
        for (int i = 0; i < initCard.colors.length - 1; i++) {
            UnoCard.Color color = initCard.colors[i];
            cards[cardInDeck++] = new UnoCard(color,initCard.getValue(0));
           // System.out.println("card : "+cards[count++].toString());
            for (int j = 1; j < 10; j++) {
                cards[cardInDeck++] = new UnoCard(color, initCard.getValue(j));
                //System.out.println("card : "+cards[count++].toString());
                cards[cardInDeck++] = new UnoCard(color, initCard.getValue(j));
                //System.out.println("card : "+cards[count++].toString());
            }
            UnoCard.Value[] values = new UnoCard.Value[] { UnoCard.Value.PlusTwo, UnoCard.Value.Reverse,
                    UnoCard.Value.Skip };
            for (UnoCard.Value value : values) {
                
                cards[cardInDeck++] = new UnoCard(color, value);
               // System.out.println("card : "+cards[count++].toString());
                cards[cardInDeck++] = new UnoCard(color, value);
               // System.out.println("card : "+cards[count++].toString());
            }
        }
        UnoCard.Value[] values = new UnoCard.Value[] { UnoCard.Value.Wild, UnoCard.Value.WildFour };
        for (UnoCard.Value value : values) {
            for (int i = 0; i < 4; i++) {
                cards[cardInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
               // System.out.println("card : "+cards[count++].toString()+ "card : "+ i);
            }
        }
        //System.out.println("Count : " + count);
    }

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
        //System.out.println("cardInDeck : "+getCardInDeck());
        if (isEmpty()) {
            throw new IllegalArgumentException("Cann't draw a card since there are no cards in the deck");
        }
        return cards[--cardInDeck]; // minus card from deck
    }

    public Image drawCardImage(UnoCard value , char alphabet) throws IllegalArgumentException {
        if (isEmpty()) {
            throw new IllegalArgumentException("Can't draw a card since the deck is empty");
        }
        return new Image("/pic/" + value.getValueToInt() + alphabet + ".png"); // get picture from deck
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
    
    public int getCardInDeck(){
        return cardInDeck;
    }
    
    public UnoCard getCardRandom(int i){
        return cards[i];
    }
}
