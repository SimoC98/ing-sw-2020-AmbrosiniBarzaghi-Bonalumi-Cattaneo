package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.update.ModelUpdate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Core class of the game logic in the model.
 * This class holds the list of the active players.
 * It initializes everything necessary to start a game
 * and to follow its evolution.
 * <p>
 * The basic idea is to track an user decisions
 * and to communicate to other players the changes sending information through
 * {@link ModelUpdate}.
 */
public class Match extends Observable<ServerEvent> implements Model{
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;
    private static Action userAction;
    private Worker selectedWorker;
    private Map<String,Divinity> divinities;

    private int startPlayer;

    private int turnId = 0;

    /**
     * Constructor used to support other classes
     *  and to simplify testing
     */
    public Match() {}

    /**
     * Constructor used to support other classes
     *  and to simplify testing
     */
    public Match(Board board) {
        this.board = board;
    }

    /**
     * Main constructor for {@link Match}
     * it initializes the variables to manage a game
     * @param users only parameter as other properties are managed otherwise
     */
    public Match(List<String> users) {
        userAction = null;
        selectedWorker = null;
        this.board = new Board();
        players = new ArrayList<>();
        divinities = new HashMap<>();
        Color[] colors = Color.values();
        for(int i=0; i < users.size();i++) {
            Player newPlayer = new Player(users.get(i),colors[i]);
            players.add(newPlayer);
        }
        currentPlayer = null;
    }

    /**
     * Sets the starting player
     * @param startPlayer name of the starter
     */
    public void setStartPlayer(String startPlayer) {
        for(Player p : players) {
            if(p.getUsername().equals(startPlayer)) {
                this.currentPlayer=p;
                this.startPlayer = players.indexOf(p);
            }
        }
    }


    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    /**
     * @return {@link Player} whom turn it is
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return {@link Worker} chosen by the current
     * {@link Player} to perform an action
     */
    public Worker getSelectedWorker(){return selectedWorker;}

    public Board getBoard() {
        return board;
    }

    /**
     * Its aim is to hold a user choice
     * in order to evaluate if it is correct
     * and in that case to execute it
     * @return
     */
    public static Action getUserAction() {
        return userAction;
    }


    /**
     * Method used at the end of a player's turn
     * to initialize the next player's one
     */
    public void startNextTurn(){
        if(turnId>0) {
            for(Player p : players) {
                if(!p.equals(currentPlayer)) {
                    if(p.getDivinity().hasSetEffectOnOpponentWorkers()) currentPlayer.setDivinity(currentPlayer.getDivinity().getDivinity());
                }
            }
        }

        int playerIndex = players.indexOf(currentPlayer);
        if (playerIndex + 1>= players.size()) {
            playerIndex = 0;
        }else {
            playerIndex = playerIndex + 1;
        }
        currentPlayer = players.get(playerIndex);

        for(Player p : players) {
            if(!p.equals(currentPlayer)) {
                if(p.getDivinity().hasSetEffectOnOpponentWorkers() && !p.isLoser()) p.getDivinity().setEffectOnOpponentWorkers(currentPlayer);
            }
        }

        turnId++;
        currentPlayer.startOfTurn();
    }

    /**
     * {@link Worker} whom a {@link Player} decides to perform an action with.
     * Only the coordinates are passed; if the choice is not valid an exception is thrown
     * @param x expected {@link Worker} {@code x} coordinate
     * @param y expected {@link Worker} {@code y} coordinate
     * @throws InvalidWorkerSelectionException thrown whether the worker is not in the specified position, the worker does not belong to the current player or such worker has no available moves
     */
    public void selectWorker(int x, int y) throws InvalidWorkerSelectionException{
        if (board.getTile(x,y).isOccupied() && board.getTile(x, y).getWorker().getPlayer().equals(currentPlayer) && board.getAvailableMoveTiles(board.getTile(x,y).getWorker()).size()>0) {
            selectedWorker = board.getTile(x, y).getWorker();
        }
        else throw new InvalidWorkerSelectionException();
    }

    /**
     *This method has to be called at the begging of the game by
     * each player to place their workers on the {@link Board}.
     * <p>
     * The player selects four coordinates on which his two workers
     * will be placed; if a positioning is not valid, the player
     * will have to reenter all the four coordinates again
     * @throws WorkerBadPlacementException Exception called when the coordinates are out of bound, they are the same, or a {@link Tile} is already occupied by a precedent player's worker
     */
    public void placeWorkers(int x1, int y1, int x2, int y2) throws WorkerBadPlacementException{
        if(board.getTile(x1,y1) != null && board.getTile(x2,y2) != null && !board.getTile(x1,y1).equals(board.getTile(x2,y2)) && !board.getTile(x1,y1).isOccupied() && !board.getTile(x2,y2).isOccupied()) {
            currentPlayer.addWorker(board.getTile(x1, y1));
            currentPlayer.addWorker(board.getTile(x2, y2));
        }
        else throw new WorkerBadPlacementException();
    }

    /**
     * @return The winning {@link Player}
     */
    public Player findWinner(){
        for(Player p : players) {
            if (p.isWinner()) return p;
        }
         return null;
    }

    /**
     * Returns the index of the winning player, if there is one
     * @return index of the winning player
     */
    public int checkWinner() {
        Player winner = findWinner();

        if(winner==null) return -1;
        else {
            notify(new WinnerEvent(winner.getUsername()));
            return players.indexOf(winner);
        }
    }

    /**
     * This method checks if a player has lost; if so that player is removed from
     * {@link Match#players}, his workers are removed from the {@link Board} and the other players are notified.
     * If only one {@link Player} is left, he is declared the winner
     * @return Returns {@code true} if a player lost after eliminating him
     */
    public Boolean checkLoser(){
        String loser = currentPlayer.getUsername();
        Player loserPlayer = currentPlayer;
        for (Worker w : currentPlayer.getWorkers()) {
            if(board.getAvailableMoveTiles(w).size() > 0) return false;
        }
        board.removePlayerWorkers(currentPlayer);
        currentPlayer.setLoser();
        startNextTurn();

        players.remove(loserPlayer);
        if (players.size() == 1) {
            currentPlayer.setWinner();
            notify(new WinnerEvent(currentPlayer.getUsername()));
        }
        else {
            notify(new LoserEvent(loser));
        }
        return true;
    }

    /**
     * Action chosen by a client
     * @param action one of the possible actions from  the enumeration {@link Action} i.e. {@link Action#BUILD}, {@link Action#BUILDDOME}, {@link Action#MOVE} or {@link Action#END}
     * @throws InvalidActionException The action is not present among the current player's actions
     */
    public void setAction(Action action) throws InvalidActionException{
        if (currentPlayer.getPossibleActions().contains(action)) userAction = action;
        else throw new InvalidActionException();
    }


    /**
     * Moves the {@link Match#selectedWorker} of the {@link Match#currentPlayer}
     * on the corresponding {@link Tile}
     * @param x
     * @param y
     * @throws InvalidMoveException The player chose an incorrect position
     */
    public void move (int x, int y) throws InvalidMoveException {
        Tile t = board.getTile(x,y);
        if( t != null && currentPlayer.move(board,selectedWorker,t)){
            List<ModelUpdate> updates = currentPlayer.getModelUpdates();

            for(int i=0;i<updates.size();i++) {
                Pair<Integer,Integer> from = updates.get(i).getModifiedTiles().get(0);
                Pair<Integer,Integer> to = updates.get(i).getModifiedTiles().get(1);

                notify(new MoveEvent(from.getFirst(),from.getSecond(),to.getFirst(),to.getSecond(),updates.get(i).getWorker().getPlayer().getUsername()));
            }
            return;

            //notify(new MoveEvent(startTile.getX(),startTile.getY(),t.getX(),t.getY(),currentPlayer.getUsername()));
        }
        else throw new InvalidMoveException();
    }

    /**
     * The {@link Match#currentPlayer} build with the {@link Match#selectedWorker}
     * on the corresponding {@link Tile}
     * @param x
     * @param y
     * @throws InvalidBuildException The player chose an incorrect tile
     */
    public void build(int x, int y) throws InvalidBuildException {
        Tile t = board.getTile(x,y);
        if(t != null && currentPlayer.build(board,selectedWorker,t)){
            List<ModelUpdate> updates = currentPlayer.getModelUpdates();

            for(int i=0;i<updates.size();i++) {
                Pair<Integer,Integer> builtTile = updates.get(i).getModifiedTiles().get(0);

                notify(new BuildEvent(updates.get(i).getWorker().getPlayer().getUsername(),userAction,builtTile.getFirst(),builtTile.getSecond()));
            }
            return;
        }
        else throw new InvalidBuildException();
    }

    /**
     * Called at the beginning of a match to keep track of all the available divinities.
     * The divinities's names are loaded from an xml file and they are put in a map with the corresponding class.
     * @param divinities Map of divinities and their class
     */
    public void setDivinityMap(Map<String,Divinity> divinities) {
        this.divinities = divinities;
    }

    /**
     * Sets a player's divinity assigning the divinity to a player, retrieving the divinity's class from the map.
     * @param divinityName Name of the divinity to be loaded
     * @throws InvalidDivinitySelectionEvent divinity not found
     */
    public void loadPlayerDivinity(String divinityName) throws InvalidDivinitySelectionEvent {
        if(!divinities.keySet().contains(divinityName)) throw new InvalidDivinitySelectionEvent();
        currentPlayer.setDivinity(divinities.get(divinityName));
    }


    /**
     * Sets a player's divinity calling another method to load it from the map of divinities. Then it advances to the next
     * player.
     * @param divName name of the divinity chosen by the player
     * @throws InvalidDivinitySelectionEvent thrown if no such divinity is found
     */
    public void divinityInitialization(String divName) throws InvalidDivinitySelectionEvent {
        loadPlayerDivinity(divName);

        int index = players.indexOf(currentPlayer) + 1;
        if(index==players.size()) currentPlayer = players.get(0);
        else {
            currentPlayer = players.get(index);
        }
    }

    /**
     * Positions a player's workers on the board. The turn passes to the next player until the player is reached and after
     * their selection, the game begins.
     * @param x1 Worker's coordinates
     * @param y1 Worker's coordinates
     * @param x2 Worker's coordinates
     * @param y2 Worker's coordinates
     * @throws WorkerBadPlacementException The player tried to position a worker on another one
     */
    public void workerPlacementInitialization(int x1, int y1, int x2, int y2) throws WorkerBadPlacementException {
        placeWorkers(x1,y1,x2,y2);

        notify(new WorkerPlacementEvent(currentPlayer.getUsername(),x1,y1,x2,y2));

        int index = players.indexOf(currentPlayer) + 1;
        if(index==players.size()) index=0;

        if(index==this.startPlayer) {
            startNextTurn();
        }
        else currentPlayer=players.get(index);
    }


    /**
     * List of all divinities
     * @return list of divinities names
     */
    public List<String> getAllDivinities() {
        return new ArrayList<>(divinities.keySet());
    }

    /**
     * Returns the list of all divinities' powers
     * @return list of strings
     */
    public List<String> getAllDivinitiesDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        for(Divinity d : divinities.values()) {
            descriptions.add(d.getDescription());
        }
        return descriptions;
    }

    /**
     * It gives a list of players' names
     * @return list of username
     */
    public List<String> getPlayersUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for(Player p : players) {
            usernames.add(p.getUsername());
        }
        return usernames;
    }

    /**
     * It gives the list of players' colors
     * @return
     */
    public List<Color> getPlayersColors() {
        ArrayList<Color> colors = new ArrayList<>();
        for(Player p : players) {
            colors.add(p.getColor());
        }
        return colors;
    }

    public List<String> getPlayersDivinities() {
        ArrayList<String> divinities = new ArrayList<>();
        for(Player p : players) {
            divinities.add(p.getDivinity().getName());
        }
        return divinities;
    }

    public int getCurrentPlayerId() {
        return players.indexOf(currentPlayer);
    }

    /**
     * It gives a map containing the actions and a list of pair, that are the position available for an action.
     * @return map of action and list of pair of int int
     */
    public Map<Action,List<Pair<Integer,Integer>>> getPossibleActions() {
        HashMap map = new HashMap();
        List<Action> possibleActions = currentPlayer.getPossibleActions();

        for(int i=0; i<possibleActions.size(); i++) {
            Action a = possibleActions.get(i);
            List<Pair<Integer,Integer>> availableTiles = new ArrayList<>();
            List<Tile> l = new ArrayList<>();

            switch (a) {
                case MOVE: {
                    l = board.getAvailableMoveTiles(selectedWorker);
                    break;
                }
                case BUILD:
                case BUILDDOME: {
                    l = board.getAvailableBuildTiles(selectedWorker);
                    break;
                }
                case END: break;
            }

            for(int j=0; j<l.size();j++) {
                availableTiles.add(new Pair<>(l.get(j).getX(),l.get(j).getY()));

            }

            map.put(a,availableTiles);

        }

        return map;
    }

    public Pair<Integer,Integer> getSelectedWorkerCoordinates() {
        return new Pair<>(selectedWorker.getPositionOnBoard().getX(),selectedWorker.getPositionOnBoard().getY());
    }
}
