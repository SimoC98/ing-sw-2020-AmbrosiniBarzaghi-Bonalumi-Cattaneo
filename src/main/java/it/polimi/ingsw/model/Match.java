package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.model.exceptions.*;


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
 * and to communicate to other players the changes.
 */
public class Match extends Observable<ServerEvent> {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;
    private static Action userAction; //soluzione momentanea
    private Worker selectedWorker;
    private Map<String,Divinity> divinities;

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
        currentPlayer = players.get(0);
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
     */
    public static Action getUserAction() {
        return userAction;
    }


    /**
     * Method used at the end of a player's turn
     * to initialize the next player's one
     */
    public void startNextTurn(){
        for(Player p : players) {
            if(!p.equals(currentPlayer)) {
                if(p.getDivinity().hasSetEffectOnOpponentWorkers()) currentPlayer.setDivinity(p.getDivinity().getDivinity());
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
                if(p.getDivinity().hasSetEffectOnOpponentWorkers()) p.getDivinity().setEffectOnOpponentWorkers(currentPlayer);
            }
        }

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
        if (board.getTile(x,y).isOccupied() && board.getTile(x, y).getWorker().getPlayer().equals(currentPlayer) && board.getAvailableMoveTiles(board.getTile(x,y).getWorker()).size()>0)
            selectedWorker = board.getTile(x, y).getWorker();
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
/*
    /**
     * List of possible {@link Tile}s for the current {@link Player}
     * to move onto with his selected worker, when he chooses {@link Action#MOVE}
     * @param selectedWorker {@link Worker} that is going to move
     * @return The list is created checking that each of the {@link Match#selectedWorker}'s adjacent tiles are free to move onto; such conditions are verified through the call of {@code legalMove} on {@link Player}
     */
    /*public List<Tile> getAvailableMoveTiles(Worker selectedWorker){
        List<Tile> list = new ArrayList<>();
        list = board.getAdjacentTiles(selectedWorker.getPositionOnBoard());
        List<Tile> ret = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            Tile t = list.get(i);
            if (selectedWorker.getPlayer().getDivinity().legalMove(selectedWorker,t)==true) {
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * List of possible {@link Tile}s for the current {@link Player}
     * to build on with his selected worker, when he chooses {@link Action#MOVE}
     * @param selectedWorker {@link Worker} that is going to build
     * @return The list is created checking that each of the {@link Match#selectedWorker}'s adjacent tiles are free to build on; such conditions are verified through the call of {@code legalBuild} on {@link Player}
     */
    /*public List<Tile> getAvailableBuildTiles(Worker selectedWorker){
        List<Tile> list;
        list = board.getAdjacentTiles(selectedWorker.getPositionOnBoard());
        List<Tile> ret = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            Tile t = list.get(i);
            if (selectedWorker.getPlayer().getDivinity().legalBuild(selectedWorker,t)==true) {
                ret.add(t);
            }
        }
        return ret;
    }*/

    /**
     * @return The winning {@link Player}
     */
    public Player findWinner(){
        for(Player p : players) {
            if (p.isWinner()) return p;
        }
         return null;
    }

    public int checkWinner() {
        Player winner = findWinner();
        if(winner==null) return -1;
        else return players.indexOf(winner);
    }

    /**
     * This method checks if a player has lost; if so that player is removed from
     * {@link Match#players}, his workers are removed from the {@link Board} and the other players are notified.
     * If only one {@link Player} is left, he is declared the winner
     * @return Returns {@code true} if a player lost after eliminating him
     */
    public Boolean checkLoser(){
        Player loser = currentPlayer;
        for (Worker w : currentPlayer.getWorkers()) {
            if(board.getAvailableMoveTiles(w).size() > 0) return false;
        }
        board.removePlayerWorkers(currentPlayer);
        startNextTurn();
        players.remove(loser);
        if (players.size() == 1)
            currentPlayer.setWinner();
        //remove loser user form observer list
        //notify all players
        return true;
    }

    //happens when disconnected
    public void setLoser(String playerName){
        for(Player player : players){
            if(player.getUsername().equals(playerName)){
                board.removePlayerWorkers(player);
            }
        }
    }

    /**
     * @param action one of the possible actions from  the enumeration {@link Action} i.e. {@link Action#BUILD}, {@link Action#BUILDDOME}, {@link Action#MOVE} or {@link Action#END}
     * @throws InvalidActionException The action is not present among the current player's actions
     */
    public void setAction(Action action) throws InvalidActionException{
        if (currentPlayer.getPossibleActions().contains(action)) userAction = action;
        else throw new InvalidActionException();
    }


    public void setUserAction(Action action) {
        userAction = action;
    }

    /**
     *Moves the {@link Match#selectedWorker} of the {@link Match#currentPlayer}
     *on the corresponding {@link Tile}
     * @throws InvalidMoveException The player chose an incorrect position
     */
    public void move (int x, int y) throws InvalidMoveException {
        Tile t = board.getTile(x,y);
        if( t != null && currentPlayer.move(board,selectedWorker,t)){
            //notify view
        }
        else throw new InvalidMoveException();
    }

    /**
     *The {@link Match#currentPlayer} build with the {@link Match#selectedWorker}
     *on the corresponding {@link Tile}
     * @throws InvalidBuildException The player chose an incorrect tile
     */
    public void build(int x, int y) throws InvalidBuildException{
        Tile t = board.getTile(x,y);
        if(t != null && currentPlayer.build(board,selectedWorker,t)){
            //notify view
        }
        else throw new InvalidBuildException();
    }

    /**
     * Called at the beginning of a match to load the player's
     * selected divinity from an xml file
     * @param divinities Divinity name as {@code String}
     * @return Returns {@code true} if the operation was successful
     */
    public void setDivinityMap(Map<String,Divinity> divinities) {
        this.divinities = divinities;
    }

    public void loadPlayerDivinity(String divinityName) {
        currentPlayer.setDivinity(divinities.get(divinityName));
    }

    /**
     * Method to be called once per player at the beginning of the match
     * to place his workers and to choose a divinity
     */
    public void playerInitialization(int x1, int y1, int x2, int y2, String divName) throws WorkerBadPlacementException {
        placeWorkers(x1,y1,x2,y2);
        loadPlayerDivinity(divName);
        int index = players.indexOf(currentPlayer) + 1;
        if(index==players.size()) startNextTurn();
        else {
            currentPlayer = players.get(index);
        }
    }

    public int getCurrentPlayerId() {
        return players.indexOf(currentPlayer);
    }
}
