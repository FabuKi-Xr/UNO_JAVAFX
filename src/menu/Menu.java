/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class Menu extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root,1080,720);
        
        stage.setTitle("ENGO!");
        
        //add pic icon
        Image icon = new Image("logos/logo.png");
        stage.getIcons().add(icon);
        
        //add pic menu
        Image logo = new Image("logos/logonobg.png");
        ImageView imageView = new ImageView(logo);
        /*imageView.setX(20);
        imageView.setY(-30);
        imageView.setScaleX(0.5);
        imageView.setScaleY(0.5);
        root.getChildren().add(imageView);*/
        
       
        stage.setScene(scene);
        
        stage.show();
        
    }
    
}
