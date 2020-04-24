package it.polimi.ingsw.model.manager;


import it.polimi.ingsw.model.*;

public class MoveManager extends ActionManager {
    private Player player;
    private Worker worker;
    private Tile tile;

    public MoveManager(Match match, int x, int y) {
        super(match);
        player = match.getCurrentPlayer();
        worker = match.getSelectedWorker();
        tile = match.getBoard().getTile(x,y);
    }

    public boolean start() {
        boolean ret = player.move(worker,tile);
        super.clearMatch();
        return ret;
    }
}
