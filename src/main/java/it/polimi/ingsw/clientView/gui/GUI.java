package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.BoardRepresentation;
import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.UI;
import it.polimi.ingsw.model.Action;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.List;

public class GUI extends Application implements UI {

    private static BoardRepresentation board;

    private static ClientView clientView;

    private LoginController loginController;
    private WelcomeController welcomeController;
    private DivinitySelectionController divinitySelectionController;
    private PlayerDivinitySelectionController playerDivinitySelectionController;
    private MatchController matchController;
    private DisconnectionController disconnectionController;

    private Stage primaryStage;

    private Parent welcomeRoot;
    private Parent loginRoot;
    private Parent playableDivinityRoot;
    private Parent playerDivinityRoot;
    private Parent matchRoot;
    private Parent disconnectionRoot;





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
        MatchController.setClientView(clientView);
        DisconnectionController.setClientView(clientView);

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

        FXMLLoader matchLoader = new FXMLLoader(getClass().getResource("/fxml/Match.fxml"));
        //Parent playableDivinitiesPane = playableDivinitiesLoader.load();
        Parent matchPane = matchLoader.load();
        // Scene loginScene = new Scene(loginPane, 750, 500);
        this.matchRoot = matchPane;

        FXMLLoader disconnectionLoader = new FXMLLoader(getClass().getResource("/fxml/Disconnection.fxml"));
        //Parent playableDivinitiesPane = playableDivinitiesLoader.load();
        Parent disconnectionPane = disconnectionLoader.load();
        // Scene loginScene = new Scene(loginPane, 750, 500);
        this.disconnectionRoot = disconnectionPane;



        this.welcomeController = welcomeLoader.getController();
        this.loginController = loginLoader.getController();
        this.divinitySelectionController = playableDivinitiesLoader.getController();
        this.playerDivinitySelectionController = playerDivinityLoader.getController();
        this.matchController = matchLoader.getController();
        this.disconnectionController = disconnectionLoader.getController();

        clientView.startConnection();
    }


    public void setWelcomeController(WelcomeController wc) {
        this.welcomeController = wc;
    }


    public void login(){


        System.out.println("\nlogin...");

        if(clientView.getUsername()!=null) clientView.loginQuestion(clientView.getUsername());
        else {
            Platform.runLater(()->{
                primaryStage.setScene(new Scene(loginRoot,1500,1000));
                primaryStage.show();
            });
        }


    }


    public void failedLogin(List<String> usernames) {
        Platform.runLater(()-> {
            primaryStage.getScene().setRoot(loginRoot);
            loginController.invalidUsername(usernames);
        });
    }

    public void selectPlayersNumber() {}


    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players) {

        Platform.runLater(()-> {
            divinitySelectionController.setDivinityOnGrid(divinitiesNames,divinitiesDescriptions,playersNumber,players);

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

    public void placeWorkers() {
        System.out.println("place your workers...");

        Platform.runLater(() -> {
            matchController.setActionPlaceWorkers();
        });
    }

    public void textMessage(String msg) {}

    public void startTurn() {
        System.out.println("select worker...");

        Platform.runLater(() -> {
            matchController.setActionSelectWorker();
        });
    }

    public void selectWorker() {
        System.out.println("select worker...");

        Platform.runLater(()->{
            matchController.setActionSelectWorker();
        });
    }

    public void performAction(List<Action> possibleActions) {
        Platform.runLater(() -> {
            matchController.handlePossibleActions(possibleActions);
        });
    }

    public void loser(String username) {
        /*
        TODO: loser --> on the right side communication that has lost; keep receiving notifications and board updates
              not loser --> communication of loser (alert?)
         */
    }

    public void winner(String username) {
        /*
        TODO: end game scene loaded; buttons to exit or start a new match
         */
    }

    @Override
    public void playersDivinities() {
        Platform.runLater(()->{
            matchController.setPlayers();

            primaryStage.getScene().setRoot(matchRoot);
        });
    }

    @Override
    public void playerDisconnection(String username) {
        clientView.disconnect();

        Platform.runLater(() -> {
            primaryStage.getScene().setRoot(disconnectionRoot);

            disconnectionController.disconnect(username);
        });

    }

    @Override
    public void inLobby() {
        Platform.runLater(()->{
            primaryStage.getScene().setRoot(loginRoot);
            loginController.inLobby();
        });

    }

    @Override
    public void lobbyFull() {
        Platform.runLater(() -> {
            primaryStage.getScene().setRoot(loginRoot);
            loginController.lobbyFull();
        });

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void invalidMove(List<Action> possibleActions, int wrongX, int wrongY) {

    }

    @Override
    public void invalidBuild(List<Action> possibleActions, int wrongX, int wrongY) {

    }

    @Override
    public void invalidWorkerPlacement() {

    }

    @Override
    public void invalidWorkerSelection(int wrongX, int wrongY) {
        System.out.println("errore selezione worker");

    }

    @Override
    public void moveUpdate(String player, int xFrom, int yFrom, int xTo, int yTo) {
        Platform.runLater(() -> {
            matchController.moveUpdate(player,xFrom,yFrom,xTo,yTo);
        });

    }

    @Override
    public void buildUpdate(String player, int x, int y) {

        Platform.runLater(() -> {
            matchController.buildUpdate(player,x,y);
        });

    }

    @Override
    public void workerPlacementUpdate(String player, int x1, int y1, int x2, int y2) {
        System.out.println("placing workers...");

        Platform.runLater(() -> {
            matchController.placeWorkers(player,x1,y1,x2,y2);
        });

    }


}
