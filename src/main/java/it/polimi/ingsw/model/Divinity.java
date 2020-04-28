package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.StandardDivinity;

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
    public void move(Board board, Worker selectedWorker, Tile selectedTile) ;
    public void build(Board board, Worker selectedWorker, Tile selectedTile);
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile);
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile);
    public Divinity getDivinity();
    public void updatePossibleActions(List<Action> possibleActions);
    public void setupDivinity(List<Action> possibleActions);

    public boolean hasSetEffectOnOpponentWorkers();
    public void setEffectOnOpponentWorkers(Player opponentPlayer);
}
