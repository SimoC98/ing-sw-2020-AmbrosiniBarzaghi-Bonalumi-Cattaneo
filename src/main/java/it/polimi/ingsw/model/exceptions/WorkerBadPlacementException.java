package it.polimi.ingsw.model.exceptions;

/**
 * Thrown when a player tries to place their workers on an occupied tile
 */
public class WorkerBadPlacementException extends Exception{
    public WorkerBadPlacementException (){super();}
}
