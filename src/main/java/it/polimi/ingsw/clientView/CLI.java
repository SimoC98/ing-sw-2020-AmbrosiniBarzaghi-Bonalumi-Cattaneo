package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Class for the Command Line Interface. It implements the methods of the {@link UI}. The CLI is the default option
 * for the {@link TemporaryMain}.
 */
public class CLI implements UI{

    //terminal related
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    ClientView clientView;
    BoardRepresentation board;
    Scanner scanner;

    private final Object lock = new Object();

    private int selectedWX;
    private int selectedWY;

    /**
     * Constructor passing the {@link ClientView} in order to communicate to the server. It also initializes a scanner
     * to retrieve user's input data and the board's representation.
     * @param clientView
     */
    public CLI(ClientView clientView) {
        this.clientView = clientView;
        board = clientView.getBoard();
        scanner = new Scanner(System.in);
    }

    public CLI() {
        scanner = new Scanner(System.in);
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        this.board = clientView.getBoard();
    }

    /*
     *  turn composition:
     *  - start of turn
     *  - selection of worker
     *  - selection of action to perform
     *  - selection of tile on which do the action
     *  - back to worker selection until turn is ended (no more action is possible)
     */

    /**
     * Print's Santorini as a welcome title and opens the client socket.
     */
    @Override
    public void start() {
        System.out.println("\n\nWelcome to..." + "\n");

        System.out.println(" .oooooo..o                           .                       o8o               o8o");
        System.out.println("d8P'    `Y8                         .o8                       `\"'               `\"'");
        System.out.println("Y88bo.       .oooo.   ooo. .oo.   .o888oo  .ooooo.  oooo d8b oooo  ooo. .oo.   oooo");
        System.out.println(" `\"Y8888o.  `P  )88b  `888P\"Y88b    888   d88' `88b `888\"\"8P `888  `888P\"Y88b  `888");
        System.out.println("     `\"Y88b  .oP\"888   888   888    888   888   888  888      888   888   888   888");
        System.out.println("oo     .d8P d8(  888   888   888    888 . 888   888  888      888   888   888   888");
        System.out.println("8\"\"88888P'  `Y888\"\"8o o888o o888o   \"888\" `Y8bod8P' d888b    o888o o888o o888o o888o" + "\n\n");

        clientView.startConnection();
        //this.board = clientView.getBoard();
    }

    /**
     * Attempts to log a player to a lobby with the username typed by the player (more than 3 char).
     * It calls {@link ClientView#loginQuestion(String)}
     */
    @Override
    public void login() {
        String username;

        do {
            System.out.print("Choose your username (at least 3 characters): ");
            username = scanner.nextLine();
        } while(username.length() < 3);

        clientView.loginQuestion(username);
    }

    /**
     * Called when the server informs the client that their attempt to log was refused due to the unavailability of the
     * picked username. It calls {@link ClientView#loginQuestion(String)} again
     * @param users List of already logged players
     */
    @Override
    public void failedLogin(List<String> users) {
        String username;

        System.out.println("Other users are logged in, and the username you chose is not available.");
        do {
            System.out.println("Please avoid choosing:");
            for(String user : users)
                System.out.println("\t- " + user);
            System.out.print("Choose your username (at least 3 characters): ");
            username = scanner.nextLine();
        } while(username.length()<3 && !users.contains(username));

        clientView.loginQuestion(username);
    }

    /**
     * Called if the CLI is the one of the last player. It displays the list of divinities with their description 
     * @param divinitiesNames List of all divinities
     * @param divinitiesDescriptions List of their effects
     * @param playersNumber number of players in game
     * @param players Names of the players
     */
    @Override
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players) {
        clearScreen();
        System.out.println("You're the last user logged in, so you must choose the divinities among which players will choose theirs.");
        System.out.println("You must choose exactly " + playersNumber + " cards.");
        System.out.println("\n");

        List<String> playableDivinities = new ArrayList<>();
        String input;
        int selection = 0;

        for(int i=0;i<divinitiesNames.size();i++) {
            System.out.println(i+1 + ") " + divinitiesNames.get(i) + "\n\t" + divinitiesDescriptions.get(i));
        }
        while(playableDivinities.size() != playersNumber) {
            do {
                System.out.print("Select #" + (playableDivinities.size()+1) + " divinity: ");
                input = scanner.nextLine();

                if(input.matches("[0-9]+"))
                    selection = Integer.parseInt(input);

            } while (selection <= 0 || selection > divinitiesNames.size());
            playableDivinities.add(divinitiesNames.get(selection-1));
            divinitiesNames.remove(selection-1);
            divinitiesDescriptions.remove(selection-1);
            if (playableDivinities.size() != playersNumber) {
                System.out.println("\n\n\n\n");
                for (int i = 0; i < divinitiesNames.size(); i++)
                    System.out.println((i+1) + ") " + divinitiesNames.get(i) + "\n\t" + divinitiesDescriptions.get(i));
            }
        }

        System.out.print("\n\nThe divinities you have chosen: ");
        for(String div : playableDivinities)
            System.out.print("\t" + div);

        System.out.println("\n\n");
        for(int i=0;i<players.size();i++) {
            System.out.println("(" + i + ")" + players.get(i));
        }

        int start = -1;
        do{
            System.out.println("Now choose the starter player: ");
            String in = scanner.nextLine();
            if(in.matches("[0-9]")) start = Integer.parseInt(in);
        } while(start<0||start>players.size());

        clientView.playableDivinitiesSelection(playableDivinities,players.get(start));
    }



    @Override
    public void selectDivinity(List<String> divinitiesNames) {
        clearScreen();

        String divinity = null;

        System.out.println("You have to select your divinity. Choose from:");
        for (int i = 0; i < divinitiesNames.size(); i++)
            System.out.println("\t" + (i + 1) + ") " + divinitiesNames.get(i));
        System.out.println("\t" + (divinitiesNames.size() + 1) + ") " + "See divinities descriptions");

        String input;
        int selection = 0;
        do {
            do {
                System.out.print("\nChoose: ");
                input = scanner.nextLine();
                if (input.matches("[0-9]+"))
                    selection = Integer.parseInt(input);
            } while (selection <= 0 || selection > divinitiesNames.size() + 1);

            if (selection == divinitiesNames.size() + 1) {
                printDescriptions(divinitiesNames);
                System.out.println("\n\n\n");
                System.out.println("You have to select your divinity. Choose from:");
                for (int i = 0; i < divinitiesNames.size(); i++)
                    System.out.println("\t" + (i + 1) + ") " + divinitiesNames.get(i));
                System.out.println("\t" + (divinitiesNames.size() + 1) + ") " + "See divinities descriptions");
            }
            else
                divinity = divinitiesNames.get(selection - 1);
        }while(divinity==null);

        clientView.divinitySelection(divinity);
    }

    @Override
    public void placeWorkers() {
        int x1, y1, x2, y2;

        synchronized (lock) {

            System.out.println("You have to choose your workers' initial position.");
            System.out.println("You must type the coordinate (E.G. A5, D2, E4) in which you want to place you workers, one at a time.");
            System.out.println("You cannot place workers on occupied tiles.");

            System.out.println("+----+----+----+----+----+");
            System.out.println("| A1 | A2 | A3 | A4 | A5 |");
            System.out.println("+----+----+----+----+----+");
            System.out.println("| B1 | B2 | B3 | B4 | B5 |");
            System.out.println("+----+----+----+----+----+");
            System.out.println("| C1 | C2 | C3 | C4 | C5 |");
            System.out.println("+----+----+----+----+----+");
            System.out.println("| D1 | D2 | D3 | D4 | D5 |");
            System.out.println("+----+----+----+----+----+");
            System.out.println("| E1 | E2 | E3 | E4 | E5 |");
            System.out.println("+----+----+----+----+----+");
        }


        String input;

        do{
            System.out.print("\tChoose a position for first worker: ");
            input = scanner.nextLine().toUpperCase();
        }while(!input.matches("[A-E][1-5]"));
        x1 = (input.charAt(0) - 'A');
        y1 = (input.charAt(1) - '1');

        do{
            System.out.print("\tChoose a position for second worker: ");
            input = scanner.nextLine().toUpperCase();
        }while(!input.matches("[A-E][1-5]"));
        x2 = (input.charAt(0) - 'A');
        y2 = (input.charAt(1) - '1');

        System.out.println("\n\n");

        clientView.workerPlacement(x1, y1, x2, y2);

    }

    @Override
    public void textMessage(String msg) {

        synchronized (lock) {
            System.out.println("\n\n" +"RECEIVED TEXT MESSAGE: \"" + msg + "\"");

        }
    }

    @Override
    public void startTurn() {

        synchronized (lock) {
            System.out.println("Your turn is started!");

        }


        updateBoard();
        selectWorker();

    }

    @Override
    public void selectWorker() {
        System.out.println("You have to select a worker to do an action with:");
        PlayerRepresentation player = board.getPlayersMap().get(clientView.getUsername());

        int i = 0;
        for (Pair<Integer, Integer> worker : player.getWorkers()){
            System.out.println("\t" + (i+1) + ")\t" + (char)('A' + worker.getFirst()) + ", " + (char)('1' + worker.getSecond()));
            i++;
        }

        String input;
        int selection = 0;

        do {
            do {
                System.out.print("\nSelect: ");
                input = scanner.nextLine();
                if(input.matches("[0-9]+"))
                    selection = Integer.parseInt(input);
            } while(selection <= 0 || selection > player.getWorkers().size());
            System.out.println();

            do {
                System.out.print("Do you confirm? [type y or yes to confirm, n or no to deny] \t");
                input = scanner.nextLine().toLowerCase();
            } while (!input.matches("[yn]|(yes)|(no)"));
        }while(!input.matches("y|(yes)"));

        Pair<Integer, Integer> selectedWorker = player.getWorkers().get(selection-1);

        System.out.println("\n\n");

        this.selectedWX = selectedWorker.getFirst();
        this.selectedWY = selectedWorker.getSecond();

        clientView.selectWorkerQuestion(selectedWorker.getFirst(), selectedWorker.getSecond());
    }


    @Override
    public void performAction(Map<Action, List<Pair<Integer, Integer>>> possibleActions) {

        synchronized (lock) {

            //print list of possible Actions, send AskActionEvent
            //System.out.println("Now you have to select the action you want your worker to perform.");
        }
        updateBoard();

        List<Action> l = new ArrayList<>();
        possibleActions.keySet().stream().forEach(x -> l.add(x));

        printActions(l);

        String inputAction;
        boolean done = false;
        Action action = Action.MOVE;    //should be overwritten
        do {
            do {
                System.out.print("Choose: ");
                inputAction = scanner.nextLine().toLowerCase();
            } while (!inputAction.matches("[mbde]|(div)|(up)"));

            switch (inputAction) {
                case "m":
                    action = Action.MOVE;
                    //done = true;
                    break;

                case "b":
                    action = Action.BUILD;
                    //done = true;
                    break;

                case "d":
                    action = Action.BUILDDOME;
                    //done = true;
                    break;

                case "e":
                    action = Action.END;
                    //done = true;
                    break;

                case "div":
                    printDescriptions(new ArrayList<>(board.getDivinities().keySet()));
                    updateBoard();
                    printActions(l);
                    break;

                case "up":
                    updateBoard();
                    printActions(l);
            }
            if(l.contains(action)) done = true;
        }while(!done);

        String inputTile;
        int x=-1,y=-1;
        if(!inputAction.equals("e")) {
            do {
                if (inputAction.equals("m"))
                    System.out.print("Choose the tile to move to: ");
                else
                    System.out.print("Choose the tile to build on: ");
                inputTile = scanner.nextLine().toUpperCase();
            } while(!inputTile.matches("[A-E][1-5]"));
            x = (inputTile.charAt(0) - 'A');
            y = (inputTile.charAt(1) - '1');
        }

        System.out.println("\n\n");

        clientView.actionQuestion(action, x, y);

    }

    @Override
    public void loser(String username) {

        synchronized (lock) {
            if(clientView.getUsername().equals(username)) {
                System.out.println("You lost! Fs in the chat");
                for(int i=0; i<10; i++)
                    System.out.println("F");
                //clientView.disconnect();
            }else{
                System.out.println("\n" + username + " has lost!");
            }
        }
        updateBoard();

    }

    @Override
    public void winner(String username) {

        synchronized (lock) {
            if(clientView.getUsername().equals(username)){
                System.out.println("\n-----------------------------------------");
                System.out.println("    Congratulation, you won the game!");
                System.out.println("-----------------------------------------");

//            System.out.println(" /$$     /$$                                                          /$$");
//            System.out.println("|  $$   /$$/                                                         | $$");
//            System.out.println(" \  $$ /$$//$$$$$$  /$$   /$$       /$$  /$$  /$$  /$$$$$$  /$$$$$$$ | $$");
//            System.out.println("  \  $$$$//$$__  $$| $$  | $$      | $$ | $$ | $$ /$$__  $$| $$__  $$| $$");
//            System.out.println("   \  $$/| $$  \ $$| $$  | $$      | $$ | $$ | $$| $$  \ $$| $$  \ $$|__/");
//            System.out.println("    | $$ | $$  | $$| $$  | $$      | $$ | $$ | $$| $$  | $$| $$  | $$");
//            System.out.println("    | $$ |  $$$$$$/|  $$$$$$/      |  $$$$$/$$$$/|  $$$$$$/| $$  | $$ /$$");
//            System.out.println("    |__/  \______/  \______/        \_____/\___/  \______/ |__/  |__/|__/\");

            }else{
                System.out.println("\n" + username + " won! This means you lost, Fs in the chat");
                for(int i=0; i<10; i++)
                    System.out.println("F");
            }
        }

        clientView.disconnect();
    }

    @Override
    public void playersDivinities() {
        printPlayersInGame();
    }

    @Override
    public void playerDisconnection(String username) {

        synchronized (lock) {
            System.out.println("\n\n");
            System.out.println("User " + username  + " has left... This match will end soon");
            clientView.disconnect();

            exit(0);
        }


    }

    @Override
    public void inLobby() {
        System.out.println("\nWAIT THE GAME START...\n\n");
    }

    @Override
    public void lobbyFull() {
        System.out.println("The lobby is full, you can't join this match!");
        System.out.println("\ndisconnecting...");
        clientView.disconnect();
    }

    @Override
    public void endTurn() {
        System.out.println("\nYour turn is ended!\n");
    }

    @Override
    public void startGame() {
        System.out.println("\n\nYOUR GAME IS STARTING! PLEASE WAIT...\n\n");
    }

    @Override
    public void invalidMove(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY) {
        StringBuilder s = new StringBuilder();
        s.append("ERROR!");

        if(board.getBoard()[wrongX][wrongY]==4) s.append(" YOU CAN'T MOVE ON A DOME!");
        else if(board.isThereAWorker(wrongX,wrongY)!=null) s.append(" YOU CAN'T MOVE ON AN OCCUPIED TILE!");
        else if(board.getBoard()[wrongX][wrongY]-board.getBoard()[selectedWY][selectedWY]>1) s.append(" YOU CAN'T MOVE TO A TILE SO HIGH!");
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) s.append(" YOU MUST SELECT A TILE ON THE BOARD!");
        else if(Math.abs(wrongX-selectedWX)>1 || Math.abs(wrongY-selectedWY)>1) s.append(" YOU MUST SELECT AN ADJACENT TILE!");
        else s.append(" YOU CAN'T MOVE HERE!");

        System.out.println(s.toString());


        //System.out.println("\n\nERROR! YOU CAN'T MOVE HERE!");
        performAction(possibleActions);
    }

    @Override
    public void invalidBuild(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY) {
        StringBuilder s = new StringBuilder();
        s.append("ERROR!");

        if(board.getBoard()[wrongX][wrongY]==4) s.append(" YOU CAN'T BUILD ON A DOME!");
        else if(board.isThereAWorker(wrongX,wrongY)!=null) s.append(" YOU CAN'T BUILD ON AN OCCUPIED TILE!");
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) s.append(" YOU MUST SELECT A TILE ON THE BOARD!");
        else if(Math.abs(wrongX-selectedWX)>1 || Math.abs(wrongY-selectedWY)>1) s.append(" YOU MUST SELECT AN ADJACENT TILE!");
        else s.append(" YOU CAN'T BUILD HERE!");

        System.out.println(s.toString());

        //System.out.println("\n\nERROR! YOU CAN'T BUILD HERE!");
        performAction(possibleActions);
    }

    @Override
    public void invalidWorkerPlacement() {
        System.out.println("\n\nERROR! INVALID WORKER PLACEMENT!");
        placeWorkers();
    }

    @Override
    public void invalidWorkerSelection(int wrongX, int wrongY) {
        StringBuilder s = new StringBuilder();
        s.append("ERROR!");

        if(board.isThereAWorker(wrongX,wrongY)==null) s.append(" YOU CAN'T SELECT A VOID CELL!");
        else if(!board.isThereAWorker(wrongX,wrongY).equals(clientView.getColor())) s.append(" YOU CAN'T SELECT AN OPPONENT WORKER!");
        else if(wrongX<0 || wrongX>4 || wrongY<0 || wrongY>4) s.append(" YOU MUST SELECT A TILE ON THE BOARD!");
        else s.append("YOU CAN'T USE THIS WORKER!");

        System.out.println(s.toString());
        //System.out.println("\n\nERROR! INVALID WORKER SELECTION!");
        selectWorker();
    }

    @Override
    public void moveUpdate(String player, int xFrom, int yFrom, int xTo, int yTo) {
        updateBoard();
    }

    @Override
    public void buildUpdate(String player, int x, int y) {
        updateBoard();
    }

    @Override
    public void workerPlacementUpdate(String player, int x1, int y1, int x2, int y2) {
        updateBoard();
    }



    public void updateBoard() {

        clearScreen();
        printPlayersInGame();

        synchronized (lock) {

            //System.out.println("\n\n\n");
            int [][]map = this.board.getBoard();
            char yCoordinate = 'A';

            //coordinates line
            System.out.println("\t" + "    1      2      3      4      5");

            for(int i=0; i<board.boardDimension; i++){
                //first line
                System.out.println("\t" + "+------+------+------+------+------+");

                //second line and board height
                System.out.print("\t");
                for(int j=0; j<board.boardDimension; j++) {
                    System.out.print("|");
                    if(map[i][j] >= 4 )
                        System.out.print(ANSI_PURPLE + (map[i][j]-4) + ANSI_RESET);
                    else
                        System.out.print(ANSI_PURPLE + map[i][j] + ANSI_RESET);
                    System.out.print("     ");
                }
                System.out.println("|");

                //third line and worker position
                System.out.print((yCoordinate++) + "\t");
                for(int j=0; j<board.boardDimension; j++) {
                    System.out.print("|   ");
                    Color worker = board.isThereAWorker(i, j);
                    if(map[i][j] >= 4) {
                        System.out.print(ANSI_BLUE + "D" + ANSI_RESET);
                    } else if(worker == null) {
                        System.out.print(" ");
                    } else {
                        switch (worker) {
                            case CREAM:
                                System.out.print(ANSI_YELLOW + "W" + ANSI_RESET);
                                break;

                            case BLUE:
                                System.out.print(ANSI_CYAN + "W" + ANSI_RESET);
                                break;

                            case WHITE:
                                System.out.print(ANSI_RED+ "W" + ANSI_RESET);
                        }
                    }
                    System.out.print("  ");
                }
                System.out.println("|");

                //fourth line -- NOT USED
//                System.out.println("\t" + "|       |       |       |       |       |");

            }
            //fifth and last line
            System.out.println("\t" + "+------+------+------+------+------+");



        }


    }

    private void printActions(List<Action> possibleActions) {
        System.out.println("Here are you available actions:");

        for(Action action : possibleActions) {
            switch(action) {
                case MOVE:
                    System.out.println("\tm)\t\tMove worker");
                    break;

                case BUILD:
                    System.out.println("\tb)\t\tBuild a level on a tile (dome if already level 3)");
                    break;

                case BUILDDOME:
                    System.out.println("\td)\t\tBuild a dome at any level on a tile");
                    break;

                case END:
                    System.out.println("\te)\t\tEnd your turn without further do");
                    break;
            }
        }
        System.out.println("\tdiv)\tRead divinities' effects descriptions");
        System.out.println("\tup)\t\tUpdate board");

        System.out.println();
    }

    private void printDescriptions(List<String> divinities) {
        Map<String, String> divDescriptions = board.getDivinities();
        List<String> divNames = new ArrayList<>(divDescriptions.keySet());

        System.out.println("\n\nHere you can read the descriptions of the divinities use in this match.");

        while(true) {
            System.out.println("Choose the divinity which you want to read the description:");

            System.out.println("\t0) Quit");
            for (int i = 0; i < divinities.size(); i++){
                //System.out.println("\t" + (i + 1) + ") " + divNames.get(i));
                System.out.println("\t" + (i + 1) + ") " + divinities.get(i));

            }

            int selection = -1;
            String input;
            do {
                System.out.print("Choose: ");
                input = scanner.nextLine();
                if(input.matches("[0-9]+"))
                    selection = Integer.parseInt(input);
            } while (selection < 0 || selection > divinities.size());

            if (selection == 0)
                return;
            else
                System.out.println("\t" + divDescriptions.get(divinities.get(selection-1)) + "\n");
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printPlayersInGame() {
        System.out.println();
        List<PlayerRepresentation> players = board.getPlayersList();

        synchronized (lock) {
            System.out.print("\nPLAYERS IN GAME: \n");
            for(PlayerRepresentation p : players) {
                String color = null;
                if(p.getColor().equals(Color.WHITE)) color = ANSI_RED;
                else if(p.getColor().equals(Color.BLUE)) color = ANSI_CYAN;
                else if(p.getColor().equals(Color.CREAM)) color = ANSI_YELLOW;

                StringBuilder s = new StringBuilder();
                s.append(color + p.getUsername());
                if(p.getDivinity()!=null) s.append(" --> " + p.getDivinity());
                if(p.getUsername().equals(clientView.getUsername())) s.append("\t\tYOU");

                System.out.print(s.toString() + "\n");
            }
            System.out.print(ANSI_RESET);
        }
    }

}
