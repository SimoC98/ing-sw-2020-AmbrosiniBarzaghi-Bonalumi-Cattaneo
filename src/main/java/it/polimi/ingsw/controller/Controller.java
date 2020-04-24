package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.viewToController.VCEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;

import javax.swing.text.View;
import java.util.HashSet;
import java.util.List;

public class Controller implements Observer<VCEvent> {
    private Match model;
    private View users;
    private int currentPlayerId;

    public Controller(Match model, View user) {
        this.model = model;
        this.users = user;
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
            //view.show("sjdchue");
            //view.askmovedncje
        }
    }

    public void handleSelectionWorker(int x, int y) {
        try {
            model.selectWorker(x,y);
            //
        } catch (InvalidWorkerSelectionException e) {
            //send error to current user and ask again
        }
    }

    public void handleMove(int x, int y) {
        try {
            model.move(x,y);
            //check winner or ask next move
        } catch (InvalidMoveException e) {
            //send error to current user and ask again
        }
    }

    public void handleBuild(int x, int y) {
        try {
            model.build(x,y);
            //check winner or ask next move
        } catch (InvalidBuildException e) {
            //send error to current user and ask again
        }
    }

    public void nextActionHandler() {
        HashSet<Action> possibleActions =  model.getCurrentPlayer().getPossibleActions();
        if(possibleActions.size()==0) {
            //send message end turn
            //startNewTurn();
        }
        else {
            //ask next action to user
        }
    }

    public void handleStartNextTurn() {
        //
    }


    @Override
    public void update(VCEvent event) {
        //if(!(event instanceof VCEvent)) throw new RuntimeException("Wrong event type");
        event.handleEvent(this);
    }
}
