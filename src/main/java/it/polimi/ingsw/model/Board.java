package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Tile [][] board;

    public Board(){
        board = new Tile[5][5];
    }

    public Tile getTile(int x, int y){
        return board[x][y];
    }

    public List<Tile> getAdjacentTiles (Tile t){
        ArrayList<Tile> list = new ArrayList<Tile>();
        int x = t.getX();
        int y = t.getY();
        /**Code similar to the one from https://stackoverflow.com/questions/2035522/get-adjacent-elements-in-a-two-dimensional-array
         *
         */
        for (int dx = (x > 0 ? -1 : 0) ; dx <= (x < 5 ? 1 : 0); ++dx)
        {
            for (int dy = (y > 0 ? -1 : 0); dy <= (y < 5 ? 1 : 0); ++dy)
            {
                if (dx != 0 || dy != 0)
                {
                    list.add(getTile(x +dx, y+dy));
                }
            }
        }
        return list;
    }
}
