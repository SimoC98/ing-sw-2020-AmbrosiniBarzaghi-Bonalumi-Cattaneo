package it.polimi.ingsw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * JavaFX App
 */
public class testfx extends Application {

    @Override
    public void start(Stage stage) {


        stage.setTitle("HelloDevoxx");
        GridPane root= new GridPane();
        Scene scene=new Scene(root, 400, 250,Color.ALICEBLUE);

        Text text1= new Text();
        Button b = new Button("press me");
        text1.setX(105);
        text1.setY(120);
        text1.setFont(new Font(30));
        text1.setText("prova row 1");

        Text text2 = new Text();
        text2.setX(105);
        text2.setY(120);
        text2.setFont(new Font(30));
        text2.setText("prova row 2");

        StackPane stackpane = new StackPane();
        stackpane.getChildren().add(new Button("ciao"));



        root.addRow(0,text1);
        root.addRow(1,text2);
        //root.addRow(2,b);
        root.addColumn(0,b);

        root.getChildren().add(stackpane);

        b.setOnMouseClicked((e)-> {
            System.out.print("clicked");

            int x = (int)e.getSceneX();
            int y = (int)e.getSceneY();

            System.out.print(x + y);

            text1.setText("presssssssss");
            stackpane.setVisible(false);
        });
        //root.getChildren().add(b);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}