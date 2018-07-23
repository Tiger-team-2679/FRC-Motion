package org.team2679.motion.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.team2679.motion.Waypoint;

public class DisplayWaypoint extends Waypoint{

    private double X_BOUNDRY;
    private double Y_BOUNDRY;
    private Circle sourcePoint;
    private Circle direcionPoint;
    private Line connectionLine;

    public DisplayWaypoint(double x, double y, double angle, double X_BOUNDRY, double Y_BOUNDRY, Runnable runnable) {
        super(x, y, angle);
        this.X_BOUNDRY = X_BOUNDRY;
        this.Y_BOUNDRY = Y_BOUNDRY;

        this.sourcePoint = new Circle();
        this.sourcePoint.setRadius(10);
        this.sourcePoint.setCenterX(x);
        this.sourcePoint.setCenterY(y);

        this.direcionPoint = new Circle();
        this.direcionPoint.setRadius(7);
        this.direcionPoint.setCenterX(x + 50);
        this.direcionPoint.setCenterY(y);
        this.direcionPoint.setFill(Color.RED);

        this.connectionLine = new Line();
        this.connectionLine.setFill(Color.RED);
        this.connectionLine.setStroke(Color.RED);
        this.connectionLine.setStrokeWidth(2);
        updateConnectionLineAndAngle();

        runnable.run();

        this.sourcePoint.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();
                double lastX = getX();
                double lastY = getY();
                setX(x);
                setY(y);
                Circle point = (Circle) e.getSource();
                if(x <= X_BOUNDRY - point.getRadius() && 0 + point.getRadius() <= x && y <= Y_BOUNDRY- point.getRadius() && 0 + point.getRadius() <= y) {
                    point.setCenterX(x);
                    point.setCenterY(y);
                    direcionPoint.setCenterX(direcionPoint.getCenterX() + x - lastX);
                    direcionPoint.setCenterY(direcionPoint.getCenterY() + y - lastY);
                    updateConnectionLineAndAngle();
                    runnable.run();
                }
            }
        });
        this.direcionPoint.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e){
                double x = e.getX();
                double y = e.getY();
                Circle point = (Circle) e.getSource();
                if(x <= X_BOUNDRY - point.getRadius() && 0 + point.getRadius() <= x && y <= Y_BOUNDRY- point.getRadius() && 0 + point.getRadius() <= y) {
                    point.setCenterX(x);
                    point.setCenterY(y);
                    updateConnectionLineAndAngle();
                    runnable.run();
                }
            }
        });
    }

    private void updateConnectionLineAndAngle(){
        this.connectionLine.setStartX(this.sourcePoint.getCenterX());
        this.connectionLine.setStartY(this.sourcePoint.getCenterY());
        this.connectionLine.setEndX(this.direcionPoint.getCenterX());
        this.connectionLine.setEndY(this.direcionPoint.getCenterY());
        int angle = (int) -(Math.toDegrees(Math.atan2(connectionLine.getEndX() - connectionLine.getStartX(), connectionLine.getEndY() - connectionLine.getStartY())) - 90);
        if(angle < 360 && angle > 180){
            angle -= 360;
        }
        setAngle(angle);
    }

    public Circle getSourcePoint(){
        return this.sourcePoint;
    }
    public Circle getDirecionPoint(){
        return this.direcionPoint;
    }
    public Line getConnectionLine(){
        return this.connectionLine;
    }

}
