package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.events.clientToServer.Ping;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
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
    Scanner scanner;

    private final Object lock;

    public CLI(ClientView clientView) {
        lock = new Object();
        this.clientView = clientView;
        board = clientView.getBoard();
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
    public void start() {
        System.out.println("Welcome to..." + "\n");

        System.out.println(" .oooooo..o                           .                       o8o               o8o");
        System.out.println("d8P'    `Y8                         .o8                       `\"'               `\"'");
        System.out.println("Y88bo.       .oooo.   ooo. .oo.   .o888oo  .ooooo.  oooo d8b oooo  ooo. .oo.   oooo");
        System.out.println(" `\"Y8888o.  `P  )88b  `888P\"Y88b    888   d88' `88b `888\"\"8P `888  `888P\"Y88b  `888");
        System.out.println("     `\"Y88b  .oP\"888   888   888    888   888   888  888      888   888   888   888");
        System.out.println("oo     .d8P d8(  888   888   888    888 . 888   888  888      888   888   888   888");
        System.out.println("8\"\"88888P'  `Y888\"\"8o o888o o888o   \"888\" `Y8bod8P' d888b    o888o o888o o888o o888o" + "\n\n");
    }

//    @Override
//    public void login() {
//        String username;
//        do {
//            System.out.print("Choose your username (at least 3 characters): ");
//            username = scanner.nextLine();
//        }while(username.length() < 3);
//        clientView.loginQuestion(username);
//    }
    //TODO: this login is temporary
    @Override
    public void login() {
        String username;
        do {
            System.out.print("Choose your username (at least 3 characters): ");
            username = scanner.nextLine();
        }while(username.length() < 3);

        if(clientView.getUserID() == 0)
        {
            System.out.println("You're the first logged user, so you have to choose the number of players for this match.");
            System.out.println("You can choose between 2 and 3 players.");

            String input;
            int playersNumber = 0;

            do{
                System.out.print("Choose: ");
                input = scanner.nextLine();
                if(input.matches("[0-9]+"))
                    playersNumber = Integer.parseInt(input);

            }while(playersNumber<2 || playersNumber>3);
            clientView.loginQuestion2(playersNumber, username);
            clientView.startPing();
            return;
        }

        clientView.loginQuestion(username);
        clientView.startPing();

    }

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
        }while(username.length()<3 && !users.contains(username));
        clientView.loginQuestion(username);
    }

    @Override
    public void selectPlayersNumber() {
        System.out.println("You're the first logged user, so you have to choose the number of players for this match.");
        System.out.println("You can choose between 2 and 3 players.");

        String input;
        int playersNumber = 0;
        do{
            System.out.print("Choose: ");
            input = scanner.nextLine();
            if(input.matches("[0-9]+"))
                playersNumber = Integer.parseInt(input);
        }while(playersNumber < 2 || playersNumber > 3);

        clientView.playersNumberQuestion(playersNumber);
    }

    //TODO: print once and don't remove div from names list OR re print the list every time
    @Override
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber) {
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

                //next line found at https://stackoverflow.com/questions/10575624/java-string-see-if-a-string-contains-only-numbers-and-not-letters
                if(input.matches("[0-9]+"))
                    selection = Integer.parseInt(input);

            } while (selection <= 0 || selection > divinitiesNames.size());
            playableDivinities.add(divinitiesNames.get(selection-1));
            divinitiesNames.remove(selection-1);
            divinitiesDescriptions.remove(selection-1);
            if (playableDivinities.size() != playersNumber) {
                System.out.println("\n\n\n\n");
                for (int i = 0; i < divinitiesNames.size(); i++)
                    System.out.println(i + 1 + ") " + divinitiesNames.get(i) + "\n\t" + divinitiesDescriptions.get(i));
            }
        }

        System.out.print("The divinities you have chosen: ");
        for(String div : playableDivinities)
            System.out.print("\t" + div);

        clientView.playableDivinitiesSelection(playableDivinities);
    }

//    @Override
//    public void selectDivinityAndPlaceWorkers(List<String> divinitiesNames) {
//        synchronized (lock) {
//            String divinity=null;
//
//            System.out.println("You have to select your divinity. Choose from:");
//            for (int i = 0; i < divinitiesNames.size(); i++)
//                System.out.println("\t" + (i + 1) + ") " + divinitiesNames.get(i));
//            System.out.println("\t" + (divinitiesNames.size() + 1) + ") " + "See divinities descriptions");
//
//            String input;
//            int selection = 0;
//            do {
//                do {
//                    System.out.print("\nChoose: ");
//                    input = scanner.nextLine();
//                    if (input.matches("[0-9]+"))
//                        selection = Integer.parseInt(input);
//                } while (selection <= 0 || selection > divinitiesNames.size() + 1);
//
//                if (selection == divinitiesNames.size() + 1) {
//                    printDescriptions();
//                    System.out.println("\n\n\n");
//                    for (int i = 0; i < divinitiesNames.size(); i++)
//                        System.out.println("\t" + (i + 1) + ") " + divinitiesNames.get(i));
//                    System.out.println("\t" + (divinitiesNames.size() + 1) + ") " + "See divinities descriptions");
//                }
//                else
//                    divinity = divinitiesNames.get(selection - 1);
//            }while(divinity==null);
//
//            //----------------------------------------------------------------------
//
//            int x1, y1, x2, y2;
//
//            System.out.println("You have to choose your workers' initial position.");
//            System.out.println("You must type the coordinate (E.G. A5, D2, E4) in which you want to place you workers, one at a time.");
//            System.out.println("You cannot place workers on occupied tiles.");
//
//            System.out.println("+----+----+----+----+----+");
//            System.out.println("| A1 | A2 | A3 | A4 | A5 |");
//            System.out.println("+----+----+----+----+----+");
//            System.out.println("| B1 | B2 | B3 | B4 | B5 |");
//            System.out.println("+----+----+----+----+----+");
//            System.out.println("| C1 | C2 | C3 | C4 | C5 |");
//            System.out.println("+----+----+----+----+----+");
//            System.out.println("| D1 | D2 | D3 | D4 | D5 |");
//            System.out.println("+----+----+----+----+----+");
//            System.out.println("| E1 | E2 | E3 | E4 | E5 |");
//            System.out.println("+----+----+----+----+----+");
//
//
//            do{
//                System.out.print("\tChoose a position for first worker: ");
//                input = scanner.nextLine().toUpperCase();
//            }while(!input.matches("[A-E][1-5]"));
//            x1 = (input.charAt(0) - 'A');
//            y1 = (input.charAt(1) - '1');
//
//            do{
//                System.out.print("\tChoose a position for second worker: ");
//                input = scanner.nextLine().toUpperCase();
//            }while(!input.matches("[A-E][1-5]"));
//            x2 = (input.charAt(0) - 'A');
//            y2 = (input.charAt(1) - '1');
//
//            clientView.divinitySelectionAndWorkerPlacement(divinity, x1, y1, x2, y2);
//        }
//    }

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
                printDescriptions();
                System.out.println("\n\n\n");
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

        clientView.workerPlacement(x1, y1, x2, y2);
    }

    @Override
    public void textMessage(String msg) {
        System.out.println("Received text message: \"" + msg + "\"");
    }

    @Override
    public void startTurn() {
        System.out.println("Your turn is started!");
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
        clientView.selectWorkerQuestion(selectedWorker.getFirst(), selectedWorker.getSecond());
    }

    @Override
    public void performAction(List<Action> possibleActions) {
        //print list of possible Actions, send AskActionEvent
        System.out.println("Now you have to select the action you want your worker to perform.");
        updateBoard();
        System.out.println("Here are you available actions:");

        for(Action action : possibleActions) {
            switch(action) {
                case MOVE:
                    System.out.println("\tm) Move worker");
                    break;

                case BUILD:
                    System.out.println("\tb) Build a level on a tile (dome if already level 3)");
                    break;

                case BUILDDOME:
                    System.out.println("\td) Build a dome at any level on a tile");
                    break;

                case END:
                    System.out.println("\te) End your turn without further do");
                    break;
            }
        }
        System.out.println("\tdiv) Read divinities' effects descriptions");
        System.out.println("\tup) Update board");

        System.out.println();

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
                    done = true;
                    break;

                case "b":
                    action = Action.BUILD;
                    done = true;
                    break;

                case "d":
                    action = Action.BUILDDOME;
                    done = true;
                    break;

                case "e":
                    action = Action.END;
                    done = true;
                    break;

                case "div":
                    printDescriptions();
                    break;

                case "up":
                    updateBoard();
            }
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

        clientView.actionQuestion(action, x, y);
    }

    @Override
    public void loser(String username) {
        if(clientView.getUsername().equals(username)) {
            System.out.println("You lost! Fs in the chat");
            for(int i=0; i<10; i++)
                System.out.println("F");
            //clientView.disconnect();
        }else{
            System.out.println("\n" + username + " has lost!");
        }
    }

    @Override
    public void winner(String username) {
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
        clientView.disconnect();
    }

    @Override
    public void updateBoard() {
        System.out.println("\n\n\n\n\n\n\n");
        printPlayersInGame();
        synchronized (lock) {
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
                    if(map[i][j] == 4 )
                        System.out.print(ANSI_BLUE + "D" + ANSI_RESET);
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
                    if(worker == null)
                        System.out.print(" ");
                    else {
                        switch (worker) {
                            case CREAM:
                                System.out.print(ANSI_YELLOW + "W" + ANSI_RESET);
                                break;

                            case BLUE:
                                System.out.print(ANSI_CYAN + "W" + ANSI_RESET);
                                break;

                            case RED:
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

    private void printDescriptions() {
        Map<String, String> divDescriptions = board.getDivinities();
        List<String> divNames = new ArrayList<>(divDescriptions.keySet());

        System.out.println("\n\nHere you can read the descriptions of the divinities use in this match.");

        while(true) {
            System.out.println("Choose the divinity which you want to read the description:");

            System.out.println("\t0) Quit");
            for (int i = 0; i < divNames.size(); i++)
                System.out.println("\t" + (i + 1) + ") " + divNames.get(i));

            int selection = -1;
            String input;
            do {
                System.out.print("Choose: ");
                input = scanner.nextLine();
                if(input.matches("[0-9]+"))
                    selection = Integer.parseInt(input);
            } while (selection < 0 || selection > divNames.size());

            if (selection == 0)
                return;
            else
                System.out.println("\t" + divDescriptions.get(divNames.get(selection-1)) + "\n");
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printPlayersInGame() {
        List<PlayerRepresentation> players = board.getPlayersList();

        System.out.print("\nPLAYERS IN GAME: \n");
        for(PlayerRepresentation p : players) {
            String color = null;
            if(p.getColor().equals(Color.RED)) color = ANSI_RED;
            else if(p.getColor().equals(Color.BLUE)) color = ANSI_CYAN;
            else if(p.getColor().equals(Color.CREAM)) color = ANSI_YELLOW;

            StringBuilder s = new StringBuilder();
            s.append(color + p.getUsername());
            if(p.getDivinity()!=null) s.append(" --> " + p.getDivinity());

            System.out.print(s.toString() + "\n");
        }
        System.out.print(ANSI_RESET);
    }
}
