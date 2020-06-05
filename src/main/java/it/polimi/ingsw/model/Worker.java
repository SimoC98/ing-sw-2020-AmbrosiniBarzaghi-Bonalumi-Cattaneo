package it.polimi.ingsw.model;


import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

public class Worker {
    private Tile positionOnBoard;
    private final Player player;

    /**
     * Constructor to initialize a worker, mainly used in tests
     */
    public Worker() {
        this.positionOnBoard = null;
        this.player = null;
    }

    public Worker(Player player){
        this.positionOnBoard = null;
        this.player = player;
    }

    /**
     * Constructor to initially place a worker on a given {@link Tile}.
     * <p>
     * The constructor should be called at the start of a game
     * when the {@link Player} calls {@link Player#addWorker(Tile)}
     * to place his workers
     */
    public Worker(Tile initialPosition, Player player) {
        this.positionOnBoard = initialPosition;
        positionOnBoard.setWorker(this);
        this.player = player;
    }

    /**
     * Moves the worker on a different {@link Tile} if possible.
     * If the worker performs a winning movement, his owner is set winner.
     * @param t {@link Tile} to move the worker onto
     * @throws InvalidMoveException Thrown if the selection is not valid
     */
    public void move(Tile t) throws InvalidMoveException {
        if(!legalMove(t)) {
            throw new InvalidMoveException();
        }

//        if(isWinner(t)) {
//            player.setWinner();
//        }
        positionOnBoard.free();

        t.setWorker(this);

        positionOnBoard = t;
    }

    /**
     * @param t {@link Tile} to check the possible move
     * @return Returns {@code true} if the move is possible: the tile must be free, not with a dome, not with a level difference of +2, adjacent and different from the one the worker is occupying
     */
   public boolean legalMove(Tile t){
        if(t.isDome() || t.isOccupied() || t.getLevel()-positionOnBoard.getLevel()>1 || t == this.positionOnBoard || !positionOnBoard.isAdjacent(t.getX(), t.getY())) return false;
        return true;
   }

    /**
     * Builds with the worker on a {@link Tile}. If it reaches level 3, it builds a dome.
     * @param t {@link Tile} to build on
     */
    public void build (Tile t)  {
        /*if(!legalBuild(t)) {
            throw new InvalidBuildException();
        }*/
        t.increaseLevel();
    }

    /**
     * @param t {@link Tile} to check the possible build
     * @return Returns {@code true} if the build is possible: the tile must be unoccupied, without a dome and adjacent
     */
    public boolean legalBuild(Tile t){
        if(t.isDome() || t.isOccupied() || !positionOnBoard.isAdjacent(t.getX(),t.getY())) return false;
        else return true;
    }

    /**
     * @return Returns the {@link Tile} the worker is occupying
     */
    public Tile getPositionOnBoard() {
        return this.positionOnBoard;
    }

    /**
     * Places the worker on the selected tile
     */
    public void setPositionOnBoard(Tile t) {
        this.positionOnBoard = t;
    }

    /**
     * @return Returns the worker's owner
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the movement on the selected tile is a winning condition
     * @param t {@link Tile} the worker is going to move onto
     * @return {@code true} if the basic winning condition is verified: the player must move up from level 2 to level 3
     */
    public boolean isWinner(Tile t) {
        if(positionOnBoard.getLevel()==2 && t.getLevel()==3) {
            return true;
        }
        else return false;
    }


}

