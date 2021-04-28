package gameengo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MAINController implements Initializable {

    //private RectanglenowCardRect;
    @FXML
    private Button drawBtn;

    Random rand = new Random();

    int r1, nub = 0;

    int[] randDeck = new int[108];

    private Image[] imagecards = new Image[108];

//    private int[] card = new int[108];
    private final String[] nameImages = new String[108];
//    private final int[] temp = new int[108];
//    private int Maxnum = 108;
//    private final Random r = new Random();
//    private int ran2;

    private Image[] imageRand;

    private String[] playerName = {"p1"};
    private Game game;
    @FXML
    private ImageView StopPic;

    private UnoDeck deck = new UnoDeck();

    char[] picName = {'A', 'B', 'C', 'D', 'E'};
    
    @FXML
    private Rectangle card7;
    @FXML
    private Rectangle card6;
    @FXML
    private Rectangle card5;
    @FXML
    private Rectangle card4;
    @FXML
    private Rectangle card3;
    @FXML
    private Rectangle card2;
    @FXML
    private Rectangle card1;
    @FXML
    private Rectangle nowCardRect;

    Rectangle[] startHand = {card1, card2, card3, card4, card5, card6, card7};
    ArrayList<Rectangle> playerHand = new ArrayList<Rectangle>(Arrays.asList(startHand));

    //ArrayList<Rectangle> cardRects;
    @FXML
    private HBox playerBox;

    public enum Value {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine,
        PlusTwo, Reverse, Skip, Wild, WildFour;

    }

    UnoCard nowCardPlay;

    Image[] imageCardInit = new Image[7];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //nowCardRect.setFill(new ImagePattern(imageBlue1));
        //this.addTexture();
        game = new Game(playerName);
        //game.start(game);

//        for (int i = 0; i < game.getPlayerHandSize(playerName[0]); i--) {
        int i = 0;
        int k = 0;
        for (UnoCard card : game.getPlayerHand(playerName[0])) {
            //System.out.println("cardDeck[i]"+ game.getPlayerHand(playerName[0]).get(i).toString());
            for (UnoCard.Color card3 : game.getPlayerHand(playerName[0]).get(i).colors) {
                if (game.getPlayerHand(playerName[0]).get(i).getColor().equals(card.getColor(k++))) {
                    int j = 0;
                    char collectAlphabet = picName[k - 1];
//                System.out.println("HI! color");
//                System.out.println(game.getPlayerHand(playerName[0]).get(i).toString());
                    for (UnoCard.Value card2 : game.getPlayerHand(playerName[0]).get(i).values) {

                        if (game.getPlayerHand(playerName[0]).get(i).getValue().equals(card.getValue(j++))) {
//                        System.out.println(game.getPlayerHand(playerName[0]).get(i).toString());
//                        System.out.println("HI! value");
                            System.out.println("/pic/" + card.getValueToInt(j - 1) + collectAlphabet + ".png");
                            imageCardInit[i] = new Image("/pic/" + card.getValueToInt(j - 1) + collectAlphabet + ".png");
                        }
                    }
                }
            }

            k = 0;
            i++;
        }
        addImageToCard(imageCardInit);
        nowCardPlay = game.getDeck().drawCard();
        int temp = 0;
        for (UnoCard.Color color : nowCardPlay.colors) {

            if (nowCardPlay.getColor().equals(nowCardPlay.colors[temp])) {
                nowCardRect.setFill(new ImagePattern(deck.drawCardImage(nowCardPlay, picName[temp])));
            }
            temp++;
        }
        //System.out.println("card8: " + card8.getFill().isOpaque());
    }

    @FXML
    private void drawMethod(MouseEvent event) {
        r1 = rand.nextInt(2) + 1;

        UnoCard CardDraw;
       // System.out.println("card8: " + card8.getFill().isOpaque());
        boolean isFilledImage = false;
            CardDraw = game.getDeck().drawCard();
            
            game.submitDraw(playerName[0], playerHand,CardDraw, playerBox);
            

        //playerBox.getChildren().add(card7);
    }

    @FXML
    private void clickCard1(MouseEvent event) {
        nowCardRect.setFill(game.getTopCardPic(card1));
        playerBox.getChildren().remove(card1);
        playerBox.setAlignment(Pos.CENTER);
        //game.submitPlayerCard(playerName[0], game.getPlayerHand(playerName[0]).get(0), UnoCard.Color.Yellow, nowCardRect, nowCardRect, playerBox);
    }

    @FXML
    private void clickCard2(MouseEvent event) {
        nowCardRect.setFill(game.getTopCardPic(card2));
        playerBox.getChildren().remove(card2);
        playerBox.setAlignment(Pos.CENTER);
    }

    @FXML
    private void clickCard3(MouseEvent event) {
        nowCardRect.setFill(game.getTopCardPic(card3));
        playerBox.getChildren().remove(card3);
        playerBox.setAlignment(Pos.CENTER);
    }

    private void addImageToCard(Image[] image) {
        card1.setFill(new ImagePattern(image[0]));
        card2.setFill(new ImagePattern(image[1]));
        card3.setFill(new ImagePattern(image[2]));
        card4.setFill(new ImagePattern(image[3]));
        card5.setFill(new ImagePattern(image[4]));
        card6.setFill(new ImagePattern(image[5]));
        card7.setFill(new ImagePattern(image[6]));
    }

}
