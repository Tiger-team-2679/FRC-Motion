package org.team2679.motion.gui.canvas;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.team2679.motion.spline.Waypoint;

public abstract class GuiWaypoint {

    private final int CENTER_RADIUS = 10;
    private final int DIRECTION_RADIUS = 5;

    private Circle center_point_circle;
    private Circle direction_point_circle;
    private Line connecting_line;

    private Waypoint point;

    public GuiWaypoint(Waypoint point){
        this.point = point;
        initiate_shapes();
        updateGraphics();
        this.center_point_circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double dx = 0, dy = 0;
                if(direction_point_circle!= null){
                    dx = direction_point_circle.getCenterX();
                    dy = direction_point_circle.getCenterY();
                }
                updateWaypoint(event.getX(), event.getY(), dx, dy);
            }
        });
        if(!Double.isNaN(this.point.getAngle())) {
            this.direction_point_circle.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    updateWaypoint(center_point_circle.getCenterX(), center_point_circle.getCenterY(), event.getX(), event.getY());
                }
            });
        }
    }

    private void initiate_shapes(){
        this.center_point_circle = new Circle(this.point.getX(),this.point.getY(),CENTER_RADIUS);
        if(!Double.isNaN(this.point.getAngle())) {
            System.out.println(Double.valueOf(this.point.getAngle()));
            this.direction_point_circle = new Circle(this.point.getX() + 10,this.point.getY() + 10,CENTER_RADIUS);
            this.connecting_line = new Line();
        }
    }

    private void updateWaypoint(double cx, double cy, double dx, double dy){
        this.center_point_circle.setCenterX(cx);
        this.center_point_circle.setCenterY(cy);
        this.point.setX(cx);
        this.point.setY(cy);
        if(!Double.isNaN(this.point.getAngle())) {
            this.point.setAngle(Math.atan((cx - dx) / (cy - dy)));
        }
        updateGraphics();
    }

    private void updateGraphics(){
        if(!Double.isNaN(this.point.getAngle())) {
            this.connecting_line.setStartX(this.center_point_circle.getCenterX());
            this.connecting_line.setEndX(this.direction_point_circle.getCenterX());
            this.connecting_line.setStartY(this.center_point_circle.getCenterY());
            this.connecting_line.setEndY(this.direction_point_circle.getCenterY());
        }
        onMove();
    }

    public void addToPane(Pane pane){
        pane.getChildren().add(center_point_circle);
        if(!Double.isNaN(this.point.getAngle())) {
            pane.getChildren().addAll(direction_point_circle, connecting_line);
        }
    }

    public void removeFromPane(Pane pane){
        pane.getChildren().removeAll(center_point_circle, direction_point_circle, connecting_line);
    }

    public Waypoint getWaypoint(){
        return this.point;
    }

    public abstract  void onMove();
}
