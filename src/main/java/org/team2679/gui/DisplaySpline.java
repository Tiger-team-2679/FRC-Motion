package org.team2679.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.team2679.motion.Waypoint;
import org.team2679.motion.spline.Spline;

public class DisplaySpline extends Spline    {

    private Waypoint[] waypoints;
    private Canvas canvas;
    private double PIXEL_TO_METER = 915/16.4592; //supposed to be field size in pixels divided by real size in your measure units

    public DisplaySpline(Waypoint[] waypoints, SPLINE_TYPE type, Canvas c, double xOffset, double yOffset) {
        super(waypoints, type);

        this.waypoints = waypoints;
        this.canvas = c;

        this.waypoints = waypoints;
        Waypoint[] drawPoints = this.getSamples(1000);
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getWidth());
        if(drawPoints != null) {
            for (int i = 0; i < drawPoints.length - 1; i++) {
                canvas.getGraphicsContext2D().strokeLine(drawPoints[i].getX(), drawPoints[i].getY(), drawPoints[i + 1].getX(), drawPoints[i + 1].getY());
                canvas.getGraphicsContext2D().setStroke(Color.GREEN);
                canvas.getGraphicsContext2D().setLineWidth(4);
            }
        }
    }

    @Override
    public double getLength(){
        return super.getLength()/PIXEL_TO_METER;
    }

    public void saveToTxt(){

    }
}
