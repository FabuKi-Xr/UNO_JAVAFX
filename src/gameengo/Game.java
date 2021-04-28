/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengo;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author USER
 */
public class Game {

    private int currentPlayer;
    private String[] playerID;

    private UnoDeck deck;

    public UnoDeck getDeck() {
        return deck;
    }
    private ArrayList<ArrayList<UnoCard>> playerHand;
    private ArrayList<UnoCard> stockPile;

    private UnoCard.Color nowColor;
    private UnoCard.Value nowValue;
    private UnoCard topCard;
    private boolean gameDirection;
    char[] picName = {'A', 'B', 'C', 'D', 'E'};

    public Game(String[] player_ID) { // initialgame
        deck = new UnoDeck(); // creat 108 cards 
        deck.reset();
        deck.shuffle();
        stockPile = new ArrayList<UnoCard>();

        playerID = player_ID;
        currentPlayer = 0;
        gameDirection = false;

        playerHand = new ArrayList<ArrayList<UnoCard>>();

        for (int i = 0; i < player_ID.length; i++) {
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(deck.drawCard(7)));
            playerHand.add(hand);
        }
    }

    public void start(Game game) {
        UnoCard card = deck.drawCard();
        nowColor = card.getColor();
        nowValue = card.getValue();
        
        if (card.getValue() == UnoCard.Value.Wild) {
            start(game);
        }
        if (card.getValue() == UnoCard.Value.WildFour || card.getValue() == UnoCard.Value.PlusTwo) {
            start(game);
        }

        if (card.getValue() == UnoCard.Value.Skip) { // skip card was used.
//            ImageView skipPic = new ImageView("/pic/prohibited.png");
//            skipPic.setX(150);
//            skipPic.setY(300);
//            skipPic.setFitWidth(300);
//            skipPic.setFitHeight(300);

//            Label msgSkip = new Label(playerID[currentPlayer] + " was skipped!");
//            msgSkip.setFont(new Font("Tahoma", Font.BOLD, 48));
//            JOptionPane.showMessageDialog(null, msgSkip);
            if (gameDirection == false) {
                currentPlayer = (currentPlayer + 1) % playerID.length;
            } else if (gameDirection == true) {
                currentPlayer = (currentPlayer - 1) % playerID.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerID.length - 1;
                }
            }

        }

        if (card.getValue() == UnoCard.Value.Reverse) {

//            ImageView skipPic = new ImageView("/pic/reverse.png");
//            skipPic.setX(150);
//            skipPic.setY(300);
//            skipPic.setFitWidth(300);
//            skipPic.setFitHeight(300);
//            JLabel msgRe = new JLabel(playerID[currentPlayer] + "The game direction changed!");
//            msgRe.setFont(new Font("Tahoma", Font.BOLD, 48));
//            JOptionPane.showMessageDialog(null, msgRe);
            gameDirection ^= true;
            currentPlayer = playerID.length - 1;
        }
        stockPile.add(card);
    }

    public UnoCard getTopCard() {
        return new UnoCard(nowColor, nowValue);
    }

    public boolean isGameOver() {
        for (String player : this.playerID) {
            if (hasEmptyHand(player)) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentPlayer() {
        return this.playerID[this.currentPlayer];
    }

//    public String getPreviousPlayer(int i) {
//        int index = this.currentPlayer - i;
//        if (index == -1) {
//            index = playerID.length - i;
//        }
//        return this.playerID[index];
//    }
    public String[] getPlayerID() {
        return playerID;
    }

    public ArrayList<UnoCard> getPlayerHand(String pid) {
        int index = Arrays.asList(playerID).indexOf(pid);
        return playerHand.get(index);
    }

    public int getPlayerHandSize(String player) {
        return getPlayerHand(player).size();
    }

    public UnoCard getPlayerCard(String player, int choice) {
        ArrayList<UnoCard> hand = getPlayerHand(player);
        return hand.get(choice);
    }

    public boolean hasEmptyHand(String pid) {
        return getPlayerHand(pid).isEmpty();
    }

    public boolean nowCardPlay(UnoCard card) {
        return card.getColor() == nowColor || card.getValue() == nowValue;
    }

    // part 5
    /*public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException {
        if (this.playerID[this.currentPlayer] != pid) {
            throw new InvalidPlayerTurnException("It isn't " + pid + " 's turn", pid);
        }
    }*/

    public void submitDraw(String playerName,ArrayList<Rectangle> rect,UnoCard drawCard,HBox hbox){
        //checkPlayerTurn(pid);
        
        if (deck.isEmpty()) {
            deck.replaceDeck(stockPile);
            deck.shuffle();
        }
        
        Rectangle newCardRect = new Rectangle();
        
        rect.add(newCardRect);
        getPlayerHand(playerName).add(deck.drawCard());
        
        
        newCardRect.setFill(Color.AQUA);
        newCardRect.setWidth(119.0f);
        newCardRect.setHeight(159.0f);
        
        int count = 0;

            for (UnoCard.Color color : drawCard.colors) {
                if (drawCard.getColor().equals(drawCard.colors[count])) {
                        System.out.println("/pic/" + drawCard.getValueToInt() + picName[count]+ ".png");
                        newCardRect.setFill(new ImagePattern(deck.drawCardImage(drawCard,picName[count])));
                    }
                count++;
            }
            
        hbox.getChildren().add(newCardRect);
//        hbox.setma
//        newCardRect.setStyle("-fx-margin-left: -50;");
        
        if (gameDirection == false) {
            currentPlayer = (currentPlayer + 1) % playerID.length;
        } else if (gameDirection == true) {
            currentPlayer = (currentPlayer - 1) % playerID.length;
            if (currentPlayer == -1) {
                currentPlayer = playerID.length - 1;
            }
        }
    }

    public void setCardColor(UnoCard.Color color) {
        nowColor = color;
    }

    /*public void submitPlayerCard(String pid, UnoCard nowCard, UnoCard playerCard,Rectangle cardRect,Rectangle nowCardRect,HBox playerBox)
            throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException {
        
        nowCardRect.setFill(getTopCardPic(cardRect));
        playerBox.getChildren().remove(cardRect);
        playerBox.setAlignment(Pos.CENTER);
        checkPlayerTurn(pid);
        
        ArrayList<UnoCard> pHand = getPlayerHand(pid);
        nowColor = UnoCard.Color.Yellow;
        if (!nowCardPlay(card)) {now
            if (card.getColor() == UnoCard.Color.Wild) {
                nowColor = card.getColor();
                nowValue = card.getValue();
            }
            if (card.getColor() != nowColor) {
                // get sound warning + card shake

//                JLabel msgWarnColor = new JLabel(
//                        "Invalid player move, expected color: " + nowColor + " but got color " + card.getColor());
//                msgWarnColor.setFont(new Font("Tahoma", Font.BOLD, 48));
//                JOptionPane.showMessageDialog(null, msgWarnColor);
//                // throw new InvalidColorSubmissionException(msgWarnColor, actual, expected);
                throw new InvalidColorSubmissionException(
                        "Invalid player move, expected color: " + nowColor + " but got color " + card.getColor(),
                        card.getColor(), nowColor); // delete later

            } else if (card.getValue() != nowValue) {
                // get sound warning + card shake

//                JLabel msgWarnValue = new JLabel(
//                        "Invalid player move, expected value: " + nowValue + " but got value " + card.getValue());
//                msgWarnValue.setFont(new Font("Tahoma", Font.BOLD, 48));
//                JOptionPane.showMessageDialog(null, msgWarnValue);
                throw new InvalidValueSubmissionException(
                        "Invalid player move, expected value: " + nowValue + " but got value " + card.getValue(),
                        card.getValue(), nowValue);
            }
        }
        pHand.remove(card);

        if (hasEmptyHand(this.playerID[currentPlayer])) {

//            Label msgWon = new JLabel(this.playerID[currentPlayer] + " won the game! Thank you for playing!");
//            msgWon.setFont(new Font("Tahoma", Font.BOLD, 48));
//            JOptionPane.showMessageDialog(null, msgWon);
//            System.exit(0);
        }
        nowColor = card.getColor();
        nowValue = card.getValue();
        stockPile.add(card);

        if (gameDirection == false) {
            currentPlayer = (currentPlayer + 1) % playerID.length;
        } else if (gameDirection == true) {
            currentPlayer = (currentPlayer - 1) % playerID.length;
            if (currentPlayer == -1) {
                currentPlayer = playerID.length - 1;
            }
        }
        if (card.getColor() == UnoCard.Color.Wild) {
            nowColor = gameColor;
        }
        if (card.getValue() == UnoCard.Value.PlusTwo) {
            pid = playerID[currentPlayer];
            for (int i = 0; i < 2; i++) {
                getPlayerHand(pid).add(deck.drawCard());
            }
//            JLabel msgPlusTwo = new JLabel(pid + " draw 2 cards!");
        }

        if (card.getValue() == UnoCard.Value.WildFour) {
            pid = playerID[currentPlayer];
            for (int i = 0; i < 4; i++) {
                getPlayerHand(pid).add(deck.drawCard());
            }
//            JLabel msgPlusTwo = new JLabel(pid + " draw 4 cards!");
        }

        if (card.getValue() == UnoCard.Value.Skip) {
//            JLabel msgSkip = new JLabel(playerID[currentPlayer] + " was Skipped!");
//            msgSkip.setFont(new Font("Tahoma", Font.BOLD, 48));
//            JOptionPane.showMessageDialog(null, msgSkip);
            
            if (gameDirection == false) {
                currentPlayer = (currentPlayer + 1) % playerID.length;
            } else if (gameDirection == true) {
                currentPlayer = (currentPlayer - 1) % playerID.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerID.length - 1;
                }
            }
        }

        if (card.getValue() == UnoCard.Value.Reverse) {
//            JLabel msgRe = new JLabel(pid + " changed the game direction!");
//            msgRe.setFont(new Font("Tahoma", Font.BOLD, 48));
//            JOptionPane.showMessageDialog(null, msgRe);

            gameDirection ^= true;
            if (gameDirection == true) {
                currentPlayer = (currentPlayer - 2) % playerID.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerID.length - 1;
                }

                if (currentPlayer == -2) {
                    currentPlayer = playerID.length - 2;
                }
            } else if (gameDirection == false) {
                currentPlayer = (currentPlayer + 2) % playerID.length;
            }
        }
    }*/

    class InvalidPlayerTurnException extends Exception {

        String playerID;
        Label warningMsg;
        public InvalidPlayerTurnException(String msg, String pid) {
            super(msg);
            playerID = pid;
        }

        public String getPlayerID() {
            return playerID;
        }

        public void setPlayerID(String playerID) {
            this.playerID = playerID;
        }

    }

    class InvalidColorSubmissionException extends Exception {

        private UnoCard.Color expected;
        private UnoCard.Color actual;

        public InvalidColorSubmissionException(String msg, UnoCard.Color actual, UnoCard.Color expected) {
            this.actual = actual;
            this.expected = expected;
        }
    }

    class InvalidValueSubmissionException extends Exception {

        private UnoCard.Value expected;
        private UnoCard.Value actual;

        public InvalidValueSubmissionException(String msg, UnoCard.Value actual, UnoCard.Value expected) {
            this.actual = actual;
            this.expected = expected;
        }

    }
    public boolean getGameDirection() {
            return this.gameDirection;
        }
    
    public void setTopCard(UnoCard topCard) {
        
    }
public ImagePattern getTopCardPic(Rectangle topCard) { 
        return (ImagePattern) topCard.getFill();
    }
}
