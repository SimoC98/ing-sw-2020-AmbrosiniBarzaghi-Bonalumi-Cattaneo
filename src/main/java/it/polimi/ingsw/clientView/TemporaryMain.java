package it.polimi.ingsw.clientView;

public class TemporaryMain {

    public static void main(String []args) {
        ClientView view = new ClientView();

        CLI cli = new CLI(view);

        cli.updateBoard();



    }
}
