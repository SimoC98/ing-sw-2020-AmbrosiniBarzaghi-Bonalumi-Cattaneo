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

    private final static String invalidMove = "ERROR! -> YOU CAN'T MOVE HERE!";
    private final static String invalidBuild = "ERROR! -> YOU CAN'T BUILD HERE!";
    //other error messages

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
            switch(action) {
                case MOVE:  {
                    handleMove(x,y);
                    break;
                }
                case BUILD:
                case BUILDDOME: {
                    handleBuild(x,y);
                    break;
                }
                case END: {
                    //send end turn message
                    handleStartNextTurn();
                    break;
                }
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

            //momentaneo --> perchè possibleActionEvent arriva prima a volte??
            Thread.sleep(100);

            int winner = model.checkWinner();
            if(winner>=0) {
                //disconnect all
                return;
            }
            else nextActionHandler();
        } catch (InvalidMoveException e1) {
            playersInGame.get(currentPlayerId).showMessage("error");
            nextActionHandler();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public void handleBuild(int x, int y) {
        try {
            model.build(x,y);

            Thread.sleep(100);

            int winner = model.checkWinner();
            if(winner>=0) {
                //disconnect all
            }
            else nextActionHandler();
        } catch (InvalidBuildException e1) {
            playersInGame.get(currentPlayerId).showMessage("error");
            nextActionHandler();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
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

    //probably not necessary
    /*public void disconnectPlayer(String playerName) {
        for(ServerView s : playersInGame) {
            if(!s.getUsername().equals(playerName)) s.playerDisconnection(playerName);
            s.disconnect();
        }
        //model.setLoser(playerName);
    }*/

    public void handleUnexpectedDisconnection(String playerName) {
        model.setLoser(playerName);
    }

    public void sendMessageToAll(String message) {
        for(ServerView s : playersInGame) {
            s.showMessage(message);
        }
    }

    //probably not necessary
    /*public void endGame(int winner) {
        String winnerUsername = model.getPlayers().get(winner).getUsername();
        //view.endGame(winnerUsername);
        exit(0);
    }*/

    public void startGame(List<String> gameDivinities) {
        List<String> allDivinities = model.getAllDivinities();
        List<String> allDivinitiesDescription = model.getAllDivinitiesDescriptions();
        if(gameDivinities.size()==0) {
            Map<String, Divinity> divinityMap =  XMLParserUtility.getDivinities();
            model.setDivinityMap(divinityMap);
            playersInGame.get(playersInGame.size()-1).chooseDivinitiesInGame(model.getAllDivinities(),model.getAllDivinitiesDescriptions(),playersInGame.size());
        }
        else {
            this.gameDivinities = gameDivinities;
            List<String> descriptions = new ArrayList<>();
            for(int i=0; i<gameDivinities.size();i++) {
                descriptions.add(allDivinitiesDescription.get(allDivinities.indexOf(gameDivinities.get(i))));
            }

            /*
            * send to every player:
            * -list of players in game
            * -list of player colors
            * -divinities in game (random order)
            * -divinities descriptions
            * */
            for(ServerView s : playersInGame) {
                s.sendGameSetupInfo(model.getPlayersUsernames(),model.getPlayersColors(),gameDivinities,descriptions);
            }
            playersInGame.get(0).chooseDivinity(this.gameDivinities);
        }
    }

    public void gameInitialization(int x1,int y1, int x2, int y2, String chosenDivinity) {
        boolean endInitialization=false;
        if(gameDivinities.size()==1) endInitialization=true;

        currentPlayerId = model.getCurrentPlayerId();

        try {
            model.playerInitialization(x1,y1,x2,y2,chosenDivinity);
            currentPlayerId = model.getCurrentPlayerId();

            gameDivinities.remove(chosenDivinity);

            if(endInitialization) {
                for(ServerView s : playersInGame) {
                    //s.startGame(model.getPlayersUsernames(), model.getPlayersColors(), model.getPlayersDivinities(), model.getPlayersDivinitiesDescriptions());
                    s.sendDivinitiesSetup(model.getPlayersUsernames(),model.getPlayersDivinities());
                }

                playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
            }
            else {
                playersInGame.get(currentPlayerId).chooseDivinity(new ArrayList<>(gameDivinities));
            }

        } catch (WorkerBadPlacementException e) {
            playersInGame.get(currentPlayerId).showMessage("error");
            playersInGame.get(currentPlayerId).chooseDivinity(new ArrayList<>(gameDivinities));
        }

    }




    @Override
    public void update(ClientEvent event) {
        //if(!(event instanceof VCEvent)) throw new RuntimeException("Wrong event type");
        event.handleEvent(this);
    }

}
