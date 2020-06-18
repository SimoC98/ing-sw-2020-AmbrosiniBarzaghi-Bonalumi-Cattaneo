package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.XMLparser.XMLParserUtility;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.serverView.ServerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller implements Observer<ClientEvent> {

    private Match model;
    private List<ServerView> playersInGame;
    private List<String> playersUsernames;
    private int currentPlayerId;

    private int startPlayer=-1;

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
                    playersInGame.get(currentPlayerId).endTurn();
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
            //playersInGame.get(currentPlayerId).showMessage("error");
            //playersInGame.get(currentPlayerId).selectWorker();
            playersInGame.get(currentPlayerId).invalidWorkerSelection(x,y);
        }
    }


    //TODO: riguardare winner condition
    public void handleMove(int x, int y) {
        try {
            model.move(x,y);

            //momentaneo --> perchè possibleActionEvent arriva prima a volte??
            Thread.sleep(100);

            int winner = model.checkWinner();
            if(winner>=0) {
                System.out.println("vincitore");
                disconnectAll();
                return;
            }
            else nextActionHandler();
        } catch (InvalidMoveException e1) {
            //playersInGame.get(currentPlayerId).showMessage("error");
            //nextActionHandler();
            playersInGame.get(currentPlayerId).invalidMove(model.getPossibleActions(),x,y);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    //TODO: riguardare winner condition
    public void handleBuild(int x, int y) {
        try {
            model.build(x,y);

            Thread.sleep(100);

            int winner = model.checkWinner();
            if(winner>=0) {
                disconnectAll();
                return;
            }
            else nextActionHandler();
        } catch (InvalidBuildException e1) {
            //playersInGame.get(currentPlayerId).showMessage("error");
            //nextActionHandler();
            playersInGame.get(currentPlayerId).invalidBuild(model.getPossibleActions(),x,y);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public void nextActionHandler() {
        Map<Action,List<Pair<Integer,Integer>>> possibleActions =  model.getPossibleActions();

        if(possibleActions.size()==0) {
            //playersInGame.get(currentPlayerId).showMessage("your turn is ended");
            playersInGame.get(currentPlayerId).endTurn();
            handleStartNextTurn();
        }
        else {
            playersInGame.get(currentPlayerId).askAction(possibleActions);
        }
    }

    public void handleStartNextTurn() {
        model.startNextTurn();
        this.currentPlayerId = model.getCurrentPlayerId();

        boolean isLoser = model.checkLoser();
        if(isLoser) {
            manageLoser();
        }
        else {
            playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
        }
    }

    public void manageLoser() {
        if(playersInGame.size()==2) {
            System.out.println("\n\ncheck disconnecting all");
            disconnectAll();
        }
        else {
            // playersInGame.get(currentPlayerId).disconnect();
            playersInGame.get(currentPlayerId).stopPing();
            //String message = "User " + playersUsernames.get(currentPlayerId) + " has been disconnected. You remain in " + playersInGame.size();
            playersInGame.remove(playersInGame.get(currentPlayerId));
            playersUsernames.remove(playersUsernames.get(currentPlayerId));
            //sendMessageToAll(message);
            this.currentPlayerId = model.getCurrentPlayerId();
            playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
        }
    }

    public void disconnectAll() {
        for(ServerView s : playersInGame) {
            s.disconnect();
        }
    }


    /*public void handleUnexpectedDisconnection(String playerName) {
        model.setLoser(playerName);
    }*/

    //handle disconnection of client by ui (es button "exit" in gui)
    public void handleDisconnection(String player) {
        playersInGame.stream().forEach(x -> x.playerDisconnection(player));
        disconnectAll();
    }


    public void startGame(List<String> gameDivinities, String startPlayer) {
        List<String> allDivinities = model.getAllDivinities();
        List<String> allDivinitiesDescription = model.getAllDivinitiesDescriptions();
        if(gameDivinities.size()==0) {
            Map<String, Divinity> divinityMap =  XMLParserUtility.getDivinities();
            model.setDivinityMap(divinityMap);
            playersInGame.get(playersInGame.size()-1).chooseDivinitiesInGame(model.getAllDivinities(),model.getAllDivinitiesDescriptions(),playersInGame.size(),model.getPlayersUsernames());
        }
        else {
            List<String> descriptions = new ArrayList<>();
            for(int i=0; i<gameDivinities.size();i++) {
                descriptions.add(allDivinitiesDescription.get(allDivinities.indexOf(gameDivinities.get(i))));
            }
            for(ServerView s : playersInGame) {
                s.sendGameSetupInfo(model.getPlayersUsernames(),model.getPlayersColors(),gameDivinities,descriptions);
            }

            this.startPlayer = playersUsernames.indexOf(startPlayer);
            this.gameDivinities = gameDivinities;
            model.setStartPlayer(startPlayer);

            /*
            * send to every player:
            * -list of players in game
            * -list of player colors
            * -divinities in game (random order)
            * -divinities descriptions
            * */
            playersInGame.get(this.startPlayer).sendDivinityInitialization(this.gameDivinities);
        }
    }

    public int getStartPlayer() {
        return startPlayer;
    }

    public List<ServerView> getPlayersInGame() {
        return playersInGame;
    }

    public List<String> getPlayersUsernames() {
        return playersUsernames;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setStartPlayer(String player) {
        this.startPlayer = playersUsernames.indexOf(player);
    }


    public void handleDivinityInitialization(String divinity) {
        //boolean endInitialization=false;
        //if(gameDivinities.size()==1) endInitialization=true;

        currentPlayerId = model.getCurrentPlayerId();

        try {
            model.divinityInitialization(divinity);
            currentPlayerId = model.getCurrentPlayerId();

            gameDivinities.remove(divinity);

            if(gameDivinities.size()==1) {
                model.divinityInitialization(gameDivinities.get(0));

                currentPlayerId = model.getCurrentPlayerId();

                for(ServerView s : playersInGame) {
                    //s.startGame(model.getPlayersUsernames(), model.getPlayersColors(), model.getPlayersDivinities(), model.getPlayersDivinitiesDescriptions());
                    s.sendDivinitiesSetup(model.getPlayersUsernames(),model.getPlayersDivinities());
                }
                playersInGame.get(currentPlayerId).sendWorkerInitialization();

            }
            else {
                playersInGame.get(currentPlayerId).sendDivinityInitialization(new ArrayList<>(gameDivinities));
            }

        } catch (InvalidDivinitySelectionEvent e) {
            //if chosen divinity is not available another request to choose one is sent to the same client

            playersInGame.get(currentPlayerId).showMessage("error");
            playersInGame.get(currentPlayerId).sendDivinityInitialization(new ArrayList<>(gameDivinities));
        }

    }

    public void handleWorkerPlacementInitialization(int x1, int y1, int x2, int y2) {
        currentPlayerId = model.getCurrentPlayerId();

        //if(currentPlayerId+1==playersInGame.size()) endInitialization = true;

        try {
            model.workerPlacementInitialization(x1,y1,x2,y2);
            currentPlayerId = model.getCurrentPlayerId();

            if(model.getCurrentPlayerId() == this.startPlayer) {
                boolean loser = model.checkLoser();
                if(loser) manageLoser();
                else playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
                //playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
            }
            else {
                playersInGame.get(currentPlayerId).sendWorkerInitialization();
            }

        } catch (WorkerBadPlacementException e) {
            //playersInGame.get(currentPlayerId).showMessage("error");
            //playersInGame.get(currentPlayerId).sendWorkerInitialization();
            playersInGame.get(currentPlayerId).invalidWorkerPlacement();
        }
    }

    @Override
    public void update(ClientEvent event) {
        //if(!(event instanceof VCEvent)) throw new RuntimeException("Wrong event type");
        event.handleEvent(this);
    }

}
