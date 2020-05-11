package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.XMLparser.XMLParserUtility;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.serverView.ServerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller implements Observer<ClientEvent> {
    private Match model;
    private List<ServerView> playersInGame;
    private List<String> playersUsernames;
    private int currentPlayerId;

    private List<String> gameDivinities;

    public Controller(Match model, List<ServerView> playersInGame) {
        this.model = model;
        this.playersInGame = playersInGame;

        for(int i=0;i<playersInGame.size();i++) {
            playersUsernames = model.getPlayersUsernames();
        }

        currentPlayerId = 0;

        gameDivinities = new ArrayList<>();
    }

    public void handleActionValidation(Action action, int x, int y) {
        try {
            model.setAction(action);
            switch(action.name()) {
                case "MOVE": handleMove(x,y);
                case "BUILD": handleBuild(x,y);
                case "BUILDDOME": handleBuild(x,y);
                case "END": handleStartNextTurn();
            }
        } catch (InvalidActionException e) {
            playersInGame.get(currentPlayerId).showMessage("error");
            nextActionHandler();
        }
    }

    public void handleSelectionWorker(int x, int y) {
        try {
            model.selectWorker(x,y);
            nextActionHandler();
        } catch (InvalidWorkerSelectionException e) {
            playersInGame.get(currentPlayerId).showMessage("error");
            playersInGame.get(currentPlayerId).selectWorker();
        }
    }

    public void handleMove(int x, int y) {
        try {
            model.move(x,y);

            int winner = model.checkWinner();
            if(winner>=0) {
                //disconnect all
                return;
            }
            else nextActionHandler();
        } catch (InvalidMoveException e) {
            playersInGame.get(currentPlayerId).showMessage("error");
            nextActionHandler();
        }
    }

    public void handleBuild(int x, int y) {
        try {
            model.build(x,y);

            int winner = model.checkWinner();
            if(winner>=0) {
                //disconnect all
            }
            else nextActionHandler();
        } catch (InvalidBuildException e) {
            playersInGame.get(currentPlayerId).showMessage("error");
            nextActionHandler();
        }
    }

    public void nextActionHandler() {
       List<Action> possibleActions =  model.getCurrentPlayer().getPossibleActions();

        if(possibleActions.size()==0) {
            playersInGame.get(currentPlayerId).showMessage("your turn is ended");
            handleStartNextTurn();
        }
        else {
            playersInGame.get(currentPlayerId).askAction(possibleActions);
        }
    }

    public void handleStartNextTurn() {
        model.startNextTurn();
        currentPlayerId = model.getCurrentPlayerId();

        boolean isLoser = model.checkLoser();
        if(isLoser) {
            if(playersInGame.size()==2) {
                disconnectAll();
            }
            else {
                playersInGame.get(currentPlayerId).disconnect();
                playersInGame.remove(playersInGame.get(currentPlayerId));
                playersUsernames.remove(playersUsernames.get(currentPlayerId));
                String message = "User " + playersUsernames.get(currentPlayerId) + " has been disconnected. You remain in " + playersInGame.size();
                sendMessageToAll(message);
                currentPlayerId = model.getCurrentPlayerId();
                playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
            }
        }
        else playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
    }

    public void disconnectAll() {
        for(ServerView s : playersInGame) {
            s.disconnect();
        }
    }

    public void disconnectPlayer(String playerName) {
        for(ServerView s : playersInGame) {
            if(!s.getUsername().equals(playerName)) s.playerDisconnection(playerName);
            s.disconnect();
        }
        //model.setLoser(playerName);
    }

    public void handleUnexpectedDisconnection(String playerName) {
        model.setLoser(playerName);
    }

    public void sendMessageToAll(String message) {
        for(ServerView s : playersInGame) {
            s.showMessage(message);
        }
    }

    /*public void endGame(int winner) {
        String winnerUsername = model.getPlayers().get(winner).getUsername();
        //view.endGame(winnerUsername);
        exit(0);
    }*/

    public void startGame(List<String> gameDivinities) {
        if(gameDivinities.size()==0) {
            Map<String, Divinity> divinityMap =  XMLParserUtility.getDivinities();
            model.setDivinityMap(divinityMap);
            playersInGame.get(playersInGame.size()-1).chooseDivinitiesInGame(model.getAllDivinities(),model.getAllDivinitiesDescriptions(),playersInGame.size());
        }
        else {
            this.gameDivinities = gameDivinities;
            playersInGame.get(0).chooseDivinity(this.gameDivinities);
        }
    }

    public void gameInitialization(int x1,int y1, int x2, int y2, String chosenDivinity) {
        boolean endInitialization=false;
        if(gameDivinities.size()==1) endInitialization=true;

        try {
            model.playerInitialization(x1,y1,x2,y2,chosenDivinity);

            gameDivinities.remove(chosenDivinity);

            if(endInitialization) {
                currentPlayerId = model.getCurrentPlayerId();

                for(ServerView s : playersInGame) {
                    s.startGame(model.getPlayersUsernames(), model.getPlayersColors(), model.getPlayersDivinities(), model.getPlayersDivinitiesDescriptions());
                }

                playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
            }
            else {
                int i = playersInGame.size() - gameDivinities.size();
                playersInGame.get(i).chooseDivinity(gameDivinities);
            }

        } catch (WorkerBadPlacementException e) {
            //
        }

    }




    @Override
    public void update(ClientEvent event) {
        //if(!(event instanceof VCEvent)) throw new RuntimeException("Wrong event type");
        event.handleEvent(this);
    }

}
