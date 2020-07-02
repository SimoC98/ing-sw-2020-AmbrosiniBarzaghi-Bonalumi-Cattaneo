package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that is to be "decorated" with the effects
 * of a defined divinity. It can be decorated and undecorated
 * more than one time.
 * <p>
 * StandardDivinity contains all the information and methods of a generic divinity: its name,
 * heading, description and number; it also has the methods corresponding to base actions that
 * are called through the invocation of the matching function on a worker.
 */
public class StandardDivinity implements Divinity {

    private String name;
    private String heading;
    private String description;
    private int number;

    /**
     * Constructor used for testing
     */
    public StandardDivinity(){
        this.name = null;
        this.heading = null;
        this.description = null;
        this.number = 0;
    }

    /**
     * Constructor used to characterise a divinity upon filling its information fields
     * @param name name of the divinity
     * @param heading heading (subtitle) of the divinity
     * @param description description of the divinity's power
     * @param number index of the divinity as in the game
     */
    public StandardDivinity(String name, String heading, String description, int number) {
        this.name = name;
        this.heading = heading;
        this.description = description;
        this.number = number;
    }

    /**
     * Calls the method {@code move} on the {@link Worker} passed as parameter, given a {@link Tile}.
     * @param board Reference to {@link Board} so that it can be accessed and modified
     * @param selectedWorker {@link Worker} that will perform an action
     * @param selectedTile {@link Tile} the action will be performed on
     * @return List of tiles to update on the UI representations. {@link ModelUpdate}
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        List<ModelUpdate> ret = new ArrayList<>();
        //List<Tile> modifiedTiles = new ArrayList<>();
        List<Pair<Integer,Integer>> modifiedTiles = new ArrayList<>();

        modifiedTiles.add(new Pair<>(selectedWorker.getPositionOnBoard().getX(),selectedWorker.getPositionOnBoard().getY()));
        modifiedTiles.add(new Pair<>(selectedTile.getX(),selectedTile.getY()));
        ret.add(new ModelUpdate(Action.MOVE,selectedWorker,modifiedTiles));

        selectedWorker.move(selectedTile);

        return ret;
    }

    /**
     * Calls the method {@code build} on the {@link Worker} passed as parameter, given a {@link Tile}.
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to build on
     * @return
     */
    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        List<ModelUpdate> ret = new ArrayList<>();

        List<Pair<Integer,Integer>> modifiedTiles = new ArrayList<>();
        modifiedTiles.add(new Pair<Integer, Integer>(selectedTile.getX(),selectedTile.getY()));

        ModelUpdate update = new ModelUpdate(Action.BUILD,selectedWorker,modifiedTiles);
        ret.add(update);

        selectedWorker.build(selectedTile);

        return ret;
    }

    /**
     * Checks if the player is performing a winning move with the current effects
     * @param selectedWorker current worker performing an action
     * @param selectedTile {@link Tile} the worker is going to perform the action on
     * @return
     */
    @Override
    public boolean isWinner(Worker selectedWorker, Tile selectedTile){
        return selectedWorker.isWinner(selectedTile);
    }

    /**
     * Checks if the selected {@link Worker} is able to move on the specified {@link Tile}
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose move is verified
     * @param selectedTile {@link Tile} to check
     * @return {@code true} if the move is legal
     */
    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalMove(selectedTile);
    }

    /**
     * Checks if the selected {@link Worker} is able to build on the specified {@link Tile}
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose build is verified
     * @param selectedTile {@link Tile} to check
     * @return {@code true} if the build is legal
     */
    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalBuild(selectedTile);
    }



    /**
     * @return this instance of StandardDivinity
     */
    @Override
    public Divinity getDivinity() {
        return this;
    }

    /**
     * It returns a set of possible actions, updated with
     * the effects of the divinity.
     * <p>
     * The standard divinity does not update the set
     * @param possibleActions Available actions of a player
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        return;
    }

    /**
     * Function used to initialize the parameters of
     * a divinity in able to update correctly the set
     * of possible actions that {@link StandardDivinity#updatePossibleActions(List)} will return
     * @param possibleActions list of {@link Action} to be modified
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        return;
    }

    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        return false;
    }

    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        return;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeading() {
        return heading;
    }

    /**
     * Text of a divinity powers
     * @return The description of a divinity's effects
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
