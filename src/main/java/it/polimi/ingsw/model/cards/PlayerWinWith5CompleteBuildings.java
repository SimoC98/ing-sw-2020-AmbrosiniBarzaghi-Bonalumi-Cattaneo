package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

public class PlayerWinWith5CompleteBuildings extends SetEffectOnOpponent{
    private Player winnerPlayer=null;
    private Board board=null;

    public PlayerWinWith5CompleteBuildings() {
    }

    public PlayerWinWith5CompleteBuildings(Divinity divinity) {
        super(divinity);
    }

    private int checkCompleteTowersNumber(Board b) {
        int count=0;

        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                if(b.getTile(i,j).getLevel()==3 && b.getTile(i,j).isDome()) count++;
            }
        }
        return count;
    }

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
