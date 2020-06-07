package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

public class Check5CompleteTowerWinCondition extends DivinityDecoratorWithEffects {
    private Player winnerPlayer;

    public Check5CompleteTowerWinCondition() {
    }

    public Check5CompleteTowerWinCondition(Divinity divinity) {
        super(divinity);
    }

    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()==3) {
            winnerPlayer.setWinner();
        }
        return super.build(board, selectedWorker, selectedTile);
    }

    protected void setWinnerPlayer(Player p) {
        this.winnerPlayer = p;
    }
}
