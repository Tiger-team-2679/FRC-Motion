package org.team2679.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import org.team2679.motion.Waypoint;
import org.team2679.motion.spline.Spline;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class  Display extends Application {

    private Display instance;
    private Pane waypointsPane;
    private ArrayList<DisplayWaypoint> points;
    private DisplaySpline spline;
    private Canvas canvas;
    private Parent root;

    public static void startApplication(){
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.instance = this;
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

        updateCanvas();
        updateWaypointsPane();

        GridPane bottomGrid = (GridPane) root.lookup("#bottomGrid");
        Button saveButton = new Button("save");
        saveButton.setMaxSize(150, 21);
        saveButton.setTranslateX(25);

        Button resetButton = new Button("Reset");
        resetButton.setMaxSize(150, 31);
        resetButton.setTranslateX(25);
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                resetBoard();
            }
        });

        Button toggleButton = new Button("Toggle points");
        toggleButton.setMaxSize(150, 31);
        toggleButton.setTranslateX(25);
        toggleButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                toggleWaypointsPane();
            }
        });

        Text  lengthBox = new Text("Path Length: 0.00");
        lengthBox.setTranslateX(25);

        bottomGrid.add(saveButton, 4,0);
        bottomGrid.add(resetButton, 3,0);
        bottomGrid.add(toggleButton, 2,0);
        bottomGrid.add(lengthBox, 0, 0);

        stage.setTitle("Spline Planner");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void toggleWaypointsPane(){
        if(this.waypointsPane.isVisible()){
            this.waypointsPane.setVisible(false);
        }
        else{
            this.waypointsPane.setVisible(true);
        }
    }

    private void updateWaypointsPane(){
        this.waypointsPane = (Pane) root.lookup("#pane");
        this.waypointsPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.SECONDARY) {
                    DisplayWaypoint p = new DisplayWaypoint(e.getX(), e.getY(), 0, waypointsPane.getPrefWidth(), waypointsPane.getPrefHeight(), instance);
                    points.add(p);
                    waypointsPane.getChildren().add(p.getConnectionLine());
                    waypointsPane.getChildren().add(p.getSourcePoint());
                    waypointsPane.getChildren().add(p.getDirecionPoint());
                    updateCanvas();
                }
            }
        });
    }

    private void resetBoard(){
        this.points = new ArrayList<>();
        this.updateCanvas();
        this.waypointsPane.getChildren().clear();
    }

    public void updateCanvas(){
        Waypoint[] p = new Waypoint[points.size()];
        double xOffset = 0;
        double yOffset = 0;
        if(this.points.size() != 0){
            xOffset = this.points.get(0).getX();
            yOffset = this.points.get(0).getY();
        }
        this.points.toArray(p);
        this.spline = null;
        this.spline = new DisplaySpline(p, Spline.SPLINE_TYPE.QUINTIC ,canvas, xOffset, yOffset);

        GridPane bottomGrid = (GridPane) root.lookup("#bottomGrid");

        DecimalFormat df=new DecimalFormat("0.00");
        String formate = df.format(spline.getLength());

        bottomGrid.getChildren().forEach(it->{
            if( it instanceof Text){
                if(((Text) it).getText().contains("Path Length: ")) {
                    ((Text) it).setText("Path Length: " + formate);
                }
            }
        });
    }
}