package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Spline {

    Polynomial[] splines;
    private double length = 0;
    private double xLength = 0;

    private SPLINE_TYPE type;

    public enum SPLINE_TYPE {
        CUBIC, QUINTIC
    }

    public Spline(Waypoint[] waypoints, SPLINE_TYPE type){
        this.splines = new Polynomial[waypoints.length - 1];
        if(type == SPLINE_TYPE.CUBIC)
        {
            this.splines = new Polynomial[waypoints.length - 1];
            for(int i = 0; i < waypoints.length - 1; i++){
                splines[i] = new Cubic(waypoints[i], waypoints[i + 1]);
            }
        }
        else if (type == SPLINE_TYPE.QUINTIC)
        {
            this.splines = new Quintic[waypoints.length - 1];
            for(int i = 0; i < waypoints.length - 1; i++){
                splines[i] = new Quintic(waypoints[i], waypoints[i + 1]);
            }
        }

        this.type = type;
    }

    public double getLength(){
        if(this.length > 0){
            return this.length;
        }
        for(Polynomial polynomial: splines){
            this.length += polynomial.getLength();
        }
        return this.length;
    }

    public double getXLength(){
        if(this.length > 0){
            return this.length;
        }
        for(Polynomial polynomial: splines){
            this.length += polynomial.getEndPoint().getX() - polynomial.getStartPoint().getX();
        }
        return this.length;
    }

    public Waypoint[] getSamples(int amount){
        Waypoint[] path = new Waypoint[amount];
        double u = splines[0].getStartPoint().getX();
        int currSplineIndex = 0;
        for (int j = 0; j < path.length; j += 1) {
            u += getXLength()/path.length;
            if(u > splines[currSplineIndex].getEndPoint().getX() && currSplineIndex + 1 < splines.length){
                currSplineIndex+=1;
            }
            path[j] = new Waypoint(u, splines[currSplineIndex].getYByX(u), splines[currSplineIndex].getAngleByX(u));
        }
        return path;
    }
}
