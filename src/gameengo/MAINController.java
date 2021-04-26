package gameengo;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MAINController implements Initializable {

    @FXML
    private Rectangle playCard;
    @FXML
    private Button drawBtn;
    @FXML
    private Rectangle card1, card2, card3, card4, card5, card6, card7, card8, card9;

    Image imageBlue1 = new Image("/pic/1.png");
    Image imageBlue2 = new Image("/pic/2.png");
    Random rand = new Random();
    int r1, nub = 0;
    UnoDeck deck = new UnoDeck();
    int[] randDeck = new int[10];

    private Image imagecards;
    private int[] card = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final String[] nameImages = new String[10];
    private final int[] temp = new int[10];
    private int Maxnum = 10;
    private final Random r = new Random();
    private int ran2;

    public int[] ran() {
        for (int i = 1; i <= 10; i++) {
            ran2 = r.nextInt(Maxnum);
            temp[i - 1] = card[ran2];
            Maxnum--;
            for (int j = ran2; j < Maxnum; j++) {
                card[j] = card[j + 1];
            }
            int[] numcop = new int[Maxnum];
            System.arraycopy(card, 0, numcop, 0, Maxnum);
            card = new int[Maxnum];
            System.arraycopy(numcop, 0, card, 0, Maxnum);
        }
        return temp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //playCard.setFill(new ImagePattern(imageBlue1));
        randDeck = ran();
        Image imageRand = new Image("/pic/" + randDeck[0] + ".png");
        Image imageRand1 = new Image("/pic/" + randDeck[1] + ".png");
        Image imageRand2 = new Image("/pic/" + randDeck[2] + ".png");
        Image imageRand3 = new Image("/pic/" + randDeck[3] + ".png");
        Image imageRand4 = new Image("/pic/" + randDeck[4] + ".png");
        Image imageRand5 = new Image("/pic/" + randDeck[5] + ".png");
        card1.setFill(new ImagePattern(imageRand));
        card2.setFill(new ImagePattern(imageRand1));
        card3.setFill(new ImagePattern(imageRand2));
        card4.setFill(new ImagePattern(imageRand3));
        card5.setFill(new ImagePattern(imageRand4));
        playCard.setFill(new ImagePattern(imageRand5));

    }

    @FXML
    private void drawMethod(MouseEvent event) {
        r1 = rand.nextInt(2) + 1;
        nub++;
        Image imageRand = new Image("/pic/" + randDeck[0] + ".png");
        if (nub == 1) {
            card6.setFill(new ImagePattern(imageRand));
            deck.drawCard();
        }
        if (nub == 2) {
            card7.setFill(new ImagePattern(imageRand));
            deck.drawCard();
        }
        if (nub == 3) {
            card8.setFill(new ImagePattern(imageRand));
            deck.drawCard();
        }
        if (nub == 4) {
            card9.setFill(new ImagePattern(imageRand));
            deck.drawCard();
        }
        
    }

    @FXML
    private void clickCard1(MouseEvent event) {
        playCard.setFill(getTopCardPic(card1));
        card1.setVisible(false);
        
    }

    @FXML
    private void clickCard2(MouseEvent event) {
        playCard.setFill(getTopCardPic(card2));
        card2.setVisible(false);
        
    }
    
    public ImagePattern getTopCardPic(Rectangle topCard) {
        return (ImagePattern) topCard.getFill();
    }
    
    private void clickCard(Rectangle rect){
        
    }
}
