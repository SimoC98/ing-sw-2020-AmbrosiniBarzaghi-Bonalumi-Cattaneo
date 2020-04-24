package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.viewToController.VCEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.view.View;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Controller implements Observer<VCEvent> {
    private Match model;
    private View view;
    private int currentPlayerId;

    public Controller(Match model, View user) {
        this.model = model;
        this.view = user;
        currentPlayerId = 0;
    }

    public void handleActionValidation(Action action, int x, int y) {
        try {
            model.setAction(action);
            switch(action.name()) {
                case "MOVE": handleMove(x,y);
                case "BUILD": handleBuild(x,y);
                case "BUILDDOME": handleBuild(x,y);
                case "END": //
            }
        } catch (InvalidActionException e) {
            view.showMessage("error");
            nextActionHandler();
        }
    }

    public void handleSelectionWorker(int x, int y) {
        try {
            model.selectWorker(x,y);
            nextActionHandler();
        } catch (InvalidWorkerSelectionException e) {
            view.showMessage("error");
            view.selectWorker();
        }
    }

    public void handleMove(int x, int y) {
        try {
            model.move(x,y);
            //check winner or ask next move
            nextActionHandler();
        } catch (InvalidMoveException e) {
            view.showMessage("error");
            nextActionHandler();
        }
    }

    public void handleBuild(int x, int y) {
        try {
            model.build(x,y);
            //check winner or ask next move
            nextActionHandler();
        } catch (InvalidBuildException e) {
            view.showMessage("error");
            nextActionHandler();
        }
    }

    public void nextActionHandler() {
        HashSet<Action> possibleActions =  model.getCurrentPlayer().getPossibleActions();

        if(possibleActions.size()==0) {
            view.showMessage("your turn is ended");
            handleStartNextTurn();
        }
        else {
            //view.askAction();
        }
    }

    public void handleStartNextTurn() {
        //
        view.startTurn(model.getCurrentPlayer().getUsername());
    }


    @Override
    public void update(VCEvent event) {
        //if(!(event instanceof VCEvent)) throw new RuntimeException("Wrong event type");
        event.handleEvent(this);
    }
}
