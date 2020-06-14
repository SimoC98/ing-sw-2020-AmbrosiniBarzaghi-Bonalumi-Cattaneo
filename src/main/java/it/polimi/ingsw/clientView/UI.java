package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Action;

import java.util.List;

public interface UI {

    //metodi to have
    public void start();

    public void login();

    public void failedLogin(List<String> usernames);


    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players);

    public void selectDivinity(List<String> divinitiesNames);

    public void placeWorkers();
//    public void selectDivinityAndPlaceWorkers(List<String> divinityNames) {}

    //public void updateBoard();

    public void textMessage(String msg);

    public void startTurn();

    public void selectWorker();

    public void performAction(List<Action> possibleActions);

    public void loser(String username);

    public void winner(String username);

    public void playersDivinities();

    public void playerDisconnection(String username);

    public void inLobby();

    public void lobbyFull();

    public void endTurn();

    public void startGame();

    //errors
    public void invalidMove(List<Action> possibleActions, int wrongX, int wrongY);

    public void invalidBuild(List<Action> possibleActions, int wrongX, int wrongY);

    public void invalidWorkerPlacement();

    public void invalidWorkerSelection(int wrongX, int wrongY);

    //updates from model
    public void moveUpdate(String player,int xFrom,int yFrom,int xTo,int yTo);

    public void buildUpdate(String player, int x, int y);

    public void workerPlacementUpdate(String player,int x1,int y1,int x2,int y2);




}
