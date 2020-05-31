package it.polimi.ingsw.clientView.aaaaGUITesting;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ResizeTest extends Application {
    public static void main(String[] args) throws Exception { launch(args); }
    public void start(final Stage stage) throws Exception {
        // create a scene (a 200x200 rectangle centered in a StackPane).
//        final StackPane layout = StackPaneBuilder.create().children(
//                RectangleBuilder.create().x(0).y(0).height(200).width(200).fill(Color.PALEGREEN).build()
//        ).build();

        StackPane layout = new StackPane();
        Rectangle rect = new Rectangle(0,0,200,200);
        rect.setFill(Color.PALEGREEN);
        layout.getChildren().add(rect);
        final Scene scene = new Scene(layout, 200, 200);
        stage.setScene(scene);

        // add a listener so that we log what the scene's actual width is.
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Scene width = " + newSceneWidth);
            }
        });

        // show the stage.
        stage.show();

        // the stage's width is a read only property, but, somewhat strangely, it is settable, so we set up our own
        // read/write property which we animate and modify the stage's width whenever it changes.
        SimpleDoubleProperty stageWidthProperty = new SimpleDoubleProperty(stage.getWidth());
        stageWidthProperty.addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldStageWidth, Number newStageWidth) {
                stage.setWidth(newStageWidth.doubleValue());
                System.out.println("Stage width = " + newStageWidth);
            }
        });

        // animate the stage width and the scene is resized to fill the viewable area of the stage.
        Timeline resizer = new Timeline(
                new KeyFrame(Duration.ZERO,       new KeyValue(stageWidthProperty, 300, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.seconds(1), new KeyValue(stageWidthProperty, 500, Interpolator.EASE_BOTH))
        );
        resizer.setCycleCount(3);
        resizer.setAutoReverse(true);
        resizer.play();
    }
}

