package it.polimi.ingsw.Client;

import it.polimi.ingsw.events.serverToClient.BuildEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;
import org.junit.jupiter.api.Test;

class ClientViewTest {

    @Test
    public void basicTest() {
        CLI cli = new CLI();
        ClientView cv = new ClientView(cli,null,-1);
        cli.setClientView(cv);
        BoardRepresentation board = cv.getBoard();

        String myUser = "Marco";
        cv.setUsername(myUser);
        board.addPlayer(myUser, Color.BLUE);
        String user2 = "Gino";
        board.addPlayer(user2, Color.CREAM);

        board.addWorker(myUser, 1, 2);
        board.addWorker(myUser, 4, 2);

        board.addWorker(user2, 3, 2);
        board.addWorker(user2, 4, 4);

        System.out.println(board.getPlayersMap().get(myUser).getColor());
        System.out.println(board.getPlayersMap().get(user2).getColor());


        cli.updateBoard();

        //cv.update(new MoveEvent(myUser, new Tile(1,2), new Tile(1,1)));
        //cv.update(new BuildEvent(myUser, Action.BUILD, new Tile(1,2)));
        System.out.println("first");
        cv.update(new BuildEvent(myUser, Action.BUILD, 2, 2));
        cv.update(new BuildEvent(myUser, Action.BUILD, 2, 2));
        cv.update(new BuildEvent(myUser, Action.BUILD, 2, 2));
        cv.update(new BuildEvent(myUser, Action.BUILD, 2, 2));

        System.out.println("second");
        cv.update(new BuildEvent(myUser, Action.BUILDDOME, 0, 0));

        System.out.println("third");
        cv.update(new BuildEvent(myUser, Action.BUILD, 4, 0));
        cv.update(new BuildEvent(myUser, Action.BUILDDOME, 4, 0));


        System.out.println("\n\n\n");
        cli.updateBoard();

    }

}