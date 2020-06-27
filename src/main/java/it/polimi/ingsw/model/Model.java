package it.polimi.ingsw.model;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.exceptions.*;

import java.util.List;
import java.util.Map;

/**
 * Interface implemented by the {@link Match}
 */
public interface Model {

    public void setStartPlayer(String startPlayer);

    public void startNextTurn();

    public void selectWorker(int x, int y) throws InvalidWorkerSelectionException;

    public void setDivinityMap(Map<String,Divinity> divinities);

    public int checkWinner();

    public Boolean checkLoser();

    public void setAction(Action action) throws InvalidActionException;

    public void move (int x, int y) throws InvalidMoveException;

    public void build(int x, int y) throws InvalidBuildException;

    public void divinityInitialization(String divName) throws InvalidDivinitySelectionEvent;

    public void workerPlacementInitialization(int x1, int y1, int x2, int y2) throws WorkerBadPlacementException;

    public List<String> getAllDivinities();

    public List<String> getAllDivinitiesDescriptions();

    public List<String> getPlayersUsernames();

    public List<Color> getPlayersColors();

    public List<String> getPlayersDivinities();

    public int getCurrentPlayerId();

    public Map<Action,List<Pair<Integer,Integer>>> getPossibleActions();
}
