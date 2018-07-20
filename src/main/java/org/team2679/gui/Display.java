package org.team2679.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import org.team2679.motion.spline.Spline;

import java.io.IOException;
import java.util.ArrayList;

public class  Display extends Application {

    private Display instance;
    private Pane waypointsPane;
    private ArrayList<DisplayWaypoint> points;
    private DisplaySpline spline;
    private Canvas canvas;

    public static void startApplication(){
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.instance = this;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);

        ImageView imageView = (ImageView) root.lookup("#fieldImage");
        imageView.setImage(new Image("/fieldImage.png"));

        this.canvas = (Canvas) root.lookup("#canvas");

        this.points = new ArrayList<DisplayWaypoint>();
        DisplayWaypoint[] p = new DisplayWaypoint[points.size()];
        this.points.toArray(p);
        this.spline = new DisplaySpline(p, Spline.SPLINE_TYPE.QUINTIC ,canvas);

        this.waypointsPane = (Pane) root.lookup("#pane");

        this.waypointsPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.SECONDARY) {
                    DisplayWaypoint p = new DisplayWaypoint(e.getX(), e.getY(), 0, waypointsPane.getPrefWidth(), waypointsPane.getPrefHeight(), instance);
                    points.add(p);
                    waypointsPane.getChildren().add(p.getSourcePoint());
                    waypointsPane.getChildren().add(p.getDirecionPoint());
                    waypointsPane.getChildren().add(p.getConnectionLine());
                }
            }
        });

        stage.setTitle("Path Maker");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void updateCanvas(){
        DisplayWaypoint[] p = new DisplayWaypoint[points.size()];
        this.points.toArray(p);
        this.spline = null;
        this.spline = new DisplaySpline(p, Spline.SPLINE_TYPE.QUINTIC ,canvas);
    }
}