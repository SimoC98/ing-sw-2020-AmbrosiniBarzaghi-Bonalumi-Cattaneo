package it.polimi.ingsw.events.modelToView;

import java.io.Serializable;

public interface MVEvent extends Serializable {
    public void handleEvent();
}
