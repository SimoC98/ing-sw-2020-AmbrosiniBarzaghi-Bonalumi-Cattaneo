package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

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
    public void move(Worker selectedWorker, Tile selectedTile) ;
    public void build(Worker selectedWorker, Tile selectedTile);
    public boolean legalMove(Worker selectedWorker, Tile selectedTile);
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile);
    public Divinity getDivinity();
    public Set<Action> updatePossibleActions(Set<Action> possibleActions);
    public void setupDivinity(Set<Action> possibleActions);
}
