package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;

import java.util.List;

/**
 * Parent class for the divinities that can apply an effect on the other players, modifying their move, build or winning
 * condition.
 */
public class SetEffectOnOpponent extends DivinityDecoratorWithEffects {
    protected boolean setEffect;

    public SetEffectOnOpponent() {
    }

    public SetEffectOnOpponent(Divinity divinity) {
        super(divinity);
    }

    /**
     * Initially set the divinity to not set the effect on the othersl
     * @param possibleActions List of actions to be modified
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        setEffect = false;
        super.setupDivinity(possibleActions);
    }

    /**
     * Sets the flag to true when it meets a certain condition.
     * @return true when a condition is met
     */
    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        if(setEffect) return true;
        return super.hasSetEffectOnOpponentWorkers();
    }
}
