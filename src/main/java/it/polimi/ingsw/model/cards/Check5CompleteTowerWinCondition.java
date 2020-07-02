package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * Decoration to be applied to other players' divinities when Chrono is in game.
 * If a player completes a tower so that there are five towers with 3 levels and a dome, Chrono's player wins.
 */
public class Check5CompleteTowerWinCondition extends DivinityDecoratorWithEffects {
    private Player winnerPlayer;

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
