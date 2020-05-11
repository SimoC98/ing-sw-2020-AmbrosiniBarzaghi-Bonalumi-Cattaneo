package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.util.List;

public class CLI extends UI{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    //I'm inheriting a ClientView attribute
    BoardRepresentation board;

    public CLI(ClientView clientView) {
        this.clientView = clientView;
        board = clientView.getBoard();
    }

    /*
     *  turn composition:
     *  - start of turn
     *  - selection of worker
     *  - selection of action to perform
     *  - selection of tile on which do the action
     *  - back to worker selection until turn is ended (no more action is possible)
     */

    @Override
    public void selectDivinity(List<String> divinitiesNames) {
        super.selectDivinity(divinitiesNames);
    }

    @Override
    public void placeWorkers() {
        super.placeWorkers();
    }

    @Override
    public void textMessage(String msg) {
        super.textMessage(msg);
    }

    @Override
    public void selectWorker() {
        super.selectWorker();
    }

    @Override
    public void performAction(List<Action> possibleActions) {
        super.performAction(possibleActions);
    }

    @Override
    public void startTurn() {

    }

    @Override
    public void updateBoard() {
        printBoard();
    }

    public void printBoard(){
        int [][]map = this.board.getBoard();
        for(int i=0; i<board.boardDimension; i++){

            //first line
            System.out.println("\t" + "+-------+-------+-------+-------+-------+");

            //second line and board height
            System.out.print("\t");
            for(int j=0; j<board.boardDimension; j++) {
                System.out.print("| ");
                if(map[i][j] == 4 )
                    System.out.print(ANSI_PURPLE + "D" + ANSI_RESET);
                else
                    System.out.print(ANSI_PURPLE + map[i][j] + ANSI_RESET);
                System.out.print("     ");
            }
            System.out.println("|");

            //third line and worker position
            System.out.print("\t");
            for(int j=0; j<board.boardDimension; j++) {
                System.out.print("|     ");
                Color worker = board.isThereAWorker(i, j);
                if(worker == null)
                    System.out.print(" ");
                else {
                    switch (worker) {
                        case CREAM:
                            System.out.print(ANSI_YELLOW + "W" + ANSI_RESET);
                            break;

                        case BLUE:
                            System.out.print(ANSI_BLUE + "W" + ANSI_RESET);
                            break;

                        case WHITE:
                            System.out.print(ANSI_WHITE + "W" + ANSI_RESET);
                    }
                }
                System.out.print(" ");
            }
            System.out.println("|");
            //end of third line
        }
        //last line
        System.out.println("\t" + "+-------+-------+-------+-------+-------+");
    }



}
