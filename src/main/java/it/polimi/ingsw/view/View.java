package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.modelToView.ModelUpdateEvent;
import it.polimi.ingsw.events.viewToController.VCEvent;

public class View extends Observable<VCEvent> implements Observer<ModelUpdateEvent> {


    @Override
    public void update(ModelUpdateEvent event) {

    }
    //showMessage
    //askAction
    //askMove
    //askBuild
    //startTurn
    //selectWorker
    //matchInitialization
}
