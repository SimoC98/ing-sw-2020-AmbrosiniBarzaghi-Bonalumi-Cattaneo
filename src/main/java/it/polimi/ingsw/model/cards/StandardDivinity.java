package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

import java.util.List;
import java.util.Set;


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
     *Constructor used to characterise a divinity upon filling its information fields
     */
    public StandardDivinity(String name, String heading, String description, int number) {
        this.name = name;
        this.heading = heading;
        this.description = description;
        this.number = number;
    }

    /**
     * Calls the method {@code move} on the {@link Worker} passed as parameter, given a {@link Tile}.
     * Throws {@link InvalidMoveException} if the move was not valid
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        try {
            selectedWorker.move(selectedTile);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the method {@code build} on the {@link Worker} passed as parameter, given a {@link Tile}.
     * Throws {@link InvalidBuildException} if the build was not valid
     */
    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        try {
            selectedWorker.build(selectedTile);
        } catch (InvalidBuildException e) {
            e.printStackTrace();
        }
    }

    /**
     *Checks if the selected {@link Worker} is able to move on the specified {@link Tile}
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalMove(selectedTile);
    }

    /**
     *Checks if the selected {@link Worker} is able to build on the specified {@link Tile}
     */
    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
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
     * @return updated list of {@link Action}s
     */
    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        return possibleActions;
    }

    /**
     * Function used to initialize the parameters of
     * a divinity in able to update correctly the set
     * of possible actions that {@link StandardDivinity#updatePossibleActions(Set)} will return
     */
    @Override
    public void setupDivinity(Set<Action> possibleActions) {
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

    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
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
