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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
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
    private EndGameWinnerController endGameWinnerController;
    private EndGameLoserController endGameLoserController;


    private Stage primaryStage;

    private Parent welcomeRoot;
    private Parent loginRoot;
    private Parent playableDivinityRoot;
    private Parent playerDivinityRoot;
    private Parent matchRoot;
    private Parent disconnectionRoot;
    private Parent endGameWinnerRoot;
    private Parent endGameLoserRoot;





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
        EndGameWinnerController.setClientView(clientView);
        EndGameLoserController.setClientView(clientView);

        clientView.setUI(this);


       loadGUI();

        clientView.startConnection();
    }


    public void login(){


        System.out.println("\nlogin...");

        Platform.runLater(()->{
            primaryStage.setScene(new Scene(loginRoot,1500,1000));
            primaryStage.show();
        });


    }


    public void failedLogin(List<String> usernames) {
        Platform.runLater(()-> {
            primaryStage.getScene().setRoot(loginRoot);
            loginController.invalidUsername(usernames);
        });
    }


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

    public void textMessage(String msg) {
        Platform.runLater(() -> {
            matchController.textMessage("SERVER MESSAGE", "You received this message from the server", msg);
        });
    }

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

        System.out.println("winner event");

        if(username.equals(clientView.getUsername())) {
            Platform.runLater(() -> {
                primaryStage.getScene().setRoot(endGameWinnerRoot);
            });
        }
        else {
            Platform.runLater(() -> {
                primaryStage.getScene().setRoot(endGameLoserRoot);
            });
        }

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
        System.out.println("disconnection event");

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
    public void startGame() {
        Platform.runLater(() -> {
            loginController.gameStart();
        });

    }

    @Override
    public void invalidMove(List<Action> possibleActions, int wrongX, int wrongY) {
        Platform.runLater(() -> {
            matchController.textMessage("ERROR", "Something went wrong with your move", "Please, repeat the action!");
            matchController.handlePossibleActions(possibleActions);
        });
    }

    @Override
    public void invalidBuild(List<Action> possibleActions, int wrongX, int wrongY) {
        Platform.runLater(() -> {
            matchController.textMessage("ERROR", "Something went wrong with your move", "Please, repeat the action!");
            matchController.handlePossibleActions(possibleActions);
        });

    }

    @Override
    public void invalidWorkerPlacement() {
        Platform.runLater(() -> {
            matchController.textMessage("ERROR", "Something went wrong with worker placement", "Please, repeat the action!");
            matchController.setActionPlaceWorkers();
        });
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


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    protected void loadGUI() throws IOException {
        FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/fxml/Welcome.fxml"));
        Parent welcomePane = welcomeLoader.load();
        this.welcomeRoot = welcomePane;

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginPane = loginLoader.load();
        this.loginRoot = loginPane;


        FXMLLoader playableDivinitiesLoader = new FXMLLoader(getClass().getResource("/fxml/DivinitySelection.fxml"));
        Parent playableDivinitiesPane = playableDivinitiesLoader.load();
        this.playableDivinityRoot = playableDivinitiesPane;
        //to make scene reseizable
        Pane p2 = (Pane) playableDivinitiesPane;
        p2.prefHeightProperty().bind(primaryStage.heightProperty());
        p2.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader playerDivinityLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerDivinitySelection.fxml"));
        Parent playerDivinityPane = playerDivinityLoader.load();
        this.playerDivinityRoot = playerDivinityPane;

        FXMLLoader matchLoader = new FXMLLoader(getClass().getResource("/fxml/Match.fxml"));
        Parent matchPane = matchLoader.load();
        this.matchRoot = matchPane;
        //to make scene reseizable
        Pane p3 = (Pane) playableDivinitiesPane;
        p3.prefHeightProperty().bind(primaryStage.heightProperty());
        p3.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader disconnectionLoader = new FXMLLoader(getClass().getResource("/fxml/Disconnection.fxml"));
        Parent disconnectionPane = disconnectionLoader.load();
        this.disconnectionRoot = disconnectionPane;

        FXMLLoader endWinnerLoader = new FXMLLoader(getClass().getResource("/fxml/EndGameWinner.fxml"));
        Parent endWinnerPane = endWinnerLoader.load();
        this.endGameWinnerRoot = endWinnerPane;

        FXMLLoader endLoserLoader = new FXMLLoader(getClass().getResource("/fxml/EndGameLoser.fxml"));
        Parent endLoserPane = endLoserLoader.load();
        this.endGameLoserRoot = endLoserPane;



        this.welcomeController = welcomeLoader.getController();
        this.loginController = loginLoader.getController();
        this.divinitySelectionController = playableDivinitiesLoader.getController();
        this.playerDivinitySelectionController = playerDivinityLoader.getController();
        this.matchController = matchLoader.getController();
        this.disconnectionController = disconnectionLoader.getController();
        this.endGameWinnerController = endWinnerLoader.getController();
        this.endGameLoserController = endLoserLoader.getController();



    }


}
