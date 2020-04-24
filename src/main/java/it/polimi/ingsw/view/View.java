package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.modelToView.ModelUpdateEvent;
import it.polimi.ingsw.events.viewToController.*;
import it.polimi.ingsw.model.Action;

import java.util.HashSet;
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
        System.out.println(username+", it's your turn.");
        selectWorker();
    }

    public void showMessage(String message){
        System.out.println(message);
    }



    public void selectWorker(){
        System.out.println("Choose a worker");
        int x = scan.nextInt();
        int y = scan.nextInt();
        notify(new WorkerSelectionQuestionEvent(x,y));
    }

    public void askMove(){
        System.out.println("Where do you want to move?");
        int x = scan.nextInt();
        int y = scan.nextInt();
        notify(new MoveQuestionEvent(x,y));
    }

    public void askBuild(){
        System.out.println("Where do you want to build?");
        int x = scan.nextInt();
        int y = scan.nextInt();
        notify(new BuildQuestionEvent(x,y));
    }

    public void askBuildDome(){
        System.out.println("Where do you want to place a dome?");
        int x = scan.nextInt();
        int y = scan.nextInt();
        notify(new BuildDomeQuestionEvent(x,y));
    }

    public void askAction(List<Action> actions) {
        System.out.println("These are your possible actions");
        for (int i =0;i<actions.size();i++){
            System.out.println(i + ".");
            System.out.println(actions.get(i));
        }
        int s = scan.nextInt();
        Action a = actions.get(s);
        switch (a){
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

}
