package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

/**
 * Zeus can build below himself if he chooses the tile he is occupying.
 */
public class BuildUnderYourself extends DivinityDecoratorWithEffects {

    public BuildUnderYourself(Divinity divinity) {
        super(divinity);
    }

    public BuildUnderYourself() {
    }

    /**
     * If the selected worker is on a tile with a level under the third, they can choose the tile they are onto so that
     * they can build under themselves going up. As always reaching the third level through this action does't grant a
     * victory.
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose build is verified
     * @param selectedTile {@link Tile} to check
     * @return true if the build is legal
     */
    @Override
    public boolean legalBuild(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()<3 && selectedTile.getX()==selectedWorker.getPositionOnBoard().getX() && selectedTile.getY()==selectedWorker.getPositionOnBoard().getY()) return true;

        else return super.legalBuild(board, selectedWorker, selectedTile);
    }
}
