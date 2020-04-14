package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuildBeforeAndAfter extends DivinityDecoratorWithEffects {
    private boolean hasMoved;
    private boolean hasBuiltBefore;

    public BuildBeforeAndAfter() { super(); }

    public BuildBeforeAndAfter(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(hasMoved==false) {
            hasBuiltBefore=true;
        }
        super.build(selectedWorker,selectedTile);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        hasMoved = true;
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        if(!hasMoved && selectedTile.getLevel()>=selectedWorker.getPositionOnBoard().getLevel()) {
            List<Tile> l = new ArrayList<>( Game.getMatch().getAvailableMoveTiles(selectedWorker));
            List<Tile> ret = new ArrayList<>();
            for(int i=0;i<l.size();i++) {
                Tile t = l.get(i);
                if(!t.isOccupied() && !t.isDome() && t.getLevel()<=selectedWorker.getPositionOnBoard().getLevel() && !t.equals(selectedTile)) ret.add(t);
            }
            if(ret.size()==0) return false;
        }
        return super.legalBuild(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(hasBuiltBefore==true) {
            if(selectedTile.getLevel()==1 + selectedWorker.getPositionOnBoard().getLevel()) {
                return false;
            }
        }
        return super.legalMove(selectedWorker,selectedTile);
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        if(hasBuiltBefore && !hasMoved) {
            possibleActions.add(Action.MOVE);
        }
        return super.updatePossibleActions(possibleActions);
    }

    /**
     * is added to the @param possibleActions BUILD: the current player can choose to build as first action
     */
    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        hasMoved=false;
        hasBuiltBefore = false;
        possibleActions.add(Action.BUILD);
        super.setupDivinity(possibleActions);
    }
}
