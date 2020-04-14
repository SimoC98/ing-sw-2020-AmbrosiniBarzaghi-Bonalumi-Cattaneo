package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.model.cards.Divinity;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Match extends Observable {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;
    private Action userAction; //soluzione momentanea
    private Worker selectedWorker;

    public Match() {  }

    public Match(Board board) {
        this.board = board;
    }

    public Match(List<String> users) {
        userAction = null;
        selectedWorker = null;
        currentPlayer = null;
        this.board = new Board();
        players = new ArrayList<>();
        Color[] colors = Color.values();
        for(int i=0; i < users.size();i++) {
            Player newPlayer = new Player(users.get(i),colors[i]);
            players.add(newPlayer);
        }
    }


    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Worker getSelectedWorker(){return selectedWorker;}

    public Board getBoard() {
        return board;
    }

    public Action getUserAction() {
        return userAction;
    }

    public void setUserAction(Action userAction) {
        this.userAction = userAction;
    }

    public void startNextTurn(){
        if(currentPlayer!=null) {
            int playerIndex = players.indexOf(currentPlayer);
            if (playerIndex + 1>= players.size()) {
                playerIndex = 0;
            }else {
                playerIndex = playerIndex + 1;
            }
            currentPlayer = players.get(playerIndex);
        }
        else {
            currentPlayer = players.get(0);
        }
        currentPlayer.startOfTurn();
    }

    public void selectWorker(int x, int y) throws InvalidWorkerSelectionException{
        if (board.getTile(x, y).isOccupied() && board.getTile(x, y).getWorker().getPlayer().equals(currentPlayer) && getAvailableMoveTiles(board.getTile(x,y).getWorker()).size()>0)
            selectedWorker = board.getTile(x, y).getWorker();
        else throw new InvalidWorkerSelectionException();
    }

    public void placeWorkers(int x1, int y1, int x2, int y2) throws WorkerBadPlacementException{
        if(board.getTile(x1,y1) != null && board.getTile(x2,y2) != null && !board.getTile(x1,y1).equals(board.getTile(x2,y2)) && !board.getTile(x1,y1).isOccupied() && !board.getTile(x2,y2).isOccupied()) {
            currentPlayer.addWorker(board.getTile(x1, y1));
            currentPlayer.addWorker(board.getTile(x2, y2));
        }
        else throw new WorkerBadPlacementException();
    }

    public List<Tile> getAvailableMoveTiles(Worker selectedWorker){
        List<Tile> list = new ArrayList<>();
        list = board.getAdjacentTiles(selectedWorker.getPositionOnBoard());
        List<Tile> ret = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            Tile t = list.get(i);
            if (selectedWorker.getPlayer().getDivinity().legalMove(selectedWorker,t)==true) {
                ret.add(t);
            }
        }
        return ret;
    }

    public List<Tile> getAvailableBuildTiles(Worker selectedWorker){
        List<Tile> list = new ArrayList<>();
        list = board.getAdjacentTiles(selectedWorker.getPositionOnBoard());
        List<Tile> ret = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            Tile t = list.get(i);
            if (selectedWorker.getPlayer().getDivinity().legalBuild(selectedWorker,t)==true) {
                ret.add(t);
            }
        }
        return ret;
    }

    public Player findWinner(){
        for(Player p : players) {
            if (p.isWinner()) return p;
        }
         return null;
    }

    public Boolean checkLoser(){
        Player loser = currentPlayer;
        for (Worker w : currentPlayer.getWorkers()) {
            if(getAvailableMoveTiles(w).size() > 0) return false;
        }
        board.removePlayerWorkers(currentPlayer);
        startNextTurn();
        players.remove(loser);
        if (players.size() == 1)
            currentPlayer.setWinner();
        //remove loser user form observer list
        //notify all players
        return true;
    }

    public void setAction(Action action) throws InvalidActionException{
        if (currentPlayer.getPossibleActions().contains(action)) userAction = action;
        else throw new InvalidActionException();
    }

    public void move (int x, int y) throws InvalidMoveException {
        Tile t = board.getTile(x,y);
        if( t != null && currentPlayer.move(selectedWorker, t)){
            //notify view & controller
        }
        else throw new InvalidMoveException();
    }

    public void build(int x, int y) throws InvalidBuildException{
        Tile t = board.getTile(x,y);
        if(t != null && currentPlayer.build(selectedWorker,t)){
            //notify view & controller
        }
        else throw new InvalidBuildException();
    }

    public boolean loadDivinity(String divinityName) {
        Divinity divinity = XMLDecoderUtility.loadDivinity(divinityName);
        if(divinity != null) {
            currentPlayer.setDivinity(divinity);
            return true;
        }
        return false;
    }
}
