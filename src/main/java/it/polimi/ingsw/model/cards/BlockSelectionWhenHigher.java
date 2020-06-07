package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.ArrayList;
import java.util.List;

public class BlockSelectionWhenHigher extends DivinityDecoratorWithEffects {

    public boolean hasMoved;

    public BlockSelectionWhenHigher(){super();}

    public BlockSelectionWhenHigher(Divinity divinity){ super(divinity); }

    public List<Worker> otherWorkers(Worker selectedWorker){
        List<Worker> list = new ArrayList<>();
        for (Worker w :selectedWorker.getPlayer().getWorkers())
            if(!w.equals(selectedWorker)) {
                list.add(w);
            }
        return list;
    }

    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {

        hasMoved = true;
        return super.move(board, selectedWorker, selectedTile);
    }

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

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        hasMoved=false;
        super.setupDivinity(possibleActions);
    }
}
