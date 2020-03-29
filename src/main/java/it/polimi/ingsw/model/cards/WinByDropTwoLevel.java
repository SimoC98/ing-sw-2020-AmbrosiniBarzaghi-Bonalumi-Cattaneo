package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class WinByDropTwoLevel extends AdditionalWinCondition {

    public WinByDropTwoLevel(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    protected boolean isWinner(Worker selectedWorker, Tile selectedTile) {
        int levelDifference = selectedWorker.getPositionOnBoard().getLevel() - selectedTile.getLevel();
        if(levelDifference>=2) return true;
        else return false;
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(isWinner(selectedWorker,selectedTile)==true) {
            selectedWorker.getPlayer().setWinner();
        }
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        super.build(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(selectedWorker, selectedTile);
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }




}
