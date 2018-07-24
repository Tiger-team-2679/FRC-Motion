package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Spline {

    Polynomial[] splines;

    private SPLINE_TYPE type;
    private double length;
    private Waypoint[] waypoints;
    private boolean isSplineSuccessfull = true;

    public enum SPLINE_TYPE {
        CUBIC, QUINTIC
    }

    public Spline(Waypoint[] waypoints, SPLINE_TYPE type){
        try {
            if (waypoints.length <= 1) {
                this.splines = new Polynomial[0];
            } else {
                this.splines = new Polynomial[waypoints.length - 1];
                if (type == SPLINE_TYPE.CUBIC) {
                    this.splines = new Polynomial[waypoints.length - 1];
                    for (int i = 0; i < waypoints.length - 1; i++) {
                        splines[i] = new Cubic(waypoints[i], waypoints[i + 1]);
                    }
                } else if (type == SPLINE_TYPE.QUINTIC) {
                    this.splines = new Quintic[waypoints.length - 1];
                    for (int i = 0; i < waypoints.length - 1; i++) {
                        splines[i] = new Quintic(waypoints[i], waypoints[i + 1]);
                    }
                }
            }
        }
        catch (Exception e){
            isSplineSuccessfull = false;
        }
        this.type = type;
        this.waypoints = waypoints;
    }

    public Waypoint[] getSamples(int amount){
        if(this.splines.length < 1 || !isSplineSuccessfull){
            return null;
        }
        Waypoint[] samplePoints = new Waypoint[amount*splines.length];
        for(int i = 0; i < splines.length; i++) {
            double u = 0;
            for (int j = 0; j < amount; j += 1) {
                u += (double) 1 / amount;
                samplePoints[i*amount + j] = splines[i].getWaypointByPrecent(u);
            }
        }
        return samplePoints;
    }

    public Waypoint[] getWaypoints(){
        return this.waypoints;
    }

    public double getLength(){
        if(length != 0){
            return this.length;
        }
        this.length = 0;
        for(Polynomial poly: this.splines)
        {
            length += poly.getLength();
        }
        return  this.length;
    }

    public boolean isSplineSuccessfull(){
        return this.isSplineSuccessfull;
    }
}
