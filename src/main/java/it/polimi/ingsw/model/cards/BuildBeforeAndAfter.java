package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This divinity, Prometheus, is able to build before and after moving only if
 * he does not go up by one level during the same turn. If he does not move up, he has the standard options.
 * <p>
 * Prometheus require a flag to check whether he has built before moving; if so he can move and then build again.
 * We designed this divinity so that he can not be unable to move after building preliminary. To do so, we inserted
 * some extra {@code if} conditions in {@code legalBuild} so that if he will not be able to move, he will not be
 * able to build either.
 */
public class BuildBeforeAndAfter extends DivinityDecoratorWithEffects {
    private boolean hasMoved;
    private boolean hasBuiltBefore;

    public BuildBeforeAndAfter() { super(); }

    public BuildBeforeAndAfter(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * If the player builds before moving, a flag is set to remember this decision
     */
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

    /**
     * If the build before the movement will lead the worker to be blocked, such build is prevented.
     * This is the reason why an {@code ArrayList} containing the possible moves after the build is created.
     * @return {@code true} if the second build is viable, or the first build will not stall the {@link Worker}
     */
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

    /**
     * @return {@code true} when the worker is moving on a valid position before or after building
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(hasBuiltBefore==true) {
            if(selectedTile.getLevel()==1 + selectedWorker.getPositionOnBoard().getLevel()) {
                return false;
            }
        }
        return super.legalMove(selectedWorker,selectedTile);
    }

    /**
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(hasBuiltBefore && !hasMoved) {
            possibleActions.add(Action.MOVE);
        }
        super.updatePossibleActions(possibleActions);
    }

    /**
     * The {@link Action#BUILD} is added at the initial pool of possible action as stated by the divinity's effect
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        hasMoved=false;
        hasBuiltBefore = false;
        possibleActions.add(Action.BUILD);
        super.setupDivinity(possibleActions);
    }
}
