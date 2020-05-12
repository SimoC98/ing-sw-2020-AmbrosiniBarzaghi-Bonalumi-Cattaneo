package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.lang.reflect.Array;
import java.util.*;

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
    PlayerRepresentation player;
    Scanner scanner;

    public CLI(ClientView clientView) {
        this.clientView = clientView;
        board = clientView.getBoard();
        player = board.getPlayersMap().get(clientView.getUsername());
        scanner = new Scanner(System.in);
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
    public void selectPlayersNumber() {
        System.out.println("You're the first logged user, so you have to choose the number of players for this match.");
        System.out.println("You can choose between 2 and 3 players.");

        int playersNumber = 1;
        do{
            System.out.print("Choose: ");
            playersNumber = scanner.nextInt();
        }while(playersNumber < 2 || playersNumber > 3);

        clientView.playersNumberQuestion(playersNumber);
    }

    @Override
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber) {
        System.out.println("You're the last user logged in, so you must choose the divinities among which players will choose theirs.");
        System.out.println("You must choose exactly " + playersNumber + " cards.");
        System.out.println("\n");


        List<String> playableDivinities = new ArrayList<>();
        int selection=0;

        while(playableDivinities.size() != playersNumber) {
            for(int i=0;i<divinitiesNames.size();i++) {
                System.out.println(i+1 + ") " + divinitiesNames.get(i) + "\n" + divinitiesDescriptions.get(i));
            }
            do {
                System.out.print("Select #" + (playableDivinities.size()+1) + " divinity: ");
                selection = scanner.nextInt();
            } while (selection <= 0 || selection > divinitiesNames.size());
            playableDivinities.add(divinitiesNames.get(selection-1));
            divinitiesNames.remove(selection-1);
            divinitiesDescriptions.remove(selection-1);
        }

        for(String div : playableDivinities)
            System.out.println(div);

        clientView.playableDivinitiesSelection(playableDivinities);
    }

    @Override
    public void selectDivinity(List<String> divinitiesNames) {
        while(true) {
            System.out.println("You have to select you divinity. Choose from:");
            for (int i = 0; i < divinitiesNames.size(); i++)
                System.out.println("\t" + (i + 1) + ") " + divinitiesNames.get(i));
            System.out.println("\t" + (divinitiesNames.size() + 1) + ") " + "See divinities descriptions");

            int selection = 0;
            do {
                System.out.print("\nChoose: ");
                selection = scanner.nextInt();
            } while (selection <= 0 || selection > divinitiesNames.size() + 1);

            if (selection == divinitiesNames.size() + 1)  {
                printDescriptions();
            }else{
                board.getPlayersMap().get(clientView.getUsername()).setDivinity(divinitiesNames.get(selection));    //sets player's divinity
                return;
            }
        }
    }

    @Override
    public void placeWorkers() {
        int x1, y1, x2, y2;
        List<String> coordinates = Arrays.asList("A1", "A2", "A3", "A4", "A5",
                "B1", "B2", "B3", "B4", "B5",
                "C1", "C2", "C3", "C4", "C5",
                "D1", "D2", "D3", "D4", "D5",
                "E1", "E2", "E3", "E4", "E5");

        System.out.println("You have to choose your workers' initial position.");
        System.out.println("You must type the coordinate (E.G. A5, D2, E4) in which you want to place you workers, one at a time.");
        System.out.println("You cannot place workers on occupied tiles.");

        System.out.println();
        printBoard();
        System.out.println();

        String selection;
        do{
            System.out.print("\tChoose: ");
            selection = scanner.nextLine().toUpperCase();
        }while(!coordinates.contains(selection));
        x1 = Integer.parseInt(selection.substring(0,0));
        y1 = Integer.parseInt(selection.substring(1));

        player.addWorker(x1,y1);

        System.out.println();
        printBoard();
        System.out.println();

        do{
            System.out.print("\tChoose: ");
            selection = scanner.nextLine().toUpperCase();
        }while(!coordinates.contains(selection));
        x2 = Integer.parseInt(selection.substring(0,0));
        y2 = Integer.parseInt(selection.substring(1));

        player.addWorker(x2,y2);
    }

    @Override
    public void textMessage(String msg) {
        //TODO
    }

    @Override
    public void selectWorker() {
        //TODO
    }

    @Override
    public void performAction(List<Action> possibleActions) {
        //TODO
    }

    @Override
    public void startTurn() {
        System.out.println("It's your turn, select a worker:");
        int i = 0;
        for (Pair<Integer, Integer> worker : player.getWorkers())
            System.out.println("\t" + (i+1) + ")\t" + worker.getFirst() + ", " + worker.getSecond());

        int selection = 0;
        do {
            System.out.print("\nSelect: ");
            selection = scanner.nextInt();
        } while (selection <= 0 || selection > player.getWorkers().size());

        Pair<Integer, Integer> selectedWorker = player.getWorkers().get(selection-1);
        clientView.selectWorkerQuestion(selectedWorker.getFirst(), selectedWorker.getSecond());
    }

    @Override
    public void updateBoard() {
        printBoard();
    }

    private void printDescriptions() {
        Map<String, String> divDescriptions = board.getDivinities();
        List<String> divNames = new ArrayList<>(divDescriptions.keySet());

        System.out.println("\nHere you can read the descriptions of the divinities use in this match.");

        while(true) {
            System.out.println("Choose the divinity which you want to read the description:");

            System.out.println("\t0) Quit");
            for (int i = 0; i < divNames.size(); i++)
                System.out.println("\t" + (i + 1) + ") " + divNames.get(i));

            int selection = -1;
            do {
                System.out.print("Choose: ");
                selection = scanner.nextInt();
            } while (selection < 0 || selection > divNames.size() + 1);

            if (selection == 0)
                return;
            else
                System.out.println("\t" + divDescriptions.get(divNames.get(selection)) + "\n");
        }
    }

    public void printBoard(){
        int [][]map = this.board.getBoard();
        char yCoordinate = 'A';

        //coordinates line
        System.out.println("\t" + "    1       2       3       4       5    ");

        for(int i=0; i<board.boardDimension; i++){
            //first line
            System.out.println("\t" + "+-------+-------+-------+-------+-------+");

            //second line and board height
            System.out.print("\t");
            for(int j=0; j<board.boardDimension; j++) {
                System.out.print("|");
                if(map[i][j] == 4 )
                    System.out.print(ANSI_PURPLE + "D" + ANSI_RESET);
                else
                    System.out.print(ANSI_PURPLE + map[i][j] + ANSI_RESET);
                System.out.print("      ");
            }
            System.out.println("|");

            //third line and worker position
            System.out.print((yCoordinate++) + "\t");
            for(int j=0; j<board.boardDimension; j++) {
                System.out.print("|   ");
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
                System.out.print("   ");
            }
            System.out.println("|");

            //fourth line
            System.out.println("\t" + "|       |       |       |       |       |");

        }
        //fifth and last line
        System.out.println("\t" + "+-------+-------+-------+-------+-------+");
    }



}
