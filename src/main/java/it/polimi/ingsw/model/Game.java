package it.polimi.ingsw.model;

public class Game {
    private static Match match;

    public Game(Match match) {
        this.match = match;
    }

    public static Match getMatch() {
        return match;
    }
}
