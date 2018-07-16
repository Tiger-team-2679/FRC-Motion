package org.team2679.motion.spline;

public class Waypoint {

    private double x,y,theta = 0;

    public Waypoint(double x, double y, double theta){
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getTheta(){
        return this.theta;
    }
}
