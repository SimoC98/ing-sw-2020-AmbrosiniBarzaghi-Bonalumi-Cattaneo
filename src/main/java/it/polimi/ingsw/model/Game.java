package it.polimi.ingsw.model;


/**
 * Class meant to create a first instance
 * of {@link Match}
 * <p>
 * The instance of a match is created
 * {@code static} as to be accessed freely
 * in any part of the code
 */
public class Game {
    private static Match match;

    public Game(Match match) {
        this.match = match;
    }

    public static Match getMatch() {
        return match;
    }
}
