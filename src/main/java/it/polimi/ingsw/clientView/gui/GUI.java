package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.BoardRepresentation;
import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.UI;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
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

public class GUI implements UI {

    BoardRepresentation board;

    private LoginController loginController;

    private Stage primaryStage;





    public GUI(ClientView clientView) {
        board = clientView.getBoard();
        clientView = clientView;
        loginController=null;
    }

    public void startGUI() {
    }


    //metodi to have
    public void start() {

    }
    public void login(){

        FXMLLoader loader = null;

        try {
            URL url = new File("/home/simone/IdeaProjects/ing-sw-2020-AmbrosiniBarzaghi-Bonalumi-Cattaneo/resources/Login.fxml").toURI().toURL();
            loader = new FXMLLoader(url);
            Parent root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginController = loader.getController();

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
