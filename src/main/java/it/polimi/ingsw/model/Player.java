package it.polimi.ingsw.model;

import it.polimi.ingsw.model.update.ModelUpdate;
import it.polimi.ingsw.model.update.MoveUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to manage a player, saving his username, his workers' color and
 * his chosen divinity; the class also contains the list of his workers
 * and  a set of possible actions changing during the game.
 * <p>
 * It is remarkable that we unanimously decided that different players can
 * have the same divinity
 */
public class Player {
    private String username;
    private Color color;
    private Divinity divinity;
    private boolean isWinner;
    private List<Action> possibleActions;
    private List<Worker> workers;

    private List<ModelUpdate> updates;

    /**
     * Constructor of the player, given his username and chosen a {@link Color}.
     * All his other attributes are instantiated and set to {@code null}
     * @param username chosen by the user
     * @param color assigned by the match
     */
    public Player(String username, Color color) {
        this.username = username;
        this.color = color;
        workers = new ArrayList<>();
        divinity = null;
        possibleActions = new ArrayList<>();
        isWinner = false;

        updates = new ArrayList<>();
    }

    /**
     * Method to be called through {@link Match#placeWorkers(int, int, int, int)} at the beginning of a game
     * @param initialTile {@link Tile} to place one worker onto
     */
    public void addWorker(Tile initialTile) {
        workers.add(new Worker(initialTile,this));
    }

    /**
     * States that the player is the winner
     */
    public void setWinner() {
        this.isWinner = true;
    }

    /**
     * Flag to check if the player won
     * @return {@code true} if the player is the winner
     */
    public boolean isWinner() {
        return isWinner;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Assigns a divinity to a player. It is invoked by
     * passing the divinity's name.
     * @param newDivinity
     */
    public void setDivinity(Divinity newDivinity) {
        divinity = newDivinity;
    }


    public Divinity getDivinity() {
        return divinity;
    }

    /**
     * List of player's workers
     * @return Returns an {@code ArrayList} containing the workers
     */
    public List<Worker> getWorkers() { return new ArrayList<>(workers); }

    /**
     * List of actions a player can choose from
     * @return Returns a {@code HashSet} containing the player's next possible actions, i.e. {@link Action#MOVE}, {@link Action#BUILD}, {@link Action#BUILDDOME} and {@link Action#END}
     */
    public ArrayList<Action> getPossibleActions() { return new ArrayList<>(possibleActions); }

    public Color getColor() { return color; }

    /**
     * Moves one worker on a tile through the invocation of {@code move}
     * on the player's divinity in order to follow the pattern Decorator.
     * <p>
     * In addition it manages the next possible actions: if the move
     * is possible, in first place, it clears the list from actions in order
     * to respect the game logic; then it adds the possibility to build as in the
     * basic Santorini's rules and it adds other possible actions deriving from a divinity's effects
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return {@code true} whether the move was successful
     */
    public boolean move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(divinity.legalMove(board,selectedWorker,selectedTile)) {
            possibleActions.clear();
            updates.clear();

            updates = divinity.move(board,selectedWorker,selectedTile);

            possibleActions.add(Action.BUILD);
            divinity.updatePossibleActions(possibleActions);

            if(possibleActions.contains(Action.MOVE)) {
                List<Tile> l = board.getAvailableMoveTiles(selectedWorker);
                if(l.size()==0) possibleActions.remove(Action.MOVE);
            }

            return true;
        }
        else return false;
    }

    /**
     * Builds with a worker on a tile through the invocation of {@code build}
     * on the player's divinity in order to follow the pattern Decorator.
     * <p>
     * In addition, it manages the next possible actions: if the build
     * is possible, in first place, it clears the list from actions in order
     * to respect the game logic; then it adds other possible actions deriving from a divinity's effects
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return {@code true} whether the build was successful
     */
    public boolean build(Board board,Worker selectedWorker, Tile selectedTile) {
        if(divinity.legalBuild(board,selectedWorker,selectedTile)) {
            possibleActions.clear();
            updates.clear();

            updates = divinity.build(board,selectedWorker,selectedTile);
            divinity.updatePossibleActions(possibleActions);

            if(possibleActions.contains(Action.BUILD)) {
                List<Tile> l = board.getAvailableBuildTiles(selectedWorker);
                if(l.size()==0) possibleActions.remove(Action.BUILD);
            }
            if(possibleActions.size()==1 && possibleActions.contains(Action.END)) possibleActions.clear();


            return true;
        }
        else return false;
    }

    /**
     * Method called at the start of each turn to setup
     * a player's possible actions, adding {@link Action#MOVE}
     * and eventual others resulting from a divinity's properties
     */
    public void startOfTurn(){
        updates.clear();
        possibleActions.clear();
        possibleActions.add(Action.MOVE);
        divinity.setupDivinity(possibleActions);
    }

    public List<ModelUpdate> getMoveUpdates() {
        return updates;
    }
}
