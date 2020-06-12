package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.ArrayList;
import java.util.List;


/**
 * Additional layer other divinities are wrapped into if Hypnus is in game.
 * <p>
 * At the start of a player's turn, if one of the worker is on a higher level than the others, the first cannot be selected
 */
public class BlockSelectionWhenHigher extends DivinityDecoratorWithEffects {

    public boolean hasMoved;

    public BlockSelectionWhenHigher(){super();}

    public BlockSelectionWhenHigher(Divinity divinity){ super(divinity); }

    /**
     * List of workers different from the one in parameters
     * @param selectedWorker
     * @return
     */
    public List<Worker> otherWorkers(Worker selectedWorker){
        List<Worker> list = new ArrayList<>();
        for (Worker w :selectedWorker.getPlayer().getWorkers())
            if(!w.equals(selectedWorker)) {
                list.add(w);
            }
        return list;
    }

    /**
     * The move is overwritten so that once a player's has moved, the blocking condition is removed.
     * <p>
     * This "trick" works because a worker is affected by the condition only on the first move so that it cannot be selected;
     * for divinities that can move more than once, the effects is thus removed after moving a first time.
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {

        hasMoved = true;
        return super.move(board, selectedWorker, selectedTile);
    }

    /**
     * The legal move is overwritten so that if a worker is higher than the others, it cannot be selected.
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public boolean legalMove(Board board, Worker selectedWorker, Tile selectedTile){
        if (!hasMoved) {
            for (Worker otherWorker : otherWorkers(selectedWorker)) {
                if (selectedWorker.getPositionOnBoard().getLevel() > otherWorker.getPositionOnBoard().getLevel()) {
                    return false;
                }
            }
        }
        return super.legalMove(board, selectedWorker, selectedTile);
    }

    /**
     * During the setup the flag is set to false so that the condition will be applied when selecting a worker.
     * @param possibleActions
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        hasMoved=false;
        super.setupDivinity(possibleActions);
    }
}
