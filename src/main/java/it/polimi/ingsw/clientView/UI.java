package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Action;

import java.util.List;

public abstract class UI {
    ClientView clientView;

    //metodi to have
    public void start() {}
    public void login() {}
    public void failedLogin(List<String> usernames) {}

    public void selectPlayersNumber() {}
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber) {}

//    public void selectDivinity(List<String> divinitiesNames) {}
//    public void placeWorkers() {}
    public void selectDivinityAndPlaceWorkers(List<String> divinityNames) {}

    public void updateBoard() {}
    public void textMessage(String msg) {}

    public void startTurn() {}
    public void selectWorker() {}
    public void performAction(List<Action> possibleActions) {}

    public void loser(String username) {}
    public void winner(String username) {}
}
