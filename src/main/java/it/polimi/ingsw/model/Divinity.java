package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * Interface implemented by all the divinities to
 * realize the Decorator Pattern; as such it is
 * passed as a parameter in the classes using a divinity
 * <p>
 * The choice of this Pattern is due to how we thought to
 * implement the game: the {@link it.polimi.ingsw.model.Player} can perform  the base actions
 * of Santorini calling the corresponding action on a {@link StandardDivinity} that calls the basic action on the {@link Worker};
 * each divinity is then "decorated" with other special moves and builds deriving from a specific divinity
 */
public interface Divinity {


    /**
     * Moves the selectedWorker on the selectedTile of the Board
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to move onto
     * @return List of tiles modified during the move.
     */
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) ;

    /**
     * Builds with the selectedWorker on the selectedTile of the Board
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to build on
     * @return List of tiles modified during the build.
     */
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile);

    /**
     * Called to check if the move is legal
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose move is verified
     * @param selectedTile {@link Tile} to check
     * @return true if the move is possible
     */
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile);

    /**
     * Called to check if the build is legal
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose build is verified
     * @param selectedTile {@link Tile} to check
     * @return true if the build is possible
     */
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile);

    /**
     * Returns this instance of divinity
     * @return
     */
    public Divinity getDivinity();

    /**
     * Called to update the list of {@link Action} a player can choose from, after they performed another action
     * @param possibleActions List of actions to modify
     */
    public void updatePossibleActions(List<Action> possibleActions);

    /**
     * Initializes the actions a player can perform, deriving from the active effects of the divinity (and the others)
     * @param possibleActions List of actions to be modified
     */
    public void setupDivinity(List<Action> possibleActions);

    /**
     * Checks if the worker is performing a winning action
     * @param selectedWorker current worker performing an action
     * @param selectedTile {@link Tile} the worker is going to perform the action on
     * @return true if the action will lead a player to win
     */
    public boolean isWinner(Worker selectedWorker, Tile selectedTile);

    /**
     * Used by divinities that apply their power on other divinities
     * @return true if the effect has to be applied
     */
    public boolean hasSetEffectOnOpponentWorkers();

    /**
     * Called when a divinity has to apply their effect to the other players. The specified player's divinity is
     * decorated with another layer to override their methods.
     * @param opponentPlayer player affected by the divinity
     */
    public void setEffectOnOpponentWorkers(Player opponentPlayer);

    public String getDescription();

    public String getName();
}
