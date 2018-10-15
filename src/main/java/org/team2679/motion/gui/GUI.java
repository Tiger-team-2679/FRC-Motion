package org.team2679.motion.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MenuBar menuBar = MenuBarGenerator.generate_MenuBar();
        StackPane canvas = CanvasGenerator.generate_canvas_object();

        stage.setTitle("Menu Sample");
        Scene scene = new Scene(new VBox());
        scene.setFill(Color.OLDLACE);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, canvas);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

}
