package org.team2679.motion.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import org.team2679.motion.Waypoint;
import org.team2679.motion.spline.Spline;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class  Display extends Application {

    private ArrayList<DisplayWaypoint> points;

    private Pane waypointsPane;
    private TextArea waypointsText;
    private Canvas canvas;
    private GridPane bottomGrid;

    private double PIXEL_TO_METER = 920/16.4592; //supposed to be field size in pixels divided by real size in your measure units

    public static void startApplication(){
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.points = new ArrayList<DisplayWaypoint>();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);

        ImageView imageView = (ImageView) root.lookup("#fieldImage");
        imageView.setImage(new Image("/fieldImage.png"));

        this.canvas = (Canvas) root.lookup("#canvas");
        this.waypointsPane = (Pane) root.lookup("#pane");
        this.bottomGrid = (GridPane) root.lookup("#bottomGrid");
        this.waypointsText = (TextArea) root.lookup("#waypointText");

        updateWaypointsPane();
        createBottomGrid();


        stage.setTitle("Spline Planner");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void resetBoard(){
        this.points = new ArrayList<>();
        this.updateSplineDrawing();
        this.waypointsPane.getChildren().clear();
        waypointsPane.setVisible(true);
    }

    private void toggleVisible(Node n){
        if(n.isVisible()){
            n.setVisible(false);
        }
        else{
            n.setVisible(true);
        }
    }

    private void createBottomGrid(){
        Button rawButton = new Button("raw");
        rawButton.setMaxSize(150, 21);
        rawButton.setTranslateX(25);
        rawButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                toggleVisible(waypointsText);
            }
        });

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
                toggleVisible(waypointsPane);
            }
        });

        Text  lengthBox = new Text("Path Length: 0.00");
        lengthBox.setTranslateX(25);

        this.bottomGrid.add(rawButton, 4,0);
        this.bottomGrid.add(resetButton, 3,0);
        this.bottomGrid.add(toggleButton, 2,0);
        this.bottomGrid.add(lengthBox, 0, 0);
    }

    private void updateWaypointsPane(){
        this.waypointsPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.SECONDARY) {
                    DisplayWaypoint p = new DisplayWaypoint(e.getX(), e.getY(), 0, waypointsPane.getPrefWidth(), waypointsPane.getPrefHeight(), new Runnable() {
                        @Override
                        public void run() {
                            updateSplineDrawing();
                        }
                    });
                    points.add(p);
                    waypointsPane.getChildren().add(p.getConnectionLine());
                    waypointsPane.getChildren().add(p.getSourcePoint());
                    waypointsPane.getChildren().add(p.getDirecionPoint());
                    updateSplineDrawing();
                }
            }
        });
    }

    private void updateSplineDrawing(){
        Waypoint[] p = new Waypoint[points.size()];

        this.points.forEach(point -> {
            p[points.indexOf(point)] = new Waypoint(point.getX()/PIXEL_TO_METER ,point.getY()/PIXEL_TO_METER, point.getAngle());
        });

        Spline spline = new Spline(p, Spline.SPLINE_TYPE.QUINTIC);

        this.canvas.getGraphicsContext2D().clearRect(0,0, this.canvas.getWidth(), this.canvas.getHeight());
        this.canvas.getGraphicsContext2D().setLineWidth(2);

        // Draw the spline or if not successfull draw red line between way points
        Waypoint[] drawPoints;
        if(spline.isSplineSuccessfull()){
            this.canvas.getGraphicsContext2D().setStroke(Color.LAWNGREEN);
            int samplesAmount = 100;
            drawPoints = spline.getSamples(samplesAmount);
        }
        else {
            this.canvas.getGraphicsContext2D().setStroke(Color.RED);
            drawPoints = spline.getWaypoints();
        }

        if(drawPoints != null) {
            for (int i = 0; i <  drawPoints.length - 1; i++) {
                canvas.getGraphicsContext2D().strokeLine((drawPoints[i].getX()*PIXEL_TO_METER), (drawPoints[i].getY()*PIXEL_TO_METER), (drawPoints[i + 1].getX()*PIXEL_TO_METER), (drawPoints[i + 1].getY()*PIXEL_TO_METER));
            }
        }

        // print the length
        DecimalFormat df = new DecimalFormat("0.00");
        String formate;
        if (spline.isSplineSuccessfull()) {
            formate = df.format(spline.getLength());
        }
        else {
            formate = df.format(0);
        }

        this.bottomGrid.getChildren().forEach(it->{
            if( it instanceof Text){
                if(((Text) it).getText().contains("Path Length: ")) {
                    ((Text) it).setText("Path Length: " + formate);
                }
            }
        });


        this.waypointsText.clear();
        if(!spline.isSplineSuccessfull()) {
            this.points.forEach(point -> {
                String x = df.format(point.getX() / PIXEL_TO_METER);
                String y = df.format(point.getY() / PIXEL_TO_METER);
                this.waypointsText.setText(this.waypointsText.getText() + "Waypoint " + (this.points.indexOf(point) + 1) + ": " + x + "x" + y + "    " + point.getAngle() + "\n");
            });
        }
    }
}