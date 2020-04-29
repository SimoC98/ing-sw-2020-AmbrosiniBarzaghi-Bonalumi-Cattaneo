package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.modelToView.ModelUpdateEvent;
import it.polimi.ingsw.events.viewToController.*;
import it.polimi.ingsw.model.Action;

import java.util.List;
import java.util.Scanner;

public class View extends Observable<VCEvent> implements Observer<ModelUpdateEvent> {

    private Scanner scan;

    public View(){
        scan = new Scanner(System.in);
    }

    @Override
    public void update(ModelUpdateEvent event) {

    }

    //public void matchInitialization()

    public void startTurn(String username){
        System.out.println(username + ", it's your turn.");
        selectWorker();
    }

    public void showMessage(String message){
        System.out.println(message);
    }



    public void selectWorker(){
        System.out.println("Choose a worker: ");
        scan = new Scanner(System.in);
        /*int x = scan.nextInt();
        int y = scan.nextInt();*/
        String s = scan.nextLine();
        String[] coord = s.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        notify(new WorkerSelectionQuestionEvent(x,y));
    }

    public void askMove(){
        System.out.println("Where do you want to move?: ");
        scan = new Scanner(System.in);
        String s = scan.nextLine();
        String[] coord = s.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        notify(new MoveQuestionEvent(x,y));
    }

    public void askBuild(){
        System.out.println("Where do you want to build?: ");
        scan = new Scanner(System.in);
        String s = scan.nextLine();
        String[] coord = s.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        notify(new BuildQuestionEvent(x,y));
    }

    public void askBuildDome(){
        System.out.println("Where do you want to place a dome?");
        scan = new Scanner(System.in);
        String s = scan.nextLine();
        String[] coord = s.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        notify(new BuildDomeQuestionEvent(x,y));
    }

    public void askAction(List<Action> actions) {
        Action chosenAction = actions.get(0);
        scan = new Scanner(System.in);

        if(actions.size()>1) {
            System.out.println("These are your possible actions: ");
            for (int i=0;i<actions.size();i++){
                System.out.println(i + "." + actions.get(i));
            }
            int userInput = scan.nextInt();

            while(userInput<0 || userInput>=actions.size()) {
                System.out.println("wrong choice! try again");
                userInput = scan.nextInt();
            }
            chosenAction = actions.get(userInput);
        }
        switch (chosenAction){
            case BUILD:
                askBuild();
            case MOVE:
                askMove();
            case BUILDDOME:
                askBuildDome();
            case END:
                notify(new EndTurnQuestionEvent());
        }
    }

   /* public int[] parseCoordinate(String input) {
        int[] ret = new int[2];
        String[] coords = input.split(",");
        ret[0] = Integer.parseInt(coords[0]);
        ret[1] = Integer.parseInt(coords[1]);

        return ret;
    }*/

    public void endGame(String winner) {
        System.out.println("THE WINNER IS " + winner);
    }

}
