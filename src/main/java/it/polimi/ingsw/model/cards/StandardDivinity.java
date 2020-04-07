package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

import java.util.List;


/**
 * Decorator Pattern
 * implements Divinity interface and can be decorated with effects
 */
public class StandardDivinity implements Divinity {
    /**
     * features of the divinity card
     */
    private String name;
    private String heading;
    private String description;
    private int number;


    public StandardDivinity(){
        this.name = null;
        this.heading = null;
        this.description = null;
        this.number = 0;
    }

    public StandardDivinity(String name, String heading, String description, int number) {
        this.name = name;
        this.heading = heading;
        this.description = description;
        this.number = number;
    }

    /**
     * call the method move on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
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
     * call the method build on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
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
     * call the method legalMove on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalMove(selectedTile);
    }

    /**
     * call the method legalBuild on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalBuild(selectedTile);
    }



    /**
     *
     * @return this instance of StandardDivinity
     */
    @Override
    public Divinity getDivinity() {
        return this;
    }

    @Override
    public List<Action> updatePossibleActions(List<Action> possibleActions) {
        return possibleActions;
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        return;
    }

    /**
     * getter and setter for the card's features
     * @return
     */
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
