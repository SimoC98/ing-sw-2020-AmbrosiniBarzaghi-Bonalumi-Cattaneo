package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PlayerRepresentation;
import it.polimi.ingsw.model.Action;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;
import java.util.List;

import static java.lang.System.exit;

public class MatchController {
    private static ClientView clientView;
    private static GUI gui;

    @FXML
    private HBox hBox;
    @FXML
    private VBox vBoxLeft;
    @FXML
    private GridPane board;
    @FXML
    private StackPane rightStack;
    @FXML
    private Label turnInformation;
    @FXML
    private Label message;
    @FXML
    private VBox possibleActionsBox;

    @FXML
    private VBox alertVBox;

    //CONSTANTS
    private final int LVL1=0, LVL2=1, LVL3=2, DOME=3, WORKER=4, SHADOW=5;   //for every StackPane of the board
    private final double CARD_RATIO = 1.68;
    private final int BTN_WIDTH=100, BTN_HEIGHT=40;

    private String actualAction;
    private Map<String, Image> workerColors;
    private List<Image> tileLevel;
    boolean isInitialized = false;

    private List<Pair<Integer,Integer>> workerPlacement = new ArrayList<>();

    private Map<Action,List<Pair<Integer,Integer>>> possibleActions;
    private int selectedWX,selectedWY;

    private Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(), 20);

    private Lighting lighting;

    private boolean listenerOff=false;


    public MatchController() {
    }

    @FXML
    public void initialize() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        this.lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);

        createBoard();

        hBox.setMinHeight(200);
        hBox.setMinWidth(400);
        hBox.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());
        hBox.setSpacing(20);

        workerColors = Map.of(
                "blue" , new Image("/graphics/blue.png"),
                "cream", new Image("/graphics/cream.png"),
                "white", new Image("/graphics/white.png"));

        tileLevel = Arrays.asList(
                new Image("/graphics/lvl1.png"),
                new Image("/graphics/lvl2.png"),
                new Image("/graphics/lvl3.png"),
                new Image("/graphics/dome.png"));

        isInitialized = true;

        board.prefWidthProperty().bind(hBox.widthProperty().divide(2).subtract(10));
        board.prefHeightProperty().bind(board.widthProperty());

        vBoxLeft.setSpacing(10);
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxLeft.prefWidthProperty().bind(hBox.widthProperty().divide(4).subtract(10));
        vBoxLeft.prefHeightProperty().bind(hBox.heightProperty().multiply(0.8));
        vBoxLeft.minHeightProperty().setValue(100);
        vBoxLeft.alignmentProperty().setValue(Pos.CENTER);

        rightStack.prefWidthProperty().bind(hBox.widthProperty().divide(4).subtract(10));
        rightStack.prefHeightProperty().bind(hBox.heightProperty().multiply(0.8));
        rightStack.alignmentProperty().setValue(Pos.CENTER);

        turnInformation.maxWidthProperty().bind(rightStack.maxWidthProperty());
        turnInformation.prefWidthProperty().bind(rightStack.widthProperty());
//        turnInformation.setWrapText(true);
        turnInformation.alignmentProperty().setValue(Pos.TOP_CENTER);
        turnInformation.setFont(getSantoriniFont(30));
        turnInformation.setText("OPPONENTS'\nPLACING WORKERS");

        message.maxWidthProperty().bind(rightStack.maxWidthProperty());
        message.prefWidthProperty().bind(rightStack.widthProperty());
        message.setWrapText(true);
        message.alignmentProperty().setValue(Pos.TOP_CENTER);
        message.setFont(getSantoriniFont(24));

        possibleActionsBox.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
        possibleActionsBox.setSpacing(20);

        alertVBox.prefWidthProperty().unbind();
        alertVBox.prefHeightProperty().unbind();
        alertVBox.prefWidthProperty().setValue(500);
        alertVBox.prefHeightProperty().setValue(500);
        alertVBox.maxWidthProperty().bind(alertVBox.prefWidthProperty());
        alertVBox.maxHeightProperty().bind(alertVBox.prefHeightProperty());
        alertVBox.alignmentProperty().setValue(Pos.CENTER);
        alertVBox.setVisible(false);
        alertVBox.setSpacing(10);

        actualAction="default";
    }

    protected int getSelectedWX() {
        return selectedWX;
    }

    protected int getSelectedWY() {
        return selectedWY;
    }

    public static void setClientView(ClientView clientView) {
        MatchController.clientView = clientView;
    }

    public static void setGui(GUI gui) {
        MatchController.gui = gui;
        MatchController.clientView = gui.getClientView();
    }

    public void createBoard() {
        for(int i=0; i<board.getColumnCount(); i++) {
            for(int j=0; j<board.getRowCount(); j++) {
                StackPane s = new StackPane();
                s.prefHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10));
                s.prefWidthProperty().bind(s.heightProperty());

                for(int k=0; k<3; k++) {
                    ImageView lvl = new ImageView();
                    lvl.fitHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10).multiply(1-(0.175*k)));
                    lvl.fitWidthProperty().bind(lvl.fitHeightProperty());
                    s.getChildren().add(lvl);
                }
                ImageView dome = new ImageView();
                dome.fitHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10).multiply(0.5));
                dome.fitWidthProperty().bind(dome.fitHeightProperty());
                s.getChildren().add(dome);

                ImageView worker = new ImageView();
                worker.fitHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10).multiply(0.95));
                worker.fitWidthProperty().bind(worker.fitHeightProperty());
                s.getChildren().add(worker);

                StackPane selectableShade = new StackPane();
                selectableShade.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                selectableShade.setOpacity(0.4);
                selectableShade.setVisible(false);
                s.getChildren().add(selectableShade);


                board.add(s, i, j);

                s.setOnMouseClicked((e) -> action(s));
            }
        }
    }

    public void action(StackPane s) {

        switch(actualAction) {
            case "move":
                moveOnClickedTile(s);
                break;

            case "build":
                buildOnClickedTile(s);
                break;

            case "builddome":
                buildDomeOnClickedTile(s);
                break;

            case "end":
                //nothing
                break;

            case "selectworker":
                selectWorker(s);
                break;
            case "placeworkers":
                placeWorkerOnClickedTile(s);
                break;
            default: break;
        }

    }

    /*
    methods that manage click on tiles --> invocation of methods on clientView
    commented methods can be useful to modify the board after model state changes
     */

    public void placeWorkerOnClickedTile(StackPane s) {
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);


        //System.out.println(x + "-" + y);

        workerPlacement.add(new Pair<>(x,y));

        s.getChildren().get(SHADOW).setVisible(true);

        if(workerPlacement.size()==2) {
            //System.out.println("workers placed in: " + workerPlacement.get(0).getFirst() + "-" + workerPlacement.get(0).getSecond() + "and " + workerPlacement.get(1).getFirst() + "-" + workerPlacement.get(1).getSecond());
            actualAction = "default";
            setShadowOff();
            clientView.workerPlacement(workerPlacement.get(0).getFirst(),workerPlacement.get(0).getSecond(),workerPlacement.get(1).getFirst(),workerPlacement.get(1).getSecond());
            endTurn();
            message.setText("");
        }
    }

    public void moveOnClickedTile(StackPane s) {
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        //System.out.println(x + "-" + y);

        actualAction = "default";

        emptyPossibleActions();
        setShadowOff();
        clientView.actionQuestion(Action.MOVE,x,y);
    }

    public void buildOnClickedTile(StackPane s) {
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        //System.out.println(x + "-" + y);

        actualAction = "default";

        emptyPossibleActions();
        setShadowOff();
        clientView.actionQuestion(Action.BUILD,x,y);
    }

    public void buildDomeOnClickedTile(StackPane s) {
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        //System.out.println(x + "-" + y);

        actualAction = "default";

        emptyPossibleActions();
        setShadowOff();
        clientView.actionQuestion(Action.BUILDDOME,x,y);
    }

    public void handlePossibleActions(Map<Action,List<Pair<Integer,Integer>>> possibleActions) {
        this.possibleActions = possibleActions;

        message.setText("Choose the action to perform: ");

        List<Button> actionButtons = new ArrayList<>();

        if(possibleActions.size()==1) {
            Action action = (Action) possibleActions.keySet().toArray()[0];

            actualAction = action.toString().toLowerCase();
            setShadowOff();
            markAvailableTiles(action);
            message.setText("Choose tile to\n" + action.toString());

        }
        else {
            for(Action action : possibleActions.keySet()) {
                //System.out.println(action.toString());

                Button actionBtn = newStandardizedButton();
                actionBtn.setText(action.toString());
                actionBtn.setOnMouseClicked((event) -> {
                    actualAction = action.toString().toLowerCase();
                    setShadowOff();
                    markAvailableTiles(action);
//                emptyPossibleActions();
                    if(action != Action.END)
                        message.setText("Choose tile to\n" + action.toString());
                    else
                        endTurnConfirmation();
                });
                actionBtn.getStyleClass().addAll("blue", "white");

                actionButtons.add(actionBtn);
            }

            possibleActionsBox.getChildren().addAll(actionButtons);
        }


    }

    private void emptyPossibleActions() {
        possibleActionsBox.getChildren().clear();
    }

    public void startTurn() {
        turnInformation.setText("YOUR TURN");
    }

    public void endTurn() {
        turnInformation.setText("OPPONENTS'\nTURNS");
    }

    public void selectWorker(StackPane s) {
        ImageView v = (ImageView) s.getChildren().get(WORKER);

        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        it.polimi.ingsw.model.Color color = clientView.getColor();
        it.polimi.ingsw.model.Color selectedColor = clientView.getBoard().isThereAWorker(x, y);

        if (selectedColor != null && selectedColor.equals(color)) {
            if(listenerOff)return;
            listenerOff=true;

            //System.out.println("Worker Selected");

            alertVBox.prefWidth(250);
            alertVBox.prefHeight(100);

            Button select = newStandardizedButton();
            select.setText("SELECT");
            select.prefWidth(BTN_WIDTH/2);
            select.setFont(getSantoriniFont(16));
            select.setOnMouseClicked(event -> {
                alertVBox.setVisible(false);
                alertVBox.getChildren().clear();
                setShadowOff();
                message.setText("Selected worker: " + x + "-" + y);
                this.selectedWX = x;
                this.selectedWY = y;
                clientView.selectWorkerQuestion(x, y);
                listenerOff=false;
            });
            select.getStyleClass().addAll("blue", "white");

            Button cancel = newStandardizedButton();
            cancel.setText("CANCEL");
            cancel.prefWidth(BTN_WIDTH/2);
            cancel.setFont(getSantoriniFont(16));
            cancel.setOnMouseClicked(event -> {
                alertVBox.setVisible(false);
                alertVBox.getChildren().clear();
                listenerOff=false;
            });
            cancel.getStyleClass().addAll("coral", "white");

            HBox btnBox = new HBox();
            btnBox.setAlignment(Pos.CENTER);
            btnBox.setSpacing(20);
            btnBox.getChildren().addAll(select, cancel);

            Label text = new Label("Do you want to select this worker? " + x + "-" + y);
            text.setWrapText(true);
            text.setFont(getSantoriniFont(18));
            text.prefWidth(alertVBox.getWidth() * 0.6);
            text.setAlignment(Pos.CENTER);

            alertVBox.getChildren().addAll(text, btnBox);
            alertVBox.setVisible(true);
        }
    }

    public void placeWorkers(String username, int x1, int y1, int x2, int y2) {

        StackPane s1 = (StackPane) getBoardCell(x1,y1);
        s1.getChildren().get(SHADOW).setVisible(false);
        ImageView w1 = (ImageView) s1.getChildren().get(WORKER);

        StackPane s2 = (StackPane) getBoardCell(x2,y2);
        s2.getChildren().get(SHADOW).setVisible(false);
        ImageView w2 = (ImageView) s2.getChildren().get(WORKER);

        String color = clientView.getBoard().getPlayersMap().get(username).getColor().toString().toLowerCase();

        Image image = workerColors.get(color);
        w1.setImage(image);
        w2.setImage(image);
    }

    public void moveUpdate(String player, int x1, int y1, int x2, int y2) {
        //System.out.println("update move");

        String color = clientView.getBoard().getPlayersMap().get(player).getColor().toString().toLowerCase();

        message.setText(player + " has moved from " + x1+"-"+y1 + " to " + x2+"-"+y2);

        StackPane stackPaneFrom = (StackPane) board.getChildren().get(y1*board.getRowCount() + x1);   //TODO: check if x and y must be inverted
        StackPane stackPaneTo = (StackPane) board.getChildren().get(y2*board.getRowCount() + x2);   //TODO: check if x and y must be inverted

        ImageView workerFrom = (ImageView) stackPaneFrom.getChildren().get(WORKER);
        Image workerImg = workerColors.get(color);
        if(workerImg == null) {
            System.out.println("Huston we have a problem, a move arrived from an empty tile");
            return;
        }

        if(clientView.getBoard().isThereAWorker(x1,y1)==null) workerFrom.setImage(null);

        ImageView workerTo = (ImageView) stackPaneTo.getChildren().get(WORKER);
        workerTo.setImage(null);
        workerTo.setImage(workerImg);
    }

    public void buildUpdate(String player, int x, int y) {
        message.setText(player + " has built on tile " + x+"-"+y);
        //System.out.println(x + "-" + y);
        StackPane s = (StackPane) board.getChildren().get(y*board.getRowCount() + x);   //TODO: check if x and y must be inverted

        ImageView imageView;

        switch(clientView.getBoard().getBoard()[x][y]) {
            case 1:
                imageView = (ImageView) s.getChildren().get(LVL1);
                imageView.setImage(tileLevel.get(LVL1));
                break;
            case 2:
                imageView = (ImageView) s.getChildren().get(LVL2);
                imageView.setImage(tileLevel.get(LVL2));
                break;
            case 3:
                imageView = (ImageView) s.getChildren().get(LVL3);
                imageView.setImage(tileLevel.get(LVL3));
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                imageView = (ImageView) s.getChildren().get(DOME);
                imageView.setImage(tileLevel.get(DOME));
                break;
            default:
                //System.out.println("boh");
        }
    }


    /*
    methods that ask user to do something and change actualAction --> invoked by gui
     */

    public void setActionPlaceWorkers() {
        workerPlacement.clear();

        //System.out.println("Now you PLACEWORKERS");
        turnInformation.setText("YOUR TURN\nTO PLACE WORKERS");
        message.setText("Click on the tile you want to place your workers");
        //userInteractionLabel.setVisible(true);
        actualAction = "placeworkers";

    }

    public void setActionSelectWorker() {
        //System.out.println("Now you SELECTWORKER");
        message.setText("Click the worker you want to play with");
        //userInteractionLabel.setVisible(true);
        actualAction = "selectworker";
        focusWorkers(true);
    }

    public void endTurnConfirmation() {
        if(listenerOff)return;
        listenerOff=true;

        alertVBox.prefWidth(250);
        alertVBox.prefHeight(100);


        Button select = newStandardizedButton();
        select.setText("END");
        select.prefWidth(BTN_WIDTH/2.0);
        select.setFont(getSantoriniFont(16));
        select.setOnMouseClicked(event -> {
            alertVBox.setVisible(false);
            alertVBox.getChildren().clear();
            message.setText("Your turn is over");
            emptyPossibleActions();
            clientView.actionQuestion(Action.END, -1, -1);
            listenerOff=false;
        });
        select.getStyleClass().addAll("blue", "white");

        Button cancel = newStandardizedButton();
        cancel.setText("CANCEL");
        cancel.prefWidth(BTN_WIDTH/2.0);
        cancel.setFont(getSantoriniFont(16));
        cancel.setOnMouseClicked(event -> {
            alertVBox.setVisible(false);
            alertVBox.getChildren().clear();
            listenerOff=false;
        });
        cancel.getStyleClass().addAll("coral", "white");

        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(20);
        btnBox.getChildren().addAll(select, cancel);

        Label text = new Label("Do you want to end your turn?");
        text.setWrapText(true);
        text.setFont(getSantoriniFont(18));
        text.prefWidth(alertVBox.getWidth() * 0.6);
        text.setAlignment(Pos.CENTER);

        alertVBox.getChildren().addAll(text, btnBox);
        alertVBox.setVisible(true);
    }

    public void setPlayers() {
        List<PlayerRepresentation> players = clientView.getBoard().getPlayersList();
        
        for(int i=0;i<players.size();i++) {
            String div = players.get(i).getDivinity();

            ImageView god = new ImageView();
            god.fitHeightProperty().bind(vBoxLeft.heightProperty().multiply(0.25));
            god.fitWidthProperty().bind(god.fitHeightProperty().divide(CARD_RATIO));
            String url = "/graphics/" +players.get(i).getDivinity().toLowerCase() + ".png";
            Image godImage = new Image(url);
            god.setImage(godImage);

            Tooltip tt = new Tooltip();
            tt.setFont(santoriniFont);
//            tt.setText(clientView.getBoard().getDivinities().get(div)); //hinta la descrizione
            tt.setText("Click to see God's description");
            Tooltip.install(
                god,
                tt
            );

            god.setOnMouseClicked((e) -> godDescrAlertSetup(div, god.getImage()));

            VBox box = new VBox();
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER);

            //Label playerName = new Label(players.get(i).getUsername());
            Label playerName = new Label();
            if(players.get(i).getUsername().equals(clientView.getUsername())) playerName.setText(clientView.getUsername() + " (YOU)");
            else playerName.setText(players.get(i).getUsername());
            playerName.setFont(santoriniFont);

            box.getChildren().addAll(god, playerName);

            vBoxLeft.getChildren().add(box);
        }

        Button exit = newStandardizedButton();
        exit.setText("QUIT");
        exit.getStyleClass().addAll("coral", "white");
        exit.setOnMouseClicked((e)-> exit(0));

        vBoxLeft.getChildren().add(exit);
    }

    public void godDescrAlertSetup(String godName, Image godImg) {
        if(listenerOff)return;
        listenerOff=true;

        //System.out.println("Opening description, " + godName);

        alertVBox.prefWidth(400);
        alertVBox.prefHeight(600);

       // System.out.println("ciao");

        ImageView imgView = new ImageView(godImg);
        imgView.fitHeightProperty().setValue(alertVBox.getHeight() * 0.5);
        imgView.fitWidthProperty().setValue(alertVBox.getHeight() * 0.5 / CARD_RATIO);

        Label description = new Label(clientView.getBoard().getDivinities().get(godName));
        description.setWrapText(true);
        description.setFont(getSantoriniFont(18));
        description.alignmentProperty().setValue(Pos.CENTER);
        description.prefWidthProperty().bind(alertVBox.widthProperty().multiply(0.6));

        Button closeBtn = newStandardizedButton();
        closeBtn.setText("CLOSE");
        closeBtn.setAlignment(Pos.BOTTOM_CENTER);
        closeBtn.setOnMouseClicked(event -> {
            alertVBox.setVisible(false);
            alertVBox.getChildren().clear();
            listenerOff=false;
        });
        closeBtn.getStyleClass().addAll("coral", "white");

       // System.out.println("ciao 2");

        alertVBox.getChildren().addAll(imgView, description, closeBtn);
        alertVBox.setVisible(true);
    }

    public Font getSantoriniFont(int dimension) {
        return Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(), dimension);
    }

    public void setSelectable(int x, int y, boolean val) {
        StackPane s = (StackPane) board.getChildren().get(board.getRowCount()*y + x);
        StackPane shadow = (StackPane) s.getChildren().get(SHADOW);

        shadow.setVisible(val);
    }

    public void focusWorkers(boolean value) {
        String color = clientView.getBoard().getPlayersMap().get(clientView.getUsername()).getColor().toString().toLowerCase();
        StackPane x;
        ImageView v;
        for(int i=0; i<25; i++) {
            x = (StackPane) board.getChildren().get(i);
            v = (ImageView) x.getChildren().get(WORKER);
            if(v.getImage()!=null && v.getImage().equals(workerColors.get(color)))
                setSelectable(i%5, i/5, value);
        }
    }

    /*
    return the tile in (x,y) position
    from StackOverflow: https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content
     */
    public Node getBoardCell(int x, int y) {
        for (Node node : board.getChildren()) {
            if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x) {
                return node;
            }
        }
        return null;
    }

//    public void textMessage(String msg) {
//        if(isInitialized)
//            userInteractionLabel.setText("system message: \n" + msg);
//    }

    public void textMessage(String title, String header, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setTitle(title);
        a.setHeaderText(header);
        a.show();
    }

    private void setShadowOff() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                StackPane cell = (StackPane)getBoardCell(i,j);
                cell.getChildren().get(SHADOW).setVisible(false);
            }
        }
    }

    public void markAvailableTiles(Action action) {
        List<Pair<Integer,Integer>> availableTiles = possibleActions.get(action);

        for(int i=0; i<availableTiles.size();i++) {
            Pair<Integer,Integer> cell = availableTiles.get(i);

            StackPane s = (StackPane) getBoardCell(cell.getFirst(),cell.getSecond());

            s.getChildren().get(SHADOW).setVisible(true);
        }
    }

    public void loserPlayer() {
        message.setText("YOU HAVE LOST!");
    }

    public void manageLoserPlayer() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                StackPane s = (StackPane) getBoardCell(i,j);
                if(clientView.getBoard().isThereAWorker(i,j)==null) {
                    ImageView worker = (ImageView) s.getChildren().get(WORKER);
                    worker.setImage(null);
                }
            }
        }
    }

    //returns a button with standard values, css+blue, 120x40, santoriniFont+dimension20, Centered, lighting_effect
    public Button newStandardizedButton() {
        Button btn = new Button("STD_BTN");
        btn.prefHeight(BTN_HEIGHT);
        btn.prefWidth(BTN_WIDTH);
        btn.setFont(santoriniFont);
        btn.setAlignment(Pos.CENTER);

        btn.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());
        btn.setOnMouseEntered(event -> btn.setEffect(lighting));
        btn.setOnMouseExited(event -> btn.setEffect(null));

        return btn;
    }

    public void opponentDefeated(String username) {
        listenerOff=true;
        alertVBox.getChildren().clear();
        alertVBox.setVisible(true);

        alertVBox.prefWidth(100);
        alertVBox.prefHeight(200);

        Label loserMsg = new Label("User " + username + " has lost!");
        loserMsg.setFont(getSantoriniFont(24));
        loserMsg.setAlignment(Pos.CENTER);

        Button close = newStandardizedButton();
        close.setText("CLOSE");
        close.prefWidth(BTN_WIDTH/2.0);
        close.setFont(getSantoriniFont(15));
        close.setOnMouseClicked(event -> {
            alertVBox.setVisible(false);
            alertVBox.getChildren().clear();
            listenerOff=false;
        });
        close.getStyleClass().addAll("coral", "white");

        alertVBox.getChildren().addAll(loserMsg,close);
    }
}
