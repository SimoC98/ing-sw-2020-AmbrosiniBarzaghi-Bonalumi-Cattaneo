package it.polimi.ingsw.model.update;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class MoveUpdate extends ModelUpdate{
    public MoveUpdate(Worker worker, List<Pair<Integer,Integer>> modifiedTiles) {
        super(Action.MOVE, worker, modifiedTiles);
    }
}
