package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Tile [][] board;

    public Board(){
        board = new Tile[5][5];
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                board[i][j] = new Tile(i,j);
            }
        }
    }

    public Tile getTile(int x, int y){
        if(x>=0 && y>= 0 && x<5 && y<5)
            return board[x][y];
        return null;
    }

        /**Code similar to the one from https://stackoverflow.com/questions/2035522/get-adjacent-elements-in-a-two-dimensional-array
         *
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

    public void removePlayerWorkers(Player player){
     for(Worker w : player.getWorkers()){
         w.getPositionOnBoard().free();
     }
    }
}
