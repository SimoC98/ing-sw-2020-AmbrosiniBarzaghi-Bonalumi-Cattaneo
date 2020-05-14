package it.polimi.ingsw.clientView;

import it.polimi.ingsw.events.serverToClient.BuildEvent;
import it.polimi.ingsw.events.serverToClient.MoveEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientViewTest {

    @Test
    public void basicTest() {
        ClientView cv = new ClientView();
        CLI cli = new CLI(cv);
        BoardRepresentation board = cv.getBoard();

        String myUser = "Marco";
        cv.setUsername(myUser);
        board.addPlayer(myUser, Color.BLUE);
        String user2 = "Gino";
        board.addPlayer(user2, Color.CREAM);

        board.addWorker(myUser, 1, 2);
        board.addWorker(myUser, 4, 2);

        board.addWorker(user2, 2, 2);
        board.addWorker(user2, 4, 4);

        System.out.println(board.getPlayersMap().get(myUser).getColor());
        System.out.println(board.getPlayersMap().get(user2).getColor());


        cli.updateBoard();

        //cv.update(new MoveEvent(myUser, new Tile(1,2), new Tile(1,1)));
        cv.update(new BuildEvent(myUser, Action.BUILD, new Tile(1,2)));

        System.out.println("\n\n\n");
        cli.updateBoard();

    }

}