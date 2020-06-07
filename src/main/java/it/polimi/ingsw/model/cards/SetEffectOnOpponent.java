package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;

import java.util.List;

public class SetEffectOnOpponent extends DivinityDecoratorWithEffects {
    protected boolean setEffect;

    public SetEffectOnOpponent() {
    }

    public SetEffectOnOpponent(Divinity divinity) {
        super(divinity);
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        setEffect = false;
        super.setupDivinity(possibleActions);
    }

    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        if(setEffect) return true;
        return super.hasSetEffectOnOpponentWorkers();
    }
}
