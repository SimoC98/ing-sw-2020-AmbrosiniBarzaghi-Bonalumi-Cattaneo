package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Divinity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private String username;
    private Color color;
    private Divinity divinity;
    private boolean isWinner;
    private Set<Action> possibleActions;
    private List<Worker> workers;

    public Player(String username, Color color) {
        this.username = username;
        this.color = color;
        workers = new ArrayList<>();
        divinity = null;
        possibleActions = new HashSet<>();
        isWinner = false;
    }

    public void addWorker(Tile initialTile) {
        workers.add(new Worker(initialTile,this));
    }

    public void setWinner() {
        this.isWinner = true;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String getUsername() {
        return username;
    }

    public void setDivinity(Divinity newDivinity) {
        divinity = newDivinity;
    }

    public Divinity getPlayerDivinity() {
        return divinity;
    }

    public boolean move(Worker selectedWorker, Tile selectedTile) {
        if(divinity.legalMove(selectedWorker,selectedTile)) {
            divinity.move(selectedWorker,selectedTile);
            //
            //
            divinity.updatePossibleActions(possibleActions);
            return true;
        }
        else return false;
    }

    public boolean build(Worker selectedWorker, Tile selectedTile) {
        if(divinity.legalBuild(selectedWorker,selectedTile)) {
            divinity.build(selectedWorker,selectedTile);
            divinity.updatePossibleActions(possibleActions);
            return true;
        }
        else return false;
    }


    
}
