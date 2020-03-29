package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Divinity;

import java.util.List;

public class Player {
    private String username;
    private Color color;
    private Divinity divinity;
    private boolean isWinner;
    private List<Phase> possibleActions;

    public void setWinner() {
        this.isWinner = true;
    }
}
