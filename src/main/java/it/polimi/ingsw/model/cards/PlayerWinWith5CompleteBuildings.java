package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * Chrono wins if someone in the game completes the fifth tower (level 3 plus dome).
 * The class sets a flag when there are already 4 complete towers, so that at the beginning of the turn the other divinities
 * are wrapped in a decoration that sets Chrono as the winner if they build the last dome. For further reference see {@link Match#startNextTurn()},
 * {@link CannotWinOnPerimeter} and {@link CannotSelectIfHigher}.
 */
public class PlayerWinWith5CompleteBuildings extends SetEffectOnOpponent{
    private Player winnerPlayer=null;
    private Board board=null;

    public PlayerWinWith5CompleteBuildings() {
    }

    public PlayerWinWith5CompleteBuildings(Divinity divinity) {
        super(divinity);
    }

    /**
     * Returns the number of towers, tiles with level 3 and isDome set to true.
     * @param b board to be checked
     * @return number of completed towers
     */
    private int checkCompleteTowersNumber(Board b) {
        int count=0;

       if(board!=null) {
            for(int i=0;i<5;i++) {
                for(int j=0;j<5;j++) {
                    if(b.getTile(i,j).getLevel()==3 && b.getTile(i,j).isDome()) count++;
                }
            }
        }

        return count;
    }

    /**
     * Sets the effect on opponents only when there are already 4 towers
     * @return true when there are 4 buildings
     */
    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        if(checkCompleteTowersNumber(board)==4) super.setEffect=true;
        return super.hasSetEffectOnOpponentWorkers();
    }

    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        Check5CompleteTowerWinCondition c = new Check5CompleteTowerWinCondition(opponentPlayer.getDivinity());
        c.setWinnerPlayer(winnerPlayer);
        opponentPlayer.setDivinity(c);

        super.setEffectOnOpponentWorkers(opponentPlayer);
    }

    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(winnerPlayer==null) {
            this.winnerPlayer = selectedWorker.getPlayer();
            this.board = board;
        }

        return super.move(board, selectedWorker, selectedTile);
    }
}
