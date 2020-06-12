package it.polimi.ingsw.clientView;

import it.polimi.ingsw.clientView.gui.GUI;

public class GuiEseguibile {

    public static void main(String[] args) {
        GUI gui = new GUI();
        ClientView clientView = new ClientView(gui,null,-1);

        gui.setClientView(clientView);

        new Thread(()->{
            gui.start();
        }).start();
    }
}
