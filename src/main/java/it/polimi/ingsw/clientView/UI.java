package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Action;

import java.util.List;

public abstract class UI {
    ClientView clientView;

    //metodi to have
    public void selectDivinity(List<String> divinitiesNames) {}
    public void placeWorkers() {}

    public void updateBoard() {}
    public void textMessage(String msg) {}

    public void startTurn() {}
    public void selectWorker() {}
    public void performAction(List<Action> possibleActions) {}
}
