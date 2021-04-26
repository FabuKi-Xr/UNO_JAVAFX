/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengo;

/**
 *
 * @author USER
 */
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import static javafx.scene.text.FontWeight.BOLD;

/**
 * game
 */
public class Game {

    private int currentPlayer;
    private String[] playerID;

    private UnoDeck deck;
    private ArrayList<ArrayList<UnoCard>> playerHand;
    private ArrayList<UnoCard> stockPile;

    private UnoCard.Color nowColor;
    private UnoCard.Value nowValue;

    boolean gameDirect;

    public Game(String[] pid) {
        deck = new UnoDeck();
        deck.shuffle();
        stockPile = new ArrayList<UnoCard>();

        playerID = pid;
        currentPlayer = 0;
        gameDirect = false;

        playerHand = new ArrayList<ArrayList<UnoCard>>();

        for (int i = 0; i < pid.length; i++) {
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
        if (card.getValue() == UnoCard.Value.Skip) {
            Label msgSkip = new Label(playerID[currentPlayer] + " was skipped!");
            msgSkip.setFont(Font.font("Tahoma", BOLD, 20));
            Alert.showMessageDialog(null, msgSkip);

            if (gameDirect == false) {
                currentPlayer = (currentPlayer + 1) % playerID.length;
            } else if (gameDirect == true) {
                currentPlayer = (currentPlayer - 1) % playerID.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerID.length - 1;
                }
            }
        }
        if (card.getValue() == UnoCard.Value.Reverse) {
            JLabel msgRe = new JLabel(playerID[currentPlayer] + "The game direction changed!");
            msgRe.setFont(new Font("Tahoma", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msgRe);
            gameDirect ^= true;
            currentPlayer = playerID.length - 1;
        }
        stockPile.add(card);
    }

    public UnoCard getTopCard() {
        return new UnoCard(nowColor, nowValue);
    }

    public ImageIcon getTopCardImage() {
        return new ImageIcon(nowColor + "-" + nowColor + ".png"); // FILE------------------------------
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

    public String getPrevPlayer(int i) {
        int index = this.currentPlayer - i;
        if (index == -1) {
            index = playerID.length - i;
        }
        return this.playerID[index];
    }

    public String[] getPlayer() {
        return playerID;
    }

    public ArrayList<UnoCard> getPlayerHand(String pid) {
        int index = Arrays.asList(playerID).indexOf(pid);
        return playerHand.get(index);
    }

    public int getPlayerHandSize(String pid) {
        return getPlayerHand(pid).size();
    }

    public UnoCard getPlayerCard(String pid, int choice) {
        ArrayList<UnoCard> hand = getPlayerHand(pid);
        return hand.get(choice);
    }

    public boolean hasEmptyHand(String pid) {
        return getPlayerHand(pid).isEmpty();
    }

    public boolean nowCardPlay(UnoCard card) {
        return card.getColor() == nowColor || card.getValue() == nowValue;
    }

    // part 5

    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException {
        if (this.playerID[this.currentPlayer] != pid) {
            throw new InvalidPlayerTurnException("It isn't " + pid + " 's turn", pid);
        }
    }

    public void submitDraw(String pid) throws InvalidPlayerTurnException {
        checkPlayerTurn(pid);

        if (deck.isEmpty()) {
            deck.replaceDeck(stockPile);
            deck.shuffle();
        }
        getPlayerHand(pid).add(deck.drawCard());
        if (gameDirect == false) {
            currentPlayer = (currentPlayer + 1) % playerID.length;
        } else if (gameDirect == true) {
            currentPlayer = (currentPlayer - 1) % playerID.length;
            if (currentPlayer == -1) {
                currentPlayer = playerID.length - 1;
            }
        }
    }

    public void setCardColor(UnoCard.Color color) {
        nowColor = color;
    }

    public void submitPlayerCard(String pid, UnoCard card, UnoCard.Color gameColor)
            throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException {
        checkPlayerTurn(pid);

        ArrayList<UnoCard> pHand = getPlayerHand(pid);

        if (!nowCardPlay(card)) {
            if (card.getColor() == UnoCard.Color.Wild) {
                nowColor = card.getColor();
                nowValue = card.getValue();
            }
            if (card.getColor() != nowColor) {
                JLabel msgWarnColor = new JLabel(
                        "Invalid player move, expected color: " + nowColor + " but got color " + card.getColor());
                msgWarnColor.setFont(new Font("Tahoma", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, msgWarnColor);
                // throw new InvalidColorSubmissionException(msgWarnColor, actual, expected);
                throw new InvalidColorSubmissionException(
                        "Invalid player move, expected color: " + nowColor + " but got color " + card.getColor(),
                        card.getColor(), nowColor);
            } else if (card.getValue() != nowValue) {
                JLabel msgWarnValue = new JLabel(
                        "Invalid player move, expected value: " + nowValue + " but got value " + card.getValue());
                msgWarnValue.setFont(new Font("Tahoma", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, msgWarnValue);
                throw new InvalidValueSubmissionException(
                        "Invalid player move, expected value: " + nowValue + " but got value " + card.getValue(),
                        card.getValue(), nowValue);
            }
        }
        pHand.remove(card);

        if (hasEmptyHand(this.playerID[currentPlayer])) {
            JLabel msgWon = new JLabel(this.playerID[currentPlayer] + " won the game! Thank you for playing!");
            msgWon.setFont(new Font("Tahoma", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msgWon);
            System.exit(0);
        }
        nowColor = card.getColor();
        nowValue = card.getValue();
        stockPile.add(card);

        if (gameDirect == false) {
            currentPlayer = (currentPlayer + 1) % playerID.length;
        } else if (gameDirect == true) {
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
            JLabel msgPlusTwo = new JLabel(pid + " draw 2 cards!");
        }

        if (card.getValue() == UnoCard.Value.WildFour) {
            pid = playerID[currentPlayer];
            for (int i = 0; i < 4; i++) {
                getPlayerHand(pid).add(deck.drawCard());
            }
            JLabel msgPlusTwo = new JLabel(pid + " draw 4 cards!");
        }

        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel msgSkip = new JLabel(playerID[currentPlayer] + " was Skipped!");
            msgSkip.setFont(new Font("Tahoma", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msgSkip);

            if (gameDirect == false) {
                currentPlayer = (currentPlayer + 1) % playerID.length;
            } else if (gameDirect == true) {
                currentPlayer = (currentPlayer - 1) % playerID.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerID.length - 1;
                }
            }
        }

        if (card.getValue() == UnoCard.Value.Reverse) {
            JLabel msgRe = new JLabel(pid + " changed the game direction!");
            msgRe.setFont(new Font("Tahoma", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msgRe);

            gameDirect ^= true;
            if (gameDirect == true) {
                currentPlayer = (currentPlayer - 2) % playerID.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerID.length - 1;
                }

                if (currentPlayer == -2) {
                    currentPlayer = playerID.length - 2;
                }
            } else if (gameDirect == false) {
                currentPlayer = (currentPlayer + 2) % playerID.length;
            }
        }
    }
}

class InvalidPlayerTurnException extends Exception {
    String playerID;

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
