package gameengo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    char[] picName = {'A','B','C','D','E'};
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
    
    ArrayList<Rectangle> cardRects;
    @FXML
    private Rectangle card8;
    private Rectangle nowCard;
    @FXML
    private Rectangle nowCardRect;
    
    public enum Value {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine,
        PlusTwo, Reverse, Skip, Wild, WildFour;

    }
    
    UnoCard nowCardPlay;
            
    Image[] imageCardInit = new Image[7];
    public void addTexture() {

        for (char init = 'A'; init <= 'E'; init++) {
            int n = 13;
            //int numPic = 0;
            //int count = 0;
            if (init == 'E') {
                n = 4;
            }
            for (int i = 1; i < n; i++) {
                imagecards[i - 1] = new Image("/pic/" + i + init + ".png");
                //cards[count].setFill(new ImagePattern(imagecards[i]));
                //count++;
            }
        }

    }

    public void setRectangleImage() {

    }
//    public int[] ran() {
//        
//        for (int i = 1; i <= 108; i++) {
//            ran2 = r.nextInt(Maxnum);
//            temp[i - 1] = card[ran2];
//            Maxnum--;
//            for (int j = ran2; j < Maxnum; j++) {
//                card[j] = card[j + 1];
//            }
//            int[] numcop = new int[Maxnum];
//            System.arraycopy(card, 0, numcop, 0, Maxnum);
//            card = new int[Maxnum];
//            System.arraycopy(numcop, 0, card, 0, Maxnum);
//        }
//        return temp;
//    }

//    private void setTextureToDeck(UnoDeck deck){
//        this.SetTexture();
//        for()
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //nowCardRect.setFill(new ImagePattern(imageBlue1));
        this.addTexture();
        game = new Game(playerName);
        //game.start(game);

//        for (int i = 0; i < game.getPlayerHandSize(playerName[0]); i--) {
        int i = 0;
        int k = 0;
        for (UnoCard card : game.getPlayerHand(playerName[0])) {
            //System.out.println("cardDeck[i]"+ game.getPlayerHand(playerName[0]).get(i).toString());
            for(UnoCard.Color card3 : game.getPlayerHand(playerName[0]).get(i).colors){
                if (game.getPlayerHand(playerName[0]).get(i).getColor().equals(card.getColor(k++))) {
                int j = 0;
                char collectAlphabet = picName[k-1];
//                System.out.println("HI! color");
//                System.out.println(game.getPlayerHand(playerName[0]).get(i).toString());
                for (UnoCard.Value card2 : game.getPlayerHand(playerName[0]).get(i).values) {

                    if (game.getPlayerHand(playerName[0]).get(i).getValue().equals(card.getValue(j++))) {
//                        System.out.println(game.getPlayerHand(playerName[0]).get(i).toString());
//                        System.out.println("HI! value");
                                System.out.println("/pic/"+card.getValueToInt(j-1)+collectAlphabet+".png");
                                 imageCardInit[i] = new Image("/pic/"+card.getValueToInt(j-1)+collectAlphabet+".png");
//                                 imageCardInit[i] = new Image("/pic/2D"+".png");
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
        for(UnoCard.Color color : nowCardPlay.colors){
            
                if(nowCardPlay.getColor().equals(nowCardPlay.colors[temp])){
                    nowCardRect.setFill(new ImagePattern(deck.drawCardImage(nowCardPlay,picName[temp])));
                }
            temp++;
        }
        
        
    }

    @FXML
    private void drawMethod(MouseEvent event) {
        r1 = rand.nextInt(2) + 1;
        nub++;
        UnoCard CardDraw;

        CardDraw = game.getDeck().drawCard();
        System.out.println("location: "+CardDraw.toString());
            int count = 0;
            for(UnoCard.Color color : CardDraw.colors){
                if(CardDraw.getColor().equals(CardDraw.colors[count])){
                    Image[] image = null;
                    card8.setFill(new ImagePattern(deck.drawCardImage(CardDraw,picName[count])));
                }
            count++;
        }
        
            
            
//        Image imageRand = new Image("/pic/" + randDeck[0] + ".png");
//        if (nub == 1) {
//            initCards[6].setFill(new ImagePattern(imageRand));
//            deck.drawCard();
//        }
//        if (nub == 2) {
//            initCards[7].setFill(new ImagePattern(imageRand));
//
//        }
//        if (nub == 3) {
//            initCards[8].setFill(new ImagePattern(imageRand));
//            deck.drawCard();
//        }
//        if (nub == 4) {
//            initCards[9].setFill(new ImagePattern(imageRand));
//            deck.drawCard();
//        }

    }

    @FXML
    private void clickCard1(MouseEvent event) {
       nowCardRect.setFill(getTopCardPic(card1));
        card1.setVisible(false);

    }

    @FXML
    private void clickCard2(MouseEvent event) {
       nowCardRect.setFill(getTopCardPic(card2));
        card2.setVisible(false);
    }

    public ImagePattern getTopCardPic(Rectangle topCard) { 
        return (ImagePattern) topCard.getFill();
    }

    private void addImageToCard(Image[] image){
        card1.setFill(new ImagePattern(image[0]));
        card2.setFill(new ImagePattern(image[1]));
        card3.setFill(new ImagePattern(image[2]));
        card4.setFill(new ImagePattern(image[3]));
        card5.setFill(new ImagePattern(image[4]));
        card6.setFill(new ImagePattern(image[5]));
        card7.setFill(new ImagePattern(image[6]));
    }
    
}
