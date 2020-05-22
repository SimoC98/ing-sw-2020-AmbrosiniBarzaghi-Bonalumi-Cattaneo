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

    BoardRepresentation board;

    private static ClientView clientView;

    private LoginController loginController;

    private WelcomeController welcomeController;

    private Stage primaryStage;





    public GUI() {
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        this.board = clientView.getBoard();
    }

    public static ClientView getClientView() {
        return clientView;
    }

    //metodi to have
    public void start() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginController.setClientView(clientView);
        WelcomeController.setClientView(clientView);


        URL loginUrl = new File("resources/Login.fxml").toURI().toURL();
        URL welcomeUrl = new File("resources/Welcome.fxml").toURI().toURL();



        FXMLLoader welcomeLoader = new FXMLLoader(welcomeUrl);
        Parent welcomePane = welcomeLoader.load();
        Scene welcomeScene = new Scene(welcomePane, 750, 500);

        FXMLLoader loginLoader = new FXMLLoader(loginUrl);
        Parent loginPane = loginLoader.load();
        Scene loginScene = new Scene(loginPane, 750, 500);

        welcomeController = welcomeLoader.getController();
        loginController = loginLoader.getController();

        welcomeController.setStage(stage);

        loginController.setStage(stage);

        welcomeController.setLoginScene(loginScene);

        stage.setScene(welcomeScene);
        stage.show();



    }


    public void login(){

        System.out.println("\nlogin...");

        Platform.runLater(()-> {
            welcomeController.changeScene();
        });


        /*stage.setTitle("prova");
        stage.setScene(new Scene(root,750,500));
        stage.show();*/


    }


    public void failedLogin(List<String> usernames) {}

    public void selectPlayersNumber() {}
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber) {}

    public void selectDivinity(List<String> divinitiesNames) {}
    public void placeWorkers() {}
//    public void selectDivinityAndPlaceWorkers(List<String> divinityNames) {}

    public void updateBoard() {}
    public void textMessage(String msg) {}

    public void startTurn() {}
    public void selectWorker() {}
    public void performAction(List<Action> possibleActions) {}

    public void loser(String username) {}
    public void winner(String username) {}

    public void printPlayersInGame() {}


}
