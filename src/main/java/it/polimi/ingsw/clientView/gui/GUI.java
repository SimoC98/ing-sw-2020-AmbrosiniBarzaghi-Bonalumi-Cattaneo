package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.clientView.BoardRepresentation;
import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.UI;
import it.polimi.ingsw.model.Action;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class GUI extends Application implements UI {


    //private static BoardRepresentation board;

    private static ClientView clientView;

    private LoginController loginController;
    private WelcomeController welcomeController;
    private DivinitySelectionController divinitySelectionController;
    private PlayerDivinitySelectionController playerDivinitySelectionController;
    private MatchController matchController;

    private EndGameController endController;

    private DisconnectionController disconnectionController;


    private Stage primaryStage;

    private Parent welcomeRoot;
    private Parent loginRoot;
    private Parent playableDivinityRoot;
    private Parent playerDivinityRoot;
    private Parent matchRoot;

   private Parent endGameRoot;

    private Parent disconnectionRoot;

    public GUI() {
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        //this.board = clientView.getBoard();
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

        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(600);

        primaryStage.setTitle("SANTORINI");
        primaryStage.getIcons().add(new Image(getClass().getResource("/graphics/santorini_risorse-grafiche-2_Texture2D_title_island.png").toExternalForm()));

        primaryStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            exit(0);
        });


        /*LoginController.setClientView(clientView);
        WelcomeController.setClientView(clientView);
        DivinitySelectionController.setClientView(clientView);
        PlayerDivinitySelectionController.setClientView(clientView);
        MatchController.setClientView(clientView);
        DisconnectionController.setClientView(clientView);
        EndGameController.setClientView(clientView);*/

        LoginController.setGui(this);
        DivinitySelectionController.setGui(this);
        PlayerDivinitySelectionController.setGui(this);
        MatchController.setGui(this);
        DisconnectionController.setGui(this);
        EndGameController.setGui(this);
        //

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

            primaryStage.setMinHeight(850);
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
            matchController.startTurn();
            matchController.setActionSelectWorker();
        });
    }

    public void selectWorker() {
        System.out.println("select worker...");

        Platform.runLater(()->{
            matchController.setActionSelectWorker();
        });
    }

    public void performAction(Map<Action, List<Pair<Integer, Integer>>> possibleActions) {
        List<Action> l = new ArrayList<>();
        possibleActions.keySet().stream().forEach(x -> l.add(x));

        Platform.runLater(() -> {
            matchController.handlePossibleActions(possibleActions);
        });
    }

    public void loser(String username) {
        /*
        TODO: loser --> on the right side communication that has lost; keep receiving notifications and board updates
              not loser --> communication of loser (alert?)
         */

        Platform.runLater(() -> {
            matchController.manageLoserPlayer();

            if(username.equals(clientView.getUsername())) {
                matchController.loserPlayer();
            }
            else {
                matchController.textMessage("LOSER","user " + username + "has lost","fffffffffff");
            }
        });

    }

    public void winner(String username) {

        System.out.println("winner event");

        if(username.equals(clientView.getUsername())) {
            Platform.runLater(() -> {
                endController.setWinner();
                primaryStage.getScene().setRoot(endGameRoot);
            });
        }
        else {
            Platform.runLater(() -> {
                endController.setLoser();
                primaryStage.getScene().setRoot(endGameRoot);
            });
        }

    }

    @Override
    public void playersDivinities() {
        Platform.runLater(()->{
            matchController.setPlayers();

            primaryStage.setMinHeight(700);
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
        Platform.runLater(() -> {
            matchController.endTurn();
        });
    }

    @Override
    public void startGame() {
        Platform.runLater(() -> {
            loginController.gameStart();
        });

    }

    @Override
    public void invalidMove(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY) {
        BoardRepresentation board = clientView.getBoard();
        int selectedWX = matchController.getSelectedWX();
        int selectedWY = matchController.getSelectedWY();
        String header;

        if(board.getBoard()[wrongX][wrongY]==4) header = " YOU CAN'T MOVE ON A DOME!";
        else if(board.isThereAWorker(wrongX,wrongY)!=null) header=" YOU CAN'T MOVE ON AN OCCUPIED TILE!";
        else if(board.getBoard()[wrongX][wrongY]-board.getBoard()[selectedWY][selectedWY]>1) header=" YOU CAN'T MOVE TO A TILE SO HIGH!";
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) header=" YOU MUST SELECT A TILE ON THE BOARD!";
        else if(Math.abs(wrongX-selectedWX)>1 || Math.abs(wrongY-selectedWY)>1) header=" YOU MUST SELECT AN ADJACENT TILE!";
        else header=" YOU CAN'T MOVE HERE!";

        Platform.runLater(() -> {
            matchController.textMessage("INVALID MOVE!", header, "Please, repeat the action!");
            matchController.handlePossibleActions(possibleActions);
        });
    }

    @Override
    public void invalidBuild(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY) {
        BoardRepresentation board = clientView.getBoard();
        int selectedWX = matchController.getSelectedWX();
        int selectedWY = matchController.getSelectedWY();
        String header;

        if(board.getBoard()[wrongX][wrongY]==4) header=" YOU CAN'T BUILD ON A DOME!";
        else if(board.isThereAWorker(wrongX,wrongY)!=null) header=" YOU CAN'T BUILD ON AN OCCUPIED TILE!";
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) header=" YOU MUST SELECT A TILE ON THE BOARD!";
        else if(Math.abs(wrongX-selectedWX)>1 || Math.abs(wrongY-selectedWY)>1) header=" YOU MUST SELECT A TILE ON THE BOARD!";
        else header=" YOU CAN'T BUILD HERE!";


        Platform.runLater(() -> {
            matchController.textMessage("INVALID BUILD!", header, "Please, repeat the action!");
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

        Platform.runLater(() -> {
            matchController.textMessage("ERROR!","Invalid worker selection","Please, repeat the action!");
            matchController.setActionSelectWorker();
        });
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
        //to make scene reseizable
        Pane p1 = (Pane) loginPane;
        p1.prefHeightProperty().bind(primaryStage.heightProperty());
        p1.prefWidthProperty().bind(primaryStage.widthProperty());


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
        //to make scene reseizable
        Pane p3 = (Pane) playerDivinityPane;
        p3.prefHeightProperty().bind(primaryStage.heightProperty());
        p3.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader matchLoader = new FXMLLoader(getClass().getResource("/fxml/Match.fxml"));
        Parent matchPane = matchLoader.load();
        this.matchRoot = matchPane;
        //to make scene reseizable
        Pane p4 = (Pane) matchPane;
        p4.prefHeightProperty().bind(primaryStage.heightProperty());
        p4.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader endLoader = new FXMLLoader(getClass().getResource("/fxml/EndGame.fxml"));
        Parent endPane = endLoader.load();
        this.endGameRoot = endPane;
        //to make scene reseizable
        Pane p5 = (Pane) endPane;
        p5.prefHeightProperty().bind(primaryStage.heightProperty());
        p5.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader disconnectionLoader = new FXMLLoader(getClass().getResource("/fxml/Disconnection.fxml"));
        Parent disconnectionPane = disconnectionLoader.load();
        this.disconnectionRoot = disconnectionPane;

        this.welcomeController = welcomeLoader.getController();
        this.loginController = loginLoader.getController();
        this.divinitySelectionController = playableDivinitiesLoader.getController();
        this.playerDivinitySelectionController = playerDivinityLoader.getController();
        this.matchController = matchLoader.getController();
        this.disconnectionController = disconnectionLoader.getController();
        this.endController = endLoader.getController();
    }


}
