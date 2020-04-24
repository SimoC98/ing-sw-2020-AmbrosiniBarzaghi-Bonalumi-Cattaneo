package it.polimi.ingsw.model.manager;

import it.polimi.ingsw.model.Match;

public class ActionManager {
    private static Match match = null;

    public ActionManager(Match match) {
        this.match = match;
    }

    public static Match getMatch() {
        return match;
    }

    public void clearMatch() {
        this.match = null;
    }
}
