package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage all the {@link Tile}s and to access them.
 * It is also responsible for the removal of a player
 */
public class Board {

    private final int dim=5;
    private Tile [][] board;

    /**
     * Constructor of the board. It allocates the 25 tiles.
     */
    public Board(){
        board = new Tile[dim][dim];
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                board[i][j] = new Tile(i,j);
            }
        }
    }

    /**
     * @return returns a {@link Tile} given its coordinates
     */
    public Tile getTile(int x, int y){
        if(x>=0 && y>= 0 && x<5 && y<5)
            return board[x][y];
        return null;
    }

        /**
         *Code similar to the one at <a href:"https://stackoverflow.com/questions/2035522/get-adjacent-elements-in-a-two-dimensional-array" target ="_blank"></a>
         */
    public List<Tile> getAdjacentTiles (Tile t){
        ArrayList<Tile> list = new ArrayList<Tile>();
        int x = t.getX();
        int y = t.getY();
        for (int dx = (x > 0 ? -1 : 0) ; dx <= (x < 4 ? 1 : 0); ++dx)
        {
            for (int dy = (y > 0 ? -1 : 0); dy <= (y < 4 ? 1 : 0); ++dy)
            {
                if (dx != 0 || dy != 0)
                {
                    list.add(getTile(x +dx, y+dy));
                }
            }
        }
        return list;
    }

    /**
     *Removes a {@link Player}'s workers from the board.
     */
    public void removePlayerWorkers(Player player){
     for(Worker w : player.getWorkers()){
         w.getPositionOnBoard().free();
        }
    }



    /**
     * List of possible {@link Tile}s for the current {@link Player}
     * to move onto with his selected worker, when he chooses {@link Action#MOVE}
     * @param selectedWorker {@link Worker} that is going to move
     * @return The list is created checking that each of the {@link }'s adjacent tiles are free to move onto; such conditions are verified through the call of {@code legalMove} on {@link Player}
     */
    public List<Tile> getAvailableMoveTiles(Worker selectedWorker){
        List<Tile> list = new ArrayList<>();
        list = getAdjacentTiles(selectedWorker.getPositionOnBoard());
        List<Tile> ret = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            Tile t = list.get(i);
            if (selectedWorker.getPlayer().getDivinity().legalMove(this,selectedWorker,t)==true) {
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * List of possible {@link Tile}s for the current {@link Player}
     * to build on with his selected worker, when he chooses {@link Action#MOVE}
     * @param selectedWorker {@link Worker} that is going to build
     * @return The list is created checking that each of the {}'s adjacent tiles are free to build on; such conditions are verified through the call of {@code legalBuild} on {@link Player}
     */
    public List<Tile> getAvailableBuildTiles(Worker selectedWorker){
        List<Tile> list;
        list = getAdjacentTiles(selectedWorker.getPositionOnBoard());
        List<Tile> ret = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            Tile t = list.get(i);
            if (selectedWorker.getPlayer().getDivinity().legalBuild(this,selectedWorker,t)==true) {
                ret.add(t);
            }
        }
        return ret;
    }


}
