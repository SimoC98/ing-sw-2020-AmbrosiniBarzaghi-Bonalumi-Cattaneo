package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidDivinitySelectionEvent;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import it.polimi.ingsw.serverView.ServerView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private Match match;

    @BeforeEach
    void setUp() {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("simone");
        players.add("luca");
        match = new Match(players);
        //match.setStartPlayer("luca");

        List<ServerView> proxies = new ArrayList<>();
        for(int i=0;i<players.size();i++) {
            ServerView s = new ServerView();
            proxies.add(s);
        }

        controller = new Controller(match,proxies);
        //controller.startGame(new ArrayList<>(),"luca");
        //controller.setStartPlayer("luca");
    }

    @Test
    void loseOnFirstTurntest1() {
        match.setStartPlayer("luca");
        controller.startGame(new ArrayList<>(),"luca");
        controller.setStartPlayer("luca");



        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");


        assert(match.getPlayers().get(0).getDivinity()!=null);
        assert(match.getPlayers().get(1).getDivinity()!=null);
        assert(match.getPlayers().get(2).getDivinity()!=null);


        assert(match.getCurrentPlayerId()==2);

        controller.handleWorkerPlacementInitialization(0,0,0,1);

        assert(match.getCurrentPlayerId()==0);
        assert(match.getCurrentPlayer().getUsername().equals("paolo"));

        controller.handleWorkerPlacementInitialization(1,0,1,1);

        assert(match.getCurrentPlayer().getUsername().equals("simone"));

        controller.handleWorkerPlacementInitialization(0,2,1,2);

        assert(match.getPlayers().size()==2);
        assert(match.getCurrentPlayer().getUsername().equals("paolo"));
        assert(match.getCurrentPlayerId()==0);

        assert(controller.getCurrentPlayerId()==match.getCurrentPlayerId());
        assert(controller.getPlayersInGame().size()==2);
        assert(controller.getPlayersUsernames().size()==2);
    }


    @Test
    void loseOnFirstTurntest2() {
        match.setStartPlayer("paolo");
        controller.startGame(new ArrayList<>(),"paolo");
        controller.setStartPlayer("paolo");



        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");


        assert(match.getPlayers().get(0).getDivinity()!=null);
        assert(match.getPlayers().get(1).getDivinity()!=null);
        assert(match.getPlayers().get(2).getDivinity()!=null);


        assert(match.getCurrentPlayerId()==0);

        controller.handleWorkerPlacementInitialization(0,0,0,1);

        assert(match.getCurrentPlayerId()==1);
        assert(match.getCurrentPlayer().getUsername().equals("simone"));

        controller.handleWorkerPlacementInitialization(1,0,1,1);

        assert(match.getCurrentPlayer().getUsername().equals("luca"));

        controller.handleWorkerPlacementInitialization(0,2,1,2);

        assert(match.getPlayers().size()==2);
        assert(match.getCurrentPlayer().getUsername().equals("simone"));
        assert(match.getCurrentPlayerId()==0);

        assert(controller.getCurrentPlayerId()==match.getCurrentPlayerId());
        assert(controller.getPlayersInGame().size()==2);
        assert(controller.getPlayersUsernames().size()==2);
    }


    @Test
    void loseOnFirstTurntest3() {
        match.setStartPlayer("simone");
        controller.startGame(new ArrayList<>(),"simone");
        controller.setStartPlayer("simone");



        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");


        assert(match.getPlayers().get(0).getDivinity()!=null);
        assert(match.getPlayers().get(1).getDivinity()!=null);
        assert(match.getPlayers().get(2).getDivinity()!=null);


        assert(match.getCurrentPlayerId()==1);

        controller.handleWorkerPlacementInitialization(0,0,0,1);

        assert(match.getCurrentPlayerId()==2);
        assert(match.getCurrentPlayer().getUsername().equals("luca"));

        controller.handleWorkerPlacementInitialization(1,0,1,1);

        assert(match.getCurrentPlayer().getUsername().equals("paolo"));

        controller.handleWorkerPlacementInitialization(0,2,1,2);

        assert(match.getPlayers().size()==2);
        assert(match.getCurrentPlayer().getUsername().equals("luca"));
        assert(match.getCurrentPlayerId()==1);

        assert(controller.getCurrentPlayerId()==match.getCurrentPlayerId());
        assert(controller.getPlayersInGame().size()==2);
        assert(controller.getPlayersUsernames().size()==2);
    }

    @Test
    void test4() {
        match.setStartPlayer("simone");
        controller.startGame(new ArrayList<>(),"simone");
        controller.setStartPlayer("simone");

        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");
        controller.handleDivinityInitialization("Pan");


        assert(match.getPlayers().get(0).getDivinity()!=null);
        assert(match.getPlayers().get(1).getDivinity()!=null);
        assert(match.getPlayers().get(2).getDivinity()!=null);


        assert(match.getCurrentPlayerId()==1);

        controller.handleWorkerPlacementInitialization(4,0,0,1);

        assert(match.getCurrentPlayerId()==2);
        assert(match.getCurrentPlayer().getUsername().equals("luca"));

        controller.handleWorkerPlacementInitialization(1,0,1,1);

        assert(match.getCurrentPlayer().getUsername().equals("paolo"));

        controller.handleWorkerPlacementInitialization(0,2,1,2);

        assert(match.getPlayers().size()==3);
        assert(match.getCurrentPlayer().getUsername().equals("simone"));
        assert(match.getCurrentPlayerId()==1);
        assert(match.getCurrentPlayerId()==controller.getStartPlayer());

        assert(controller.getCurrentPlayerId()==match.getCurrentPlayerId());
        assert(controller.getPlayersInGame().size()==3);
        assert(controller.getPlayersUsernames().size()==3);
    }
}