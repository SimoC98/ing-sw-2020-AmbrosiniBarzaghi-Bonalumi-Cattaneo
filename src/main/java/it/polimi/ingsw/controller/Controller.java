package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.XMLparser.XMLParserUtility;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.serverView.ServerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The controller is the bridge between the network and the model. It observes the clients' events and communicates
 * with them thanks to the server.
 */
public class Controller implements Observer<ClientEvent> {

    private Model model;
    private List<ServerView> playersInGame;
    private List<String> playersUsernames;
    private int currentPlayerId;

    private List<ServerView> loserPlayers;

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
        loserPlayers = new ArrayList<>();
    }


    /**
     * Verifies that the action extracted from the clientEvent is valid on the model; if it is not, then it notifies the client.
     * If the selection is not valid, it throws an exception and notifies the client. Called when {@link it.polimi.ingsw.events.clientToServer.WorkerSelectionQuestionEvent}
     * is received
     * @param action {@link Action} chosen by the client.
     * @param x x coordinate of the action to perform with the selected worker.
     * @param y y coordinate of the action to perform with the selected worker.
     */
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

    /**
     * Manages the {@link it.polimi.ingsw.events.clientToServer.WorkerSelectionQuestionEvent} of the current playing client.
     * If the selection is not valid, it throws an exception and notifies the client.
     * @param x x coordinate of the chosen worker
     * @param y y coordinate of the chosen worker
     */
    public void handleSelectionWorker(int x, int y) {
        try {
            model.selectWorker(x,y);
            nextActionHandler();
        } catch (InvalidWorkerSelectionException e) {
            playersInGame.get(currentPlayerId).invalidWorkerSelection(x,y);
        }
    }


    /**
     * Manages the move to perform with the selected worker on the board, after receiving the corresponding event.
     * If the move is not valid, it throws an exception and notifies the client through the server.
     * @param x x coordinate to move the worker onto
     * @param y y coordinate to move the worker onto
     */
    public void handleMove(int x, int y) {
        try {
            model.move(x,y);

            Thread.sleep(100);

            int winner = model.checkWinner();
            if(winner>=0) {
                System.out.println("vincitore");
                disconnectAll();
                return;
            }
            else nextActionHandler();
        } catch (InvalidMoveException e1) {
            playersInGame.get(currentPlayerId).invalidMove(model.getPossibleActions(),x,y);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Manages the build to perform with the selected worker on the board, after receiving the corresponding event.
     * If the move is not valid, it throws an exception and notifies the client through the server.
     * @param x x coordinate to build on
     * @param y y coordinate to build on
     */
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
            playersInGame.get(currentPlayerId).invalidBuild(model.getPossibleActions(),x,y);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Called at the end of every action. It creates a map of the available actions with the possible tiles of these.
     * If the map is not empty, it sends the player the map; otherwise it ends their turn, notifying them and starting
     * the next turn.
     */
    public void nextActionHandler() {
        Map<Action,List<Pair<Integer,Integer>>> possibleActions =  model.getPossibleActions();

        if(possibleActions.size()==0) {
            playersInGame.get(currentPlayerId).endTurn();
            handleStartNextTurn();
        }
        else {
            playersInGame.get(currentPlayerId).askAction(possibleActions);
        }
    }

    /**
     * Called at the beginning of the game or at the end of a player's turn. It move the reference in the model to the next
     * player and checks if this one has lost. If they lost, it calls {@link Controller#manageLoser()}, otherwise it tells the
     * player that it is their turn.
     */
    public void handleStartNextTurn() {
        model.startNextTurn();
        this.currentPlayerId = model.getCurrentPlayerId();

        System.out.println("\n\n CONTROLLER START TURN:");
        System.out.println(currentPlayerId);
        System.out.println(playersUsernames.get(currentPlayerId));
        System.out.println("\n\n");

        boolean isLoser = model.checkLoser();
        if(isLoser) {
            manageLoser();
        }
        else {
            playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
        }
    }

    /**
     * Called when there is a losing condition. If there are two remaining players, the last one is declared victorious.
     * The losing player is removed from all the list they were inserted into and their ping flow is interrupted (see {@link ServerView#stopPing()})
     */
    public void manageLoser() {
        if(playersInGame.size()==2) {
            System.out.println("\n\ncheck disconnecting all");
            disconnectAll();
        }
        else {
            playersInGame.get(currentPlayerId).stopPing();
            //String message = "User " + playersUsernames.get(currentPlayerId) + " has been disconnected. You remain in " + playersInGame.size();
            loserPlayers.add(playersInGame.get(currentPlayerId));
            playersInGame.remove(playersInGame.get(currentPlayerId));
            playersUsernames.remove(playersUsernames.get(currentPlayerId));
            this.currentPlayerId = model.getCurrentPlayerId();
            playersInGame.get(currentPlayerId).startTurn(playersUsernames.get(currentPlayerId));
        }
    }

    /**
     * Handles the disconnection of a player and disconnects the others. Caused by {@link it.polimi.ingsw.events.clientToServer.DisconnectionEvent}
     */
    public void disconnectAll() {
        for(ServerView s : playersInGame) {
            s.disconnect();
        }

        for(ServerView s : loserPlayers) {
            s.disconnect();
        }
    }


    /*public void handleUnexpectedDisconnection(String playerName) {
        model.setLoser(playerName);
    }*/


    //todo: è usato questo metodo???
    /**
     * Handles the disconnection of a player and disconnects the others
     * @param player Player disconnecting
     */
    public void handleDisconnection(String player) {
        playersInGame.stream().forEach(x -> x.playerDisconnection(player));
        disconnectAll();
    }


    /**
     * Called after {@link it.polimi.ingsw.events.clientToServer.DivinitiesInGameSelectionEvent} is received and the map containing
     * the divinities is no longer of size zero (the user correctly picked the divinities).
     * It retrieves the information of the divinities through {@link XMLParserUtility}, it sends to every client the details
     * regaring the game (usernames, colors for each player, divinities to choose and their description).
     * Eventually it sends the starting player a {@link it.polimi.ingsw.events.serverToClient.DivinityInitializationEvent}
     * @param gameDivinities List of divinities equal in number to the number of players
     * @param startPlayer Username of the player that will begin.
     */
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


    /**
     * Maps the divinity chosen by the player and the player on the model. It updated the list of the available divinities
     * removing the specified divinity; if the list reaches the size of one, the remaining divinity is paired with the
     * remaining player and the game skips to the worker placement after informing the players' with the others' choice
     * of the divinity. See {@link it.polimi.ingsw.events.serverToClient.DivinitiesSetupEvent}
     * Called when a {@link it.polimi.ingsw.events.clientToServer.DivinitySelectionEvent} is received.
     * @param divinity Name of the divinity chosen by a player
     */
    public void handleDivinityInitialization(String divinity) {
        currentPlayerId = model.getCurrentPlayerId();
        System.out.println(playersUsernames.get(currentPlayerId));

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

    /**
     * Places a player's worker on the {@link it.polimi.ingsw.model.Board}. If the player places the workers wrongly, an
     * exception is thrown and they have to choose again.
     * After each player placed their workers the game start (see {@link Match#workerPlacementInitialization(int, int, int, int)}
     * Called when a {@link it.polimi.ingsw.events.clientToServer.WorkerPlacementSelectionEvent} is received
     * @param x1 first x coordinate
     * @param y1 first y coordinate
     * @param x2 second x coordinate
     * @param y2 second y coordinate
     */
    public void handleWorkerPlacementInitialization(int x1, int y1, int x2, int y2) {
        currentPlayerId = model.getCurrentPlayerId();

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
            playersInGame.get(currentPlayerId).invalidWorkerPlacement();
        }
    }

    /**
     * Extracts the information from the {@link ClientEvent} which will call a method on the controller.
     * @param event Event from the client.
     */
    @Override
    public void update(ClientEvent event) {
        //if(!(event instanceof VCEvent)) throw new RuntimeException("Wrong event type");
        event.handleEvent(this);
    }

}
