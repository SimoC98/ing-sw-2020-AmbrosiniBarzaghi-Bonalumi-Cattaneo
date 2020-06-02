package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

public class BuildUnderItself extends DivinityDecoratorWithEffects {

    public BuildUnderItself(Divinity divinity) {
        super(divinity);
    }

    public BuildUnderItself() {
    }

    @Override
    public boolean legalBuild(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()<3 && selectedTile.getX()==selectedWorker.getPositionOnBoard().getX() && selectedTile.getY()==selectedWorker.getPositionOnBoard().getY()) return true;

        else return super.legalBuild(board, selectedWorker, selectedTile);
    }
}
