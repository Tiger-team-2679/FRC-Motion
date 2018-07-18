package org.team2679.motion.spline;

public class Waypoint {

    private double x,y,angle = 0;

    public Waypoint(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getAngle(){
        return this.angle;
    }
}
