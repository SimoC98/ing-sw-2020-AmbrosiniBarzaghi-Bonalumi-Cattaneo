package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class BuildDomeEverywhere extends DivinityDecoratorWithEffects {

    public BuildDomeEverywhere(Divinity divinity) {
        super(divinity);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(Game.getMatch().getUserAction().equals(Action.BUILDDOME)) {
            selectedTile.setDome();
        }
        else {
            super.build(selectedWorker,selectedTile);
        }
    }
}