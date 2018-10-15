package org.team2679.motion.spline;

public class Waypoint {

    double x, y, angle = Double.NaN;

    public Waypoint(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Waypoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
