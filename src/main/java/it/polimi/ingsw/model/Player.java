package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Divinity;

import java.util.List;

public class Player {
    private String username;
    private Color color;
    private Divinity divinity;
    private boolean isWinner;
    private List<Phase> possibleActions;

    public Player(String username, Color color) {
        this.username = username;
        this.color = color;
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




    


    
}
