package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.BoardRepresentation;
import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.UI;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.LoginRequestEvent;
import it.polimi.ingsw.model.Action;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class GUI extends Application implements UI {

    private static BoardRepresentation board;

    private static ClientView clientView;

    private LoginController loginController;
    private WelcomeController welcomeController;
    private DivinitySelectionController divinitySelectionController;
    private PlayerDivinitySelectionController playerDivinitySelectionController;

    private Stage primaryStage;

    private Parent welcomeRoot;
    private Parent loginRoot;
    private Parent playableDivinityRoot;
    private Parent playerDivinityRoot;





    public GUI() {
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        this.board = clientView.getBoard();
    }

    public ClientView getClientView() {
        return clientView;
    }

    //metodi to have
    public void start() {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

       primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1200);

        primaryStage.setTitle("SANTORINI");


        LoginController.setClientView(clientView);
        WelcomeController.setClientView(clientView);
        DivinitySelectionController.setClientView(clientView);
        PlayerDivinitySelectionController.setClientView(clientView);

        clientView.setUI(this);



        FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/fxml/Welcome.fxml"));
        Parent welcomePane = welcomeLoader.load();
        //Scene welcomeScene = new Scene(welcomePane, 750, 500);
        this.welcomeRoot = welcomePane;


        //FXMLLoader loginLoader = new FXMLLoader(loginUrl);
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginPane = loginLoader.load();
       // Scene loginScene = new Scene(loginPane, 750, 500);
        this.loginRoot = loginPane;


        FXMLLoader playableDivinitiesLoader = new FXMLLoader(getClass().getResource("/fxml/DivinitySelection.fxml"));
        Parent playableDivinitiesPane = playableDivinitiesLoader.load();
       // Scene loginScene = new Scene(loginPane, 750, 500);
        this.playableDivinityRoot = playableDivinitiesPane;

        FXMLLoader playerDivinityLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerDivinitySelection.fxml"));
        //Parent playableDivinitiesPane = playableDivinitiesLoader.load();
        Parent playerDivinityPane = playerDivinityLoader.load();
        // Scene loginScene = new Scene(loginPane, 750, 500);
        this.playerDivinityRoot = playerDivinityPane;



        this.welcomeController = welcomeLoader.getController();
        this.loginController = loginLoader.getController();
        this.divinitySelectionController = playableDivinitiesLoader.getController();
        this.playerDivinitySelectionController = playerDivinityLoader.getController();


        //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();


        //stage.setScene(new Scene(welcomeRoot,1100,750));


        //stage.setFullScreen(true);
        //stage.setMaximized(true);

       // stage.show();

        clientView.startConnection();
    }


    public void setWelcomeController(WelcomeController wc) {
        this.welcomeController = wc;
    }


    public void login(){

        System.out.println("\nlogin...");

        Platform.runLater(()->{

            //primaryStage.getScene().setRoot(loginRoot);
            primaryStage.setScene(new Scene(loginRoot,1500,900));
            primaryStage.show();
        });

    }


    public void failedLogin(List<String> usernames) {
        Platform.runLater(()-> {
            loginController.invalidUsername(usernames);
        });
    }

    public void selectPlayersNumber() {}
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber) {

        Platform.runLater(()-> {
            divinitySelectionController.setDivinityOnGrid(divinitiesNames,divinitiesDescriptions,playersNumber);

            primaryStage.getScene().setRoot(playableDivinityRoot);
        });

    }

    public void selectDivinity(List<String> divinitiesNames) {
        System.out.print("\n\nCHOOSE A DIVINITY PLS");

        Platform.runLater(()-> {
            playerDivinitySelectionController.selectPlayerDivinity(divinitiesNames);
            primaryStage.getScene().setRoot(playerDivinityRoot);
        });

    }

    public void placeWorkers() {}
//    public void selectDivinityAndPlaceWorkers(List<String> divinityNames) {}

    public void updateBoard() {}
    public void textMessage(String msg) {}

    public void startTurn() {}
    public void selectWorker() {}
    public void performAction(List<Action> possibleActions) {}

    public void loser(String username) {}
    public void winner(String username) {}

    @Override
    public void playersDivinities() {

    }

    @Override
    public void playerDisconnection(String username) {

    }


}
