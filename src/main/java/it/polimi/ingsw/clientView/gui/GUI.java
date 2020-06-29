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

/**
 * This class represents the Graphical User Interface. It implements {@link UI} and extends Application so as it can host
 * different scenes. It creates several controllers to visualize different scenes of the game. See {@link DisconnectionController},
 * {@link DivinitySelectionController}, {@link EndGameController}, {@link LoginController},
 * {@link MatchController}, {@link PlayerDivinitySelectionController}
 */
public class GUI extends Application implements UI {

    private static ClientView clientView;

    private LoginController loginController;
    private DivinitySelectionController divinitySelectionController;
    private PlayerDivinitySelectionController playerDivinitySelectionController;
    private MatchController matchController;

    private EndGameController endController;

    private DisconnectionController disconnectionController;


    private Stage primaryStage;

    private Parent loginRoot;
    private Parent playableDivinityRoot;
    private Parent playerDivinityRoot;
    private Parent matchRoot;

   private Parent endGameRoot;

    private Parent disconnectionRoot;

    public GUI() {
    }

    /**
     * Assigns the {@link ClientView} to this instance of GUI
     * @param clientView proxy to communicate to the server
     */
    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    /**
     * It gives a reference of the instance of the ClientView
     * @return {@link ClientView}
     */
    public ClientView getClientView() {
        return clientView;
    }

    public void start() {
        launch();
    }

    /**
     * It effectively start the GUI initializing all the controllers, displaying the title and calling {@link GUI#loadGUI()}
     * @param stage Stage that will be used for the gui
     * @throws Exception called if the thread fails
     */
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

        LoginController.setGui(this);
        DivinitySelectionController.setGui(this);
        PlayerDivinitySelectionController.setGui(this);
        MatchController.setGui(this);
        DisconnectionController.setGui(this);
        EndGameController.setGui(this);

        clientView.setUI(this);

        loadGUI();

        clientView.startConnection();
    }


    /**
     * Displays the starting scene setting the correct root.
     */
    public void login(){

        //System.out.println("\nlogin...");

        Platform.runLater(()->{
            primaryStage.setScene(new Scene(loginRoot,1500,1000));
            primaryStage.show();
        });

    }

    /**
     * Invoked when the user fails to log. It swaps to the appropriate root and calls {@link LoginController#invalidUsername(List)}
     * @param usernames List of strings of the already logged players
     */
    public void failedLogin(List<String> usernames) {
        Platform.runLater(()-> {
            primaryStage.getScene().setRoot(loginRoot);
            loginController.invalidUsername(usernames);
        });
    }


    /**
     * Manages the selection of the divinities to be selected among all the divinities, calling {@link DivinitySelectionController#setDivinityOnGrid(List, List, int, List)}
     * @param divinitiesNames List of all divinities
     * @param divinitiesDescriptions List of their effects
     * @param playersNumber number of players in game
     * @param players Names of the players
     */
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players) {

        Platform.runLater(()-> {
            divinitySelectionController.setDivinityOnGrid(divinitiesNames,divinitiesDescriptions,playersNumber,players);

            primaryStage.setMinHeight(850);
            primaryStage.getScene().setRoot(playableDivinityRoot);
        });
    }

    /**
     * Manages the user's choice of the divinity calling {@link PlayerDivinitySelectionController#selectPlayerDivinity(List)}
     * and switching to the root to visualize the divinities.
     * @param divinitiesNames names of the selectable divinities
     */
    public void selectDivinity(List<String> divinitiesNames) {
        //System.out.print("\n\nCHOOSE A DIVINITY");

        Platform.runLater(()-> {
            playerDivinitySelectionController.selectPlayerDivinity(divinitiesNames);
            primaryStage.getScene().setRoot(playerDivinityRoot);
        });
    }

    /**
     * Called when the client has to position their workers. {@link MatchController#setActionPlaceWorkers()}
     */
    public void placeWorkers() {
        //System.out.println("place your workers...");

        Platform.runLater(() -> {
            matchController.setActionPlaceWorkers();
        });
    }

    /**
     * Displays a text message coming from the server thanks to {@link MatchController#textMessage(String, String, String)}
     * @param msg generic message
     */
    public void textMessage(String msg) {
        Platform.runLater(() -> {
            matchController.textMessage("SERVER MESSAGE", "You received this message from the server", msg);
        });
    }

    /**
     * Called at the beginning of the player's turn. The next action will be selecting the worker {@link MatchController#startTurn()}
     * {@link MatchController#setActionSelectWorker()}
     */
    public void startTurn() {
        //System.out.println("select worker...");

        Platform.runLater(() -> {
            matchController.startTurn();
            matchController.setActionSelectWorker();
        });
    }

    /**
     * Called when the player needs to select a worker (the previous selection was refused at least once)
     */
    public void selectWorker() {
        //System.out.println("select worker...");

        Platform.runLater(()->{
            matchController.setActionSelectWorker();
        });
    }

    /**
     * Handles the map of actions and postions coming from the server. The user will have to select an action and 
     * a position. {@link MatchController#handlePossibleActions(Map)}
     * @param possibleActions {@code Map} of {@link Action} and list of coordinates
     */
    public void performAction(Map<Action, List<Pair<Integer, Integer>>> possibleActions) {
        List<Action> l = new ArrayList<>();
        possibleActions.keySet().stream().forEach(x -> l.add(x));

        Platform.runLater(() -> {
            matchController.handlePossibleActions(possibleActions);
        });
    }

    /**
     * Called when a user lost. A method updates the board ({@link MatchController#manageLoserPlayer()}.
     * If the losing player is this client, then they will be able to watch the rest of the game but will not
     * be able to perform actions ({@link MatchController#loserPlayer()}; otherwise a text message displays the name
     * of the losing player ({@link MatchController#textMessage(String, String, String)}
     * @param username Name of loser
     */
    public void loser(String username) {
        Platform.runLater(() -> {
            matchController.manageLoserPlayer();

            if(username.equals(clientView.getUsername())) {
                matchController.loserPlayer();
            }
            else {
                matchController.textMessage("LOSER","user " + username + "has lost","don't give up!");
            }
        });
    }

    /**
     * Manages the victory of a player: if this client is the winner {@link EndGameController#setWinner()} else {@link EndGameController#setLoser()}.
     * Anyways the game ends but the users can reconnect to start another game.
     * @param username Name of winner
     */
    public void winner(String username) {

        //System.out.println("winner event");

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

    /**
     * Sets the divinities for each player {@link MatchController#setPlayers()}
     */
    @Override
    public void playersDivinities() {
        Platform.runLater(()->{
            matchController.setPlayers();

            primaryStage.setMinHeight(700);
            primaryStage.getScene().setRoot(matchRoot);
        });
    }

    /**
     * Manages the disconnection of a player, displaying their name and disconnecting.
     * It changes the root and calls{@link DisconnectionController#disconnect(String)}
     * @param username Disconnected player
     */
    @Override
    public void playerDisconnection(String username) {
        clientView.disconnect();

        Platform.runLater(() -> {
            primaryStage.getScene().setRoot(disconnectionRoot);

            disconnectionController.disconnect(username);
        });
    }

    /**
     * Called if the player correctly joined a lobby.
     * It changes the root and calls {@link LoginController#inLobby()}
     */
    @Override
    public void inLobby() {
        Platform.runLater(()->{
            primaryStage.getScene().setRoot(loginRoot);
            loginController.inLobby();
        });
    }

    /**
     * Called if the player failed to joined a lobby.
     * It changes the root and calls {@link LoginController#lobbyFull()}
     */
    @Override
    public void lobbyFull() {
        Platform.runLater(() -> {
            primaryStage.getScene().setRoot(loginRoot);
            loginController.lobbyFull();
        });
    }

    /**
     * Called at the end of a player's turn {@link MatchController#endTurn()}
     */
    @Override
    public void endTurn() {
        Platform.runLater(() -> {
            matchController.endTurn();
        });
    }

    /**
     * Method invoked when the game starts {@link LoginController#gameStart()}
     */
    @Override
    public void startGame() {
        Platform.runLater(() -> {
            loginController.gameStart();
        });
    }

    /**
     * Called when the controller finds an error in the previous move and informs the client. It displays a message built
     * on the corresponding type of bad move and has the user choose the action again {@link MatchController#handlePossibleActions(Map)}
     * @param possibleActions list of possible actions
     * @param wrongX wrong x coordinate
     * @param wrongY wrong y coordinate
     * @param startX
     * @param startY
     */
    @Override
    public void invalidMove(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY, int startX, int startY) {
        BoardRepresentation board = clientView.getBoard();
        String header;

        if(Math.abs(startX-wrongX)>1 || Math.abs(startY-wrongY)>1) header = "YOU MUST SELECT AN ADJACENT TILE!";
        else if(board.getBoard()[wrongX][wrongY]==4) header = " YOU CAN'T MOVE ON A DOME!";
        else if(board.isThereAWorker(wrongX,wrongY)!=null) header=" YOU CAN'T MOVE ON AN OCCUPIED TILE!";
        else if(board.getBoard()[wrongX][wrongY]-board.getBoard()[startX][startY]>1) header=" YOU CAN'T MOVE TO A TILE SO HIGH!";
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) header=" YOU MUST SELECT A TILE ON THE BOARD!";
        else header=" YOU CAN'T MOVE HERE!";

        Platform.runLater(() -> {
            matchController.textMessage("INVALID MOVE!", header, "Please, repeat the action!");
            matchController.handlePossibleActions(possibleActions);
        });
    }

    /**
     * Called when the controller finds an error in the previous build and informs the client. It displays a message built
     * on the corresponding type of bad build and has the user choose the action again {@link MatchController#handlePossibleActions(Map)}
     * @param possibleActions list of possible actions
     * @param wrongX wrong x coordinate
     * @param wrongY wrong y coordinate
     * @param actualX
     * @param actualY
     */
    @Override
    public void invalidBuild(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY, int actualX, int actualY) {
        BoardRepresentation board = clientView.getBoard();
        String header;

        if(Math.abs(actualX-wrongX)>1 || Math.abs(actualY-wrongY)>1) header = "YOU MUST SELECT AN ADJACENT TILE!";
        else if(board.getBoard()[wrongX][wrongY]==4) header=" YOU CAN'T BUILD ON A DOME!";
        else if(board.isThereAWorker(wrongX,wrongY)!=null) header=" YOU CAN'T BUILD ON AN OCCUPIED TILE!";
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) header=" YOU MUST SELECT A TILE ON THE BOARD!";
        else header=" YOU CAN'T BUILD HERE!";


        Platform.runLater(() -> {
            matchController.textMessage("INVALID BUILD!", header, "Please, repeat the action!");
            matchController.handlePossibleActions(possibleActions);
        });

    }

    /**
     * Called if the client placed their workers not correctly. After a message the user has to choose again {@link MatchController#setActionPlaceWorkers()}
     */
    @Override
    public void invalidWorkerPlacement() {
        Platform.runLater(() -> {
            matchController.textMessage("ERROR", "Something went wrong with worker placement", "Please, repeat the action!");
            matchController.setActionPlaceWorkers();
        });
    }

    /**
     * Called if the user does not select the worker correctly. After a message they have to choose again
     * @param wrongX wrong x position
     * @param wrongY wrong y position
     */
    @Override
    public void invalidWorkerSelection(int wrongX, int wrongY) {
        //System.out.println("errore selezione worker");

        Platform.runLater(() -> {
            matchController.textMessage("ERROR!","Invalid worker selection","Please, repeat the action!");
            matchController.setActionSelectWorker();
        });
    }

    /**
     * Updates the {@link BoardRepresentation} with the move of the specified player {@link MatchController#moveUpdate(String, int, int, int, int)}
     * @param player name of the player moving
     * @param xFrom beginning worker's position
     * @param yFrom beginning worker's position
     * @param xTo destination worker's position
     * @param yTo destination worker's position
     */
    @Override
    public void moveUpdate(String player, int xFrom, int yFrom, int xTo, int yTo) {
        Platform.runLater(() -> {
            matchController.moveUpdate(player,xFrom,yFrom,xTo,yTo);
        });

    }

    /**
     * Updates the {@link BoardRepresentation} with the build of the specified player {@link MatchController#buildUpdate(String, int, int)}
     * @param player name of the building player
     * @param x coordinate of the tile built
     * @param y coordinate of the tile built
     */
    @Override
    public void buildUpdate(String player, int x, int y) {

        Platform.runLater(() -> {
            matchController.buildUpdate(player,x,y);
        });

    }

    /**
     * Updates the {@link BoardRepresentation} with the placements of the specified player's workers.
     * @param player name of the player to retrieve their color
     * @param x1 first worker's coordinate
     * @param y1 first worker's coordinate
     * @param x2 second worker's coordinate
     * @param y2 second worker's coordinate
     */
    @Override
    public void workerPlacementUpdate(String player, int x1, int y1, int x2, int y2) {
        //System.out.println("placing workers...");

        Platform.runLater(() -> {
            matchController.placeWorkers(player,x1,y1,x2,y2);
        });
    }

    /**
     * Loads the resources that will be used to display the GUI elements from fxml files. It also binds the controllers
     * in order to use them after their setup happened ({@code initialize()} has been called when creating them)
     * @throws IOException thrown if the fxmlLoader fails to retrieve the files.
     */
    protected void loadGUI() throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginPane = loginLoader.load();
        this.loginRoot = loginPane;
        //makes scene resizable
        Pane p1 = (Pane) loginPane;
        p1.prefHeightProperty().bind(primaryStage.heightProperty());
        p1.prefWidthProperty().bind(primaryStage.widthProperty());


        FXMLLoader playableDivinitiesLoader = new FXMLLoader(getClass().getResource("/fxml/DivinitySelection.fxml"));
        Parent playableDivinitiesPane = playableDivinitiesLoader.load();
        this.playableDivinityRoot = playableDivinitiesPane;
        //makes scene resizable
        Pane p2 = (Pane) playableDivinitiesPane;
        p2.prefHeightProperty().bind(primaryStage.heightProperty());
        p2.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader playerDivinityLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerDivinitySelection.fxml"));
        Parent playerDivinityPane = playerDivinityLoader.load();
        this.playerDivinityRoot = playerDivinityPane;
        //makes scene resizable
        Pane p3 = (Pane) playerDivinityPane;
        p3.prefHeightProperty().bind(primaryStage.heightProperty());
        p3.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader matchLoader = new FXMLLoader(getClass().getResource("/fxml/Match.fxml"));
        Parent matchPane = matchLoader.load();
        this.matchRoot = matchPane;
        //makes scene resizable
        Pane p4 = (Pane) matchPane;
        p4.prefHeightProperty().bind(primaryStage.heightProperty());
        p4.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader endLoader = new FXMLLoader(getClass().getResource("/fxml/EndGame.fxml"));
        Parent endPane = endLoader.load();
        this.endGameRoot = endPane;
        //makes scene resizable
        Pane p5 = (Pane) endPane;
        p5.prefHeightProperty().bind(primaryStage.heightProperty());
        p5.prefWidthProperty().bind(primaryStage.widthProperty());

        FXMLLoader disconnectionLoader = new FXMLLoader(getClass().getResource("/fxml/Disconnection.fxml"));
        Parent disconnectionPane = disconnectionLoader.load();
        this.disconnectionRoot = disconnectionPane;
        //makes scene resizable
        Pane p6 = (Pane) disconnectionPane;
        p6.prefHeightProperty().bind(primaryStage.heightProperty());
        p6.prefWidthProperty().bind(primaryStage.widthProperty());

        this.loginController = loginLoader.getController();
        this.divinitySelectionController = playableDivinitiesLoader.getController();
        this.playerDivinitySelectionController = playerDivinityLoader.getController();
        this.matchController = matchLoader.getController();
        this.disconnectionController = disconnectionLoader.getController();
        this.endController = endLoader.getController();
    }


}
